package mirari.repo.struct

import mirari.repo.Repo
import mirari.struct.Pile
import mirari.Site

/**
 * @author alari
 * @since 6/14/12 8:52 PM
 */
public interface PileRepo extends Repo<Pile> {
    Pile getById(id)
    Pile getBySiteAndTitle(Site site, String title)
    Pile findBySiteAndName(Site site, String name)
}