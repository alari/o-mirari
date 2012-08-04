package mirari.repo

/**
 * @author alari
 * @since 5/4/12 6:03 PM
 */
public interface Repo<T> {
    boolean save(T o)
    void delete(T o)
}