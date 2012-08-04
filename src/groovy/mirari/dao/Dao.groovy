package mirari.dao

import mirari.repo.Repo

/**
 * @author alari
 * @since 5/4/12 6:05 PM
 */
abstract class Dao<T> implements Repo<T> {
    boolean save(T o) {
        o.save()
    }

    void delete(T o){
        o.delete()
    }
}
