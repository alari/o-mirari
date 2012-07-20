package mirari.piles;

import java.util.Collection;
import java.util.List;

/**
 * @author alari
 * @since 7/15/12 11:36 PM
 */
public interface PilesManager<T extends PiledItem, K extends SortablePile> {
    public void put(T item, K pile, boolean first);

    public void remove(T item, K pile);

    public void delete(T item);

    public void delete(K pile);

    public void setPosition(T item, K pile, int position);

    public void dropPosition(T item, K pile, boolean withTail);

    public List<T> draw(K pile, long limit, long offset);

    public List<RelatedPile<K>> getRelatedPiles(K pile, int num, int depth);

    public Collection<K> getPiles(T item);

    public boolean inPile(T item, K pile);
}
