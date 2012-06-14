package mirari.security

import mirari.Site
import mirari.repo.security.AccountRepo
import mirari.repo.security.SecurityCodeRepo
import mirari.repo.SiteRepo
import org.apache.log4j.Logger
import ru.mirari.infra.I18n
import ru.mirari.infra.json.ServiceResponse
import ru.mirari.infra.mail.MailSendEvent

class RegistrationActService {
    private Logger log = Logger.getLogger(getClass())

    def securityService
    I18n i18n

    AccountRepo accountRepo
    SecurityCodeRepo securityCodeRepo
    SiteRepo siteRepo
    //AvatarRepo avatarRepo

    def grailsApplication

    private ConfigObject getConf() {
        grailsApplication.config
    }

    /**
     * Tries to register a user
     *
     * @param command
     * @return
     */
    ServiceResponse handleRegistration(RegisterCommand command, Site portal) {
        ServiceResponse resp = new ServiceResponse().model([:])
        if (command.hasErrors()) {
            return resp.error("register.error.commandValidationFailed")
        }

        Account account
        Site profile
        SecurityCode code

        try {
            account = new Account(
                    email: command.email, password: securityService.encodePassword(command.password), accountLocked: true, enabled: true)
            accountRepo.save(account)

            if (!account.id) {
                log.error "account not saved"
                log.error account.errors
                return resp.error("register.error.userNotSaved")
            }

            profile = new Site(
                    kind: SiteKind.PROFILE,
                    displayName: command.displayName,
                    name: command.name
            )
            profile.portal = portal
            profile.account = account
            profile.updateHost()

            siteRepo.save(profile)
            if (!profile.id) {
                accountRepo.delete(account)
                return resp.error("register.error.profileNotSaved")
            }
            account.mainProfile = profile
            accountRepo.save(account)

            code = new SecurityCode(account: account)
            securityCodeRepo.save(code)

            sendRegisterEmail(account, code.token, portal)
            return resp.model(emailSent: true, token: code.token).success()
        } catch (Exception e) {
            log.error("Error in registration", e)
            if (account?.id) {
                accountRepo.delete(account)
            }
            if (profile?.id) {
                siteRepo.delete(profile)
            }
            if (code?.id) {
                securityCodeRepo.delete(code)
            }
        }
        return resp.error("unknown error")
    }

    /**
     * Verifies registration (user email) by token
     *
     * @param token
     * @return
     */
    ServiceResponse verifyRegistration(String token) {
        ServiceResponse result = new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget)

        def code = token ? securityCodeRepo.getByToken(token) : null

        if (!code) {
            return result.error("register.error.badCode")
        }

        Account account = code.account

        if (!account || !account.id) {
            return result.error("register.error.userNotFound")
        }
        setDefaultRoles(account)
        accountRepo.save(account)

        securityCodeRepo.delete(code)

        if (result.alertCode) {
            return result
        }

        if (!account) {
            return result.error("register.error.badCode")
        }

        securityService.reauthenticate account.email

        return result.redirect(conf.grails.mirari.sec.url.emailVerified).success("register.complete")
    }

    /**
     * Sends an forgot-password email
     *
     * @param email
     * @return
     */
    ServiceResponse handleForgotPassword(String emailOrName, Site portal) {
        ServiceResponse response = new ServiceResponse()
        if (!emailOrName) {
            return response.warning('register.forgotPassword.username.missing')
        }

        Account account = accountRepo.getByEmail(emailOrName)
        if (!account) {
            Site profile = siteRepo.getByName(emailOrName)
            if (profile && profile.isProfileSite()) {
                account = profile.account
            }
        }

        if (!account) {
            return response.error('register.forgotPassword.user.notFound')
        }

        SecurityCode code = new SecurityCode(account: account)
        securityCodeRepo.save(code)

        sendForgotPasswordEmail(account, code.token, portal)
        return response.model(emailSent: true, token: code.token).info()
    }

    /**
     * Resets the password for user
     *
     * @param token
     * @param command
     * @param requestMethod
     * @return
     */
    ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
        SecurityCode code = token ? securityCodeRepo.getByToken(token) : null
        if (!code) {
            return new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget).error('register.resetPassword.badCode')
        }

        if (!requestMethod.equalsIgnoreCase("post")) {
            return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning()
        }

        command.email = code['email']
        command.validate()

        if (command.hasErrors()) {
            return new ServiceResponse().model(token: token, command: command).error()
        }

        Account account = code.account

        if (!account) {
            return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning('register.forgotPassword.user.notFound')
        }
        account.password = securityService.encodePassword( command.password )

        setDefaultRoles(account)
        // validate user account if it wasn't before
        if (account.accountLocked && account.authorities.size() == 0) {
            setDefaultRoles(account)
        }
        accountRepo.save(account)
        securityCodeRepo.delete(code)


        securityService.reauthenticate account.email

        return new ServiceResponse().redirect(
                conf.grails.mirari.sec.url.passwordResetted
                ?: conf.grails.mirari.sec.url.defaultTarget
        ).success('register.resetPassword.success')
    }

    private setDefaultRoles(Account account) {
        account.accountLocked = false
        List defaultRoleNames = conf.grails.mirari.sec.defaultRoleNames
        account.authorityNames = defaultRoleNames
    }

    /**
     * Sending register email routine
     *
     * @param person
     * @param token
     * @return
     */
    private boolean sendRegisterEmail(Account account, String token, Site portal) {
        new MailSendEvent(this)
                .to(account.email)
                .subject(i18n."register.confirm.emailSubject")
                .view("/mail-messages/confirmEmail")
                .model(username: account.mainProfile.displayName ?: account.mainProfile.name, token: token, host: portal.host)
                .fire()

        true
    }

    /**
     * Sending password reminder email routine
     *
     * @param person
     * @param token
     * @return
     */
    private boolean sendForgotPasswordEmail(Account account, String token, Site portal) {
        new MailSendEvent(this)
                .to(account.email)
                .subject(i18n."register.forgotPassword.emailSubject")
                .view("/mail-messages/forgotPassword")
                .model(username: account.mainProfile.displayName ?: account.mainProfile.name, token: token, host: portal.host)
                .fire()
        true
    }
}
