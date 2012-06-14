package mirari.security

import mirari.UtilController

import grails.plugins.springsecurity.Secured
import ru.mirari.infra.json.ServiceResponse

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class RegisterController extends UtilController {

    static defaultAction = 'index'

    def registrationActService

    def index(RegisterCommand command) {
        Map model
        if (request.post) {
            ServiceResponse resp = registrationActService.handleRegistration(command, _mainPortal)
            alertsService.alert(flash, resp)
            model = resp.model
            model?.put("command", command)
            return model
        } else {
            return [command: new RegisterCommand()]
        }
    }

    def verifyRegistration(String t) {
        String token = t
        ServiceResponse result = registrationActService.verifyRegistration(token)
        alertsService.alert(flash, result)

        redirect result.redirect
    }

    def forgotPassword() {

        if (!request.post) {
            // show the form
            render view: "/register/forgotPassword"
            return
        }

        ServiceResponse result = registrationActService.handleForgotPassword(params.name, _mainPortal)
        alertsService.alert(flash, result)

        render view: "/register/forgotPassword", model: result.model
    }

    def resetPassword(ResetPasswordCommand command) {

        String token = params.t

        ServiceResponse result = registrationActService.handleResetPassword(token, command, request.method)
        alertsService.alert(flash, result)

        if (!result.ok) {
            if (result.redirect) {
                redirect result.redirect
            } else {
                render view: "/register/resetPassword", model: result.model
            }
        } else {
            redirect result.redirect
        }
    }
}


