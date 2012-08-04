package mirari.dao.struct

import mirari.repo.struct.EntryRepo
import mirari.struct.Entry
import mirari.dao.Dao
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 6/14/12 9:00 PM
 */
@Component("entryRepo")
class EntryDao extends Dao<Entry> implements EntryRepo {
    @Override
    Entry getById(id) {
        Entry.get(id)
    }
}
