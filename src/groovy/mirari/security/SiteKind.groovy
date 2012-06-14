package mirari.security

import mirari.Site

/**
 * @author alari
 * @since 1/28/12 2:11 PM
 */
public enum SiteKind {
    PORTAL("portal"),
    PROFILE("profile"),
    PROJECT("project");

    final String name

    SiteKind(String n) {
        name = n
    }

    void setSiteHost(Site site) {
        if (site.kind == PORTAL) {
            site.host = site.name
        } else {
            site.host = site.name?.concat(".")?.concat(site.portal.host)
        }
    }
}