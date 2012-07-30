package mirari.struct

import mirari.Site
import ru.mirari.infra.piles.SortablePile

class Pile implements SortablePile {
    static mapWith = "mongo"
    String id

    Site site

    String title
    String name

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
