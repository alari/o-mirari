package mirari.security


import mirari.repo.SiteRepo
import mirari.util.validators.PasswordValidators

import mirari.util.validators.NameValidators
import grails.validation.Validateable
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.security.AccountRepo

/**
 * @author Dmitry Kurinskiy
 * @since 18.08.11 23:02
 */
@Validateable
class RegisterCommand {
    @Autowired AccountRepo accountRepo
    @Autowired SiteRepo siteRepo

    String email
    String password
    String password2

    String name
    String displayName

    static constraints = {
        name blank: false, validator: { value, command ->
            if (value) {
                if (command.siteRepo.nameExists(value)) {
                    return 'registerCommand.name.unique'
                }

                if (!((String) value).matches(NameValidators.MATCHER)) {
                    return "registerCommand.name.invalid"
                }
            }
        }
        email blank: false, email: true, validator: { String email, command ->
            if (command.accountRepo.emailExists(command.email)) {
                return 'registerCommand.email.notUnique'
            }
        }
        password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
        password2 validator: PasswordValidators.password2Validator
    }
}
