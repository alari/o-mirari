package mirari.dao.struct

import mirari.repo.struct.PileRepo
import mirari.struct.Pile
import mirari.dao.Dao
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 6/14/12 9:00 PM
 */
@Component("pileRepo")
class PileDao extends Dao<Pile> implements PileRepo {
}
