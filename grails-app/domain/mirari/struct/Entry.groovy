package mirari.struct

import mirari.Site
import mirari.data.Block

class Entry {

    String id

    String title

    List<Block> blocks

    List<Pile> piles

    Site owner

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
