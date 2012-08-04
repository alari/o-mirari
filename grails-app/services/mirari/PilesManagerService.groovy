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
    protected double getItemPilePositionById(final String id) {
        getItemPilePosition(getItemById(id))
    }

    @Override
    protected double getItemPilePosition(final Entry entry) {
        (double) entry.dateCreated.time
    }
}
