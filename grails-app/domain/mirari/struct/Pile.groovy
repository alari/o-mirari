package mirari.struct

import mirari.Site
import ru.mirari.infra.piles.SortablePile
import mirari.util.named.TitleNamedDomain
import mirari.util.named.TitleNameSetter

class Pile implements SortablePile, TitleNamedDomain {
    String getStringId() {
        id.toString()
    }

    Site site

    String title
    String name

    Date dateCreated = new Date()
    Date lastUpdated = new Date()

    String getUrl() {
        "${site.url}/pile/${name.encodeAsURL()}"
    }

    static constraints = {
    }
}
