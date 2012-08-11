package mirari.struct

import mirari.Site

import ru.mirari.infra.piles.PiledItem
import mirari.data.BlocksHolder

class Entry implements PiledItem {
    String title

    String getStringId() {
        id.toString()
    }

    Site owner

    Date dateCreated = new Date()
    Date lastUpdated = new Date()

    BlocksHolder getBlocksHolder() {
        BlocksHolder.findByEntry(this)
    }

    void setBlocksHolder(BlocksHolder blocksHolder) {
        blocksHolder.entry = this
    }

    static transients = ['blocksHolder']

    static constraints = {
    }
}
