package mirari

import mirari.security.Account
import mirari.security.SiteKind

class Site {

    String id

    String name
    String host
    String displayName

    Account account

    Site portal

    SiteKind kind

    String feedBurnerName

    Date dateCreated = new Date()
    Date lastUpdated

    boolean isProfileSite() {
        kind == SiteKind.PROFILE
    }

    boolean isPortalSite() {
        kind == SiteKind.PORTAL
    }

    boolean isSubSite() {
        !isPortalSite()
    }

    static constraints = {
        name unique: true
        host unique: true
        feedBurnerName nullable: true
        account nullable:  true
    }

    def beforeUpdate() {
        if (isDirty('kind') || isDirty('name')) {
            updateHost()
        }
    }

    def beforeInsert() {
        updateHost()
    }

    void updateHost() {
        name = name.toLowerCase()
        kind.setSiteHost(this)
    }

    String getUrl() {
        "http://"+host
    }

    String toString() {
        displayName
    }
}
