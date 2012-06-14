package mirari.data

class BlockContent {

    String id

    String text

    static belongsTo = [block:Block]

    static constraints = {
        text nullable: true
    }
}
