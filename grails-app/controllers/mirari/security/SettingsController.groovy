package mirari.security

import mirari.UtilController
import grails.plugins.springsecurity.Secured
import mirari.util.validators.PasswordValidators

@Secured("ROLE_USER")
class SettingsController extends UtilController {

    static defaultAction = "index"

    def personPreferencesActService

    def index() {
        [account: _account]
    }

    def changeEmail(ChangeEmailCommand command) {
        alert personPreferencesActService.setEmail(session, command)

        renderAlerts()
    }

    def applyEmailChange(String t) {
        alert personPreferencesActService.applyEmailChange(session, t)
        redirect action: "index"
    }

    def changePassword(ChangePasswordCommand command) {
        alert personPreferencesActService.changePassword(command, _account)

        renderAlerts()

        render template: "changePassword", model: [chPwdCommand: command]
    }

}


class ChangeEmailCommand {
    String email

    static constraints = {
        email email: true, blank: false
    }
}

class ChangePasswordCommand {
    String email
    String oldPassword
    String password
    String password2

    static constraints = {
        email blank: true
        oldPassword blank: false
        password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
        password2 validator: PasswordValidators.password2Validator
    }
}