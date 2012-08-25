package mirari

import ru.mirari.infra.piles.CommonRedisPilesService
import mirari.struct.Pile
import mirari.struct.Entry
import grails.plugin.redis.RedisService

class PilesCommonsManagerService extends CommonRedisPilesService<Entry,Pile> {

    RedisService redisService

    static transactional = false

    private Entry getItemById(final String id) {
        Entry.get(id)
    }

    private Pile getPileById(final String id) {
        Pile.get(id)
    }

    @Override
    protected double getItemPileScoreById(final String id, final String pileId) {
        getItemPileScore(getItemById(id), getPileById(pileId))
    }

    @Override
    protected double getItemPileScoreById(final String entryId, final Pile pile) {
        getItemPileScore(getItemById(entryId), pile)
    }

    @Override
    protected double getItemPileScore(final Entry entry, final Pile pile) {
        (double) entry.dateCreated.time
    }
}