package mirari.discuss

import mirari.Site

class Reply {

    static mapWith = "mongo"

    String id

    Site owner

    static belongsTo = [comment:Comment]

    Date dateCreated = new Date()
    Date lastUpdated

    String text

    static constraints = {
    }
}
