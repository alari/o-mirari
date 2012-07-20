package mirari.piles;

import java.util.LinkedList;
import java.util.List;

/**
 * @author alari
 * @since 7/20/12 10:30 PM
 */
public class RelatedPile<T extends SortablePile> {
    private T pile;
    private List<RelatedPile<T>> relatedPiles = new LinkedList<RelatedPile<T>>();

    public RelatedPile(T pile) {
        this.pile = pile;
    }

    public T getPile() {
        return pile;
    }

    public void addRelated(RelatedPile<T> relatedPile) {
        relatedPiles.add(relatedPile);
    }

    public List<RelatedPile<T>> getRelatedPiles() {
        return relatedPiles;
    }
}
