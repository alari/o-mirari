package mirari.struct

import mirari.Site
import mirari.data.Block
import ru.mirari.infra.piles.PiledItem

class Entry implements PiledItem {
    static mapWith = "mongo"
    String id

    String title

    List<Block> blocks

    Site owner

    Date dateCreated = new Date()
    Date lastUpdated

    static constraints = {
    }
}
