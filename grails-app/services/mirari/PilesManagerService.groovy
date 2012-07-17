package mirari

import mirari.piles.PilesManager
import mirari.struct.Entry
import mirari.struct.Pile
import redis.clients.jedis.Jedis

class PilesManagerService implements PilesManager<Entry, Pile> {

    def redisService

    @Override
    void put(final Entry item, final Pile pile, boolean first) {
        redisService.withRedis { Jedis redis ->
            redis.sadd(entryPilesKey(item), pile.id)
            if (first) {
                redis.lrem pileTopKey(pile), 0, item.id
                redis.lpush pileTopKey(pile), item.id
            } else {
                redis.zadd pileCommonKey(pile), item.pilePosition, item.id
            }
        }
    }

    @Override
    void remove(final Entry item, final Pile pile) {
        redisService.withRedis { Jedis redis ->
            redis.srem(entryPilesKey(item), pile.id)
            redis.lrem pileTopKey(pile), 0, item.id
            redis.zrem pileCommonKey(pile), item.id
        }
    }

    @Override
    void delete(final Entry item) {
        redisService.withRedis { Jedis redis ->
            redis.smembers(entryPilesKey(item)).each { String pileId ->
                redis.lrem pileTopKey(pileId), 0, item.id
                redis.zrem pileCommonKey(pileId), item.id
            }
            redis.del entryPilesKey(item)
        }
    }

    @Override
    void delete(final Pile pile) {
        List<String> items = []
        redisService.withRedis { Jedis redis ->
            items.addAll(redis.lrange(pileTopKey(pile), 0, redis.llen(pileTopKey(pile))))
            items.addAll(redis.zrange(pileCommonKey(pile), 0, redis.zcard(pileCommonKey(pile))))
            items.each { String itemId ->
                redis.srem(entryPilesKey(itemId), pile.id)
            }
            redis.del pileTopKey(pile)
            redis.del pileCommonKey(pile)
        }
    }

    @Override
    List<Entry> draw(final Pile pile, long limit, long offset) {
        List<String> itemIds = []
        redisService.withRedis { Jedis redis ->
            long topCount = redis.llen(pileTopKey(pile))
            if (topCount <= offset + limit) {
                itemIds.addAll(redis.lrange(pileTopKey(pile), offset, offset + limit - 1))
            }
            if (itemIds.size() < limit) {
                itemIds.addAll(redis.zrevrange(pileCommonKey(pile), offset - topCount, limit - itemIds.size() - 1))
            }
        }
        itemIds.collect {Entry.get(it)}
    }

    @Override
    Collection<Pile> getPiles(final Entry item) {
        List<String> pilesIds = []
        redisService.withRedis { Jedis redis ->
            pilesIds = redis.smembers(entryPilesKey(item))
        }
        pilesIds.collect {Pile.get(it)}
    }


    @Override
    void setPosition(final Entry item, final Pile pile, int position) {
        // TODO: set position in a top list (at first look where is an item, then if position in top list or not, ,,,)
    }

    @Override
    void dropPosition(final Entry item, final Pile pile, boolean withTail) {
        redisService.withRedis { Jedis redis ->
            if (withTail) {
                List<String> removeIds = []
                int i = -1
                String id
                for (
                        id = redis.lindex(pileTopKey(pile), -1);
                        id && id != item.id;
                        id = redis.lindex(pileTopKey(pile), --i)
                ) {
                    removeIds.add(id)
                }
                if (id == item.id) {
                    removeIds.add(id)
                    for (String rmId in removeIds) {
                        redis.lrem(pileTopKey(pile), 0, rmId)
                        redis.zadd(pileCommonKey(pile), Entry.get(rmId)?.pilePosition, rmId)
                    }
                }
            } else {
                redis.lrem(pileTopKey(pile), 0, item.id)
                redis.zadd(pileCommonKey(pile), item.pilePosition, item.id)
            }
        }
    }

    @Override
    List<Pile> getRelatedPiles(final Pile pile, int num, int depth) {
        return null  //TODO: add related piles collecting; at first -- simply with sorted sets
    }

    private String entryPilesKey(final Entry entry) { entryPilesKey(entry.id) }

    private String pileTopKey(final Pile pile) { pileTopKey(pile.id) }

    private String pileCommonKey(final Pile pile) { pileCommonKey(pile.id) }

    private String entryPilesKey(final String entryId) { "entry:${entryId}:piles" }

    private String pileTopKey(final String pileId) { "pile:${pileId}:top" }

    private String pileCommonKey(final String pileId) { "pile:${pileId}:common" }
}
