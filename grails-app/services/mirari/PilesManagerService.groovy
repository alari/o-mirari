package mirari

import grails.plugin.redis.RedisService
import mirari.struct.Entry
import mirari.struct.Pile
import ru.mirari.infra.piles.PilesSortingService

class PilesManagerService extends PilesSortingService<Entry, Pile> {

    static transactional = false

    RedisService redisService

    @Override
    protected Entry getItemById(final String id) {
        Entry.get(id)
    }

    @Override
    protected Pile getPileById(final String id) {
        Pile.get(id)
    }

    @Override
    protected double getItemPilePositionById(final String id, final String pileId) {
        getItemPilePosition(getItemById(id), getPileById(pileId))
    }

    @Override
    protected double getItemPilePositionById(final String entryId, final Pile pile) {
        getItemPilePosition(getItemById(entryId), pile)
    }

    @Override
    protected double getItemPilePosition(final Entry entry, final Pile pile) {
        (double) entry.dateCreated.time
    }
}
