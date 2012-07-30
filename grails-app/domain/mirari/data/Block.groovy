package mirari.data

import mirari.struct.Entry

class Block {

    static mapWith = "mongo"

    String id

    BlockContent content

    static hasOne = [content:BlockContent]
    static belongsTo = [entry:Entry]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        content nullable: true
    }
}
