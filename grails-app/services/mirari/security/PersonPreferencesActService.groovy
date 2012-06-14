package mirari.security

import mirari.repo.security.AccountRepo
import ru.mirari.infra.json.ServiceResponse
import ru.mirari.infra.mail.MailSendEvent

class PersonPreferencesActService {

    static transactional = false

    def i18n
    def securityService
    AccountRepo accountRepo

    ServiceResponse setEmail(session, ChangeEmailCommand command) {
        if (!securityService.loggedIn) {
            return new ServiceResponse().error("personPreferences.changeEmail.notLoggedIn")
        }

        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeEmail.errors")
        }

        String email = command.email

        if (email == securityService.account.email) {
            return new ServiceResponse().warning("personPreferences.changeEmail.oldEmailInput")
        }

        if (accountRepo.emailExists(email)) {
            return new ServiceResponse().warning("personPreferences.changeEmail.notUnique")
        }

        session.changeEmail = email
        session.changeEmailToken = UUID.randomUUID().toString().replaceAll('-', '')

        new MailSendEvent(this)
                .to(email)
                .subject(i18n."personPreferences.changeEmail.mailTitle")
                .view("/mail-messages/changeEmail")
                .model(username: securityService.profile.displayName ?: securityService.profile.name,
                token: session.changeEmailToken)
                .fire()

        new ServiceResponse().success("personPreferences.changeEmail.checkEmail")
    }

    ServiceResponse applyEmailChange(session, String token) {
        ServiceResponse resp = new ServiceResponse()
        if (!securityService.loggedIn) {
            return resp.error("personPreferences.changeEmail.notLoggedIn")
        }
        if (!token || token != session.changeEmailToken) {
            return resp.error("personPreferences.changeEmail.wrongToken")
        }

        String email = session.changeEmail

        Account account = securityService.account
        if (!account) {
            return resp.error("personPreferences.changeEmail.personNotFound")
        }
        if (accountRepo.emailExists(email)) {
            return resp.error("personPreferences.changeEmail.emailExists")
        }
        account.email = email
        accountRepo.save(account)

        session.changeEmail = null
        session.changeEmailToken = null

        resp.success("personPreferences.changeEmail.success")
    }

    ServiceResponse changePassword(ChangePasswordCommand command, Account account) {
        ServiceResponse resp = new ServiceResponse()
        if (command.oldPassword) {
            if (account.password != securityService.encodePassword(command.oldPassword)) {
                return resp.warning("personPreferences.changePassword.incorrect")
            }
            if (!command.hasErrors()) {
                account.password = securityService.encodePassword(command.password)
                accountRepo.save(account)
                return resp.success("personPreferences.changePassword.success")
            } else {
                resp.error("personPreferences.changePassword.error")
            }
        }
        resp
    }
}
