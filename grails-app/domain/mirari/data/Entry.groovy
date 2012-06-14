package mirari.data

import mirari.Site

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
