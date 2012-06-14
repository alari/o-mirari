package mirari.security

import grails.plugins.springsecurity.SpringSecurityService

import mirari.UtilController
import mirari.repo.security.SecurityCodeRepo
import mirari.Site

class HostAuthController extends UtilController {
    SpringSecurityService springSecurityService
    SecurityCodeRepo securityCodeRepo

    def js(String id) {
        String referer = request.getHeader("referer")
        if (securityService.id == id) referer = null
        if (!springSecurityService.isLoggedIn()) referer = null

        String href;
        if (referer) {
            URL url = new URL(referer)
            Site ref = siteService.getByHost(url.host)
            if (ref && ref != _portal) {
                SecurityCode code = new SecurityCode(account: securityService.account)
                code['url'] = url.path
                code['host'] = url.host
                securityCodeRepo.save(code)

                System.out.println("JS: Referer is ours: " + ref.host)
                href = ref.getUrl(controller: "hostAuth", action: "setSession", params: [token: code.token])
            }
        }
        render contentType: "application/javascript", text: href ? "window.location.href='".concat(href).concat("';") : "// nothing"
    }

    def setSession(String token) {
        System.out.println("JS: session for token: " + token)
        SecurityCode code = securityCodeRepo.getByToken(token)
        if (code && code['host'] == request.getHeader("host")) {
            System.out.println("JS: referer is: " + ((String)code['host']).concat(code['url']))
            springSecurityService.reauthenticate(code.account.email)
            redirect url: "http://".concat(code['host']).concat(code['url'])
            securityCodeRepo.delete(code)
        } else redirect url: ""
    }
}
