package mirari.data

class Block {

    static mapWith = "mongo"

    String id

    BlockContent content

    static hasOne = [content:BlockContent]
    static belongsTo = [holder:BlocksHolder]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        content nullable: true
    }
}
