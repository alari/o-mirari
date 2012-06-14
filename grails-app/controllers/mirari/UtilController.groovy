package mirari


import grails.gsp.PageRenderer
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.json.ServiceResponse

import mirari.security.Account
import mirari.security.SecurityService

abstract class UtilController {
    def alertsService
    @Autowired PageRenderer groovyPageRenderer

    SecurityService securityService

    def Logger log = Logger.getLogger(this.getClass())
    SiteService siteService

    protected Site get_portal() {
        request._portal
    }

    protected Site get_mainPortal() {
        if (_portal?.isPortalSite()) {
            return _portal
        }
        siteService.mainPortal
    }

    protected String get_siteName() {
        _site?.name ?: _portal?.name
    }

    protected Site get_site() {
        request._site ?: _portal
    }

    protected Site get_profile() {
        securityService.profile
    }

    protected Account get_account() {
        securityService.account
    }

    protected String get_accountId() {
        securityService.id
    }

    protected boolean isLoggedIn() {
        securityService.loggedIn
    }

    protected void error(String code) {
        alertsService.error flash, code
    }

    protected void info(String code) {
        alertsService.info flash, code
    }

    protected void success(String code) {
        alertsService.success flash, code
    }

    protected void warning(String code) {
        alertsService.warning flash, code
    }

    protected boolean hasNoRight(boolean rightCheck, String errCode = "error.permission.denied",
                                 String redirectUri = null) {
        if (!rightCheck) {
            error errCode
            redirect uri: redirectUri ?: "/"
            return true
        }
        false
    }

    protected boolean isNotFound(List toCheck) {
        for (def o in toCheck) {
            if (isNotFound(o)) return true
        }
        false
    }

    protected boolean isNotFound(def toCheck) {
        if (!toCheck) {
            error "error.pageNotFound"
            log.info("Not found: " + request.forwardURI)
            redirect(uri: "/")
            return true
        }
        false
    }

    protected ServiceResponse alert(ServiceResponse resp) {
        alertsService.alert flash, resp
        resp
    }

    protected void renderAlerts() {
        render template: "/includes/alerts", model: [alerts: alertsService.getAlerts(flash)]
        alertsService.clean(flash)
    }

    protected void renderJson(ServiceResponse resp) {
        Map json = [
                srv: [
                        ok: resp.isOk()
                ],
                mdl: resp.model
        ]
        if (resp.redirect) {
            json.srv.redirect = createLink(resp.redirect).toString()
        } else {
            alert resp
            json.srv.alerts = alertsService.getAlerts(flash).collect {[message: g.message(code: it.code, args: it.params), level: it.level.toString()]}
            alertsService.clean(flash)
        }
        render JsonUtil.objToString(json)
    }
}
