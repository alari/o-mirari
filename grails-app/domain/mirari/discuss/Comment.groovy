package mirari.discuss

import mirari.Site
import mirari.struct.Entry

class Comment {
    static mapWith = "mongo"

    String id

    Date dateCreated = new Date()
    Date lastUpdated

    Site owner
    Entry entry

    String title
    String text

    static hasMany = [replies:Reply]
    static belongsTo = [entry: Entry]

    static constraints = {
    }
}
