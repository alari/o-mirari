package mirari.struct

import mirari.Site
import mirari.piles.SortablePile

class Pile implements SortablePile {

    String id

    Site site

    String title
    String name

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
