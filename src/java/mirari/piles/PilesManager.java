package mirari.piles;

import java.util.List;

/**
 * @author alari
 * @since 7/15/12 11:36 PM
 */
public interface PilesManager {
    public void put(PiledItem item, SortablePile pile, boolean first);

    public void remove(PiledItem item, SortablePile pile);

    public void delete(PiledItem item);

    public void delete(SortablePile pile);

    public void setPosition(PiledItem item, SortablePile pile, int position);

    public void dropPosition(PiledItem item, SortablePile pile, boolean withTail);

    public List<? extends PiledItem> draw(SortablePile pile, int num, int offset);

    public List<? extends SortablePile> getRelatedPiles(SortablePile pile, int num, int depth);
}
