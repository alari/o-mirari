package mirari

import grails.plugins.springsecurity.Secured
import mirari.repo.SiteRepo

class SiteController extends UtilController {
    SiteRepo siteRepo

    @Secured("ROLE_USER")
    def list() {
        [sites: siteRepo.listByAccount(_account)]
    }
}
