package mirari.data

import mirari.struct.Entry

class BlocksHolder {

    static mapWith = "mongo"

    String id

    List<Block> blocks = []

    Entry entry

    static hasMany = [blocks:Block]
    static belongsTo = [entry:Entry]

    static constraints = {
    }
}
