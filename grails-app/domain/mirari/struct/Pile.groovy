package mirari.struct

import mirari.Site
import ru.mirari.infra.piles.SortablePile

class Pile implements SortablePile {
    String getStringId() {
        id.toString()
    }

    Site site

    String title
    String name

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
