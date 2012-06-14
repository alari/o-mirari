package mirari.data

class Block {

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
