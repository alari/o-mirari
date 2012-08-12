package ru.mirari.infra.piles;

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

    public void fix(T item, K pile, int position);

    public void unfix(T item, K pile, boolean withTail);

    public List<T> drawFixed(K pile, long limit, long offset);

    public List<T> draw(K pile, long limit, long offset);

    public List<K> getRelatedPiles(K pile, int num, double fromPosition, int lookMin, int lookMax);

    public boolean isFixed(T item, K pile);

    public void hide(T item, K pile);

    public boolean isHidden(T item, K pile);

    public void reveal(T item, K pile);

    public Collection<K> getPiles(T item);

    public boolean inPile(T item, K pile);

    public long sizeOf(K pile);
}
