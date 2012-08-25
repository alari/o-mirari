package mirari

import grails.plugin.redis.RedisService
import mirari.struct.Entry
import mirari.struct.Pile
import ru.mirari.infra.piles.PilesSortingService
import ru.mirari.infra.piles.CommonPilesManager

class PilesManagerService extends PilesSortingService<Entry, Pile> {

    static transactional = false

    RedisService redisService

    def pilesCommonsManagerService

    @Override
    CommonPilesManager<Entry, Pile> getCommonPilesManager() {
        pilesCommonsManagerService
    }

    @Override
    protected Entry getItemById(final String id) {
        Entry.get(id)
    }

    @Override
    protected Pile getPileById(final String id) {
        Pile.get(id)
    }
}
