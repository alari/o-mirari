package mirari

class SiteFilters {
    def alertsService
    def siteService
    
    def filters = {
        all(controller: "*", action: "*") {
            before = {
                String host = request.getHeader("host")
                if(!host) {
                    response.setStatus(200)
                    return false
                }
                if(host.contains(":")) {
                    host = host.substring(0, host.indexOf(":"))
                }
                Site site = siteService.getByHost(host)
                Site mainPortal = siteService.getMainPortal()
                if(!site) {
                    // TODO: throw an exception, render exception without layout
                    alertsService.warning(flash, "error.siteNotFound", [host])
                    log.info("Host not found: "+request.getHeader("host")+" ("+request.forwardURI+"), referer: "+request.getHeader("referer"))
                    redirect(uri: mainPortal.getUrl())
                    return false
                }

                if(site.isPortalSite()) {
                    request._site = site
                    request._portal = site
                } else if(site.isSubSite()) {
                    request._site = site
                    request._portal = site.portal ?: site
                }
            }
            after = {Map model ->
                if(model) {
                    model._site = request._site
                    model._portal = request._portal
                    model._mainPortal = siteService.getMainPortal()
                }
            }
        }
    }
}
