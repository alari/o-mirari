package mirari.struct

import mirari.Site

import ru.mirari.infra.piles.PiledItem

class Entry implements PiledItem {
    String title

    String getStringId() {
        id.toString()
    }

    //List<Block> blocks

    Site owner

    Date dateCreated = new Date()
    Date lastUpdated = new Date()

    static constraints = {
    }
}
