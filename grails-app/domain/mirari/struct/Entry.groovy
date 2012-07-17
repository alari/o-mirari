package mirari.struct

import mirari.Site
import mirari.data.Block
import mirari.piles.PiledItem

class Entry implements PiledItem {

    String id

    String title

    List<Block> blocks

    Site owner

    Date dateCreated
    Date lastUpdated

    public double getPilePosition() {
        (double) dateCreated.time
    }

    static constraints = {
    }
}
