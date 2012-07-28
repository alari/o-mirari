package mirari

import mirari.struct.Entry
import mirari.struct.Pile
import ru.mirari.infra.piles.PilesSortingService
import grails.plugin.redis.RedisService

class PilesManagerService extends PilesSortingService<Entry, Pile> {

    static transactional = false

    RedisService redisService

    @Override
    protected Entry getItem(String id) {
        Entry.get(id)
    }

    @Override
    protected Pile getPile(String id) {
        Pile.get(id)
    }

    @Override
    protected double getItemPilePosition(String id) {
        getItemPilePosition(getItem(id))
    }

    @Override
    protected double getItemPilePosition(Entry entry) {
        (double)entry.dateCreated.time
    }
}
