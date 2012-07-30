package mirari.data

class BlockContent {

    static mapWith = "mongo"

    String id

    String text

    static belongsTo = [block:Block]

    static constraints = {
        text nullable: true
    }
}
