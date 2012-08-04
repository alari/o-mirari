package mirari.dao.struct

import mirari.repo.struct.PileRepo
import mirari.struct.Pile
import mirari.dao.Dao
import org.springframework.stereotype.Component
import mirari.Site
import mirari.util.named.TitleNameSetter

/**
 * @author alari
 * @since 6/14/12 9:00 PM
 */
@Component("pileRepo")
class PileDao extends Dao<Pile> implements PileRepo {
    @Override
    Pile getBySiteAndTitle(final Site site, final String title) {
        Pile pile = Pile.findBySiteAndTitle(site, title.trim())
        if(!pile) {
            pile = new Pile()
            pile.site = site
            pile.title = title
            save(pile)
        }
        pile
    }

    @Override
    Pile findBySiteAndName(final Site site, final String name) {
        Pile.findBySiteAndName(site, name)
    }

    @Override
    Pile getById(id) {
        Pile.get(id)
    }

    @Override
    boolean save(Pile pile) {
        TitleNameSetter.setNameFromTitle(pile)
        super.save(pile)
    }
}
