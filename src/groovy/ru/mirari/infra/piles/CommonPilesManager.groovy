package ru.mirari.infra.piles

import java.util.List;

/**
 * @author alari
 * @since 8/26/12 12:42 AM
 */
public interface CommonPilesManager<I extends PiledItem, P extends SortablePile> {

    public put(final I item, final P pile)     ;

    public put(final String itemId, final P pile)  ;

    public remove(final I item, final P pile)      ;

    public remove(final I item, final String pileId)   ;

    public remove(final String itemId, final P pile)  ;

    public unwireItems(final P pile)             ;

    public List<String> getRelatedPiles(final P pile, double fromPosition, int lookMin, int lookMax)     ;

    public boolean hide(final I item, final P pile)        ;

    public boolean isHidden(final I item, final P pile)    ;

    public reveal(final I item, final P pile)         ;

    public List<String> drawIds(final P pile, long limit, long offset)    ;

    public long sizeOf(final P pile, long countFixed)               ;
}