package mirari.security

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class LogoutController {

    def securityService

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index = {
        securityService.logout()
        // TODO put any pre-logout code here
        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
    }
}
