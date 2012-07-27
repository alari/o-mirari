package mirari

import mirari.piles.PilesManager
import mirari.struct.Entry
import mirari.struct.Pile
import redis.clients.jedis.Jedis

class PilesManagerService implements PilesManager<Entry, Pile> {

    static transactional = false

    def redisService

    @Override
    void put(final Entry item, final Pile pile, boolean first) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.sadd(entryPilesKey(item), pile.id)
            if (first) {
                redis.lrem(pileTopKey(pile), 0, item.id)
                redis.lpush(pileTopKey(pile), item.id )
            } else {
                redis.zadd(pileCommonKey(pile), item.pilePosition, item.id)
            }
        }
    }

    @Override
    void remove(final Entry item, final Pile pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.srem(entryPilesKey(item), pile.id)
            redis.lrem pileTopKey(pile), 0, item.id
            redis.zrem pileCommonKey(pile), item.id
        }
    }

    @Override
    void delete(final Entry item) {
        if (!item) throw new IllegalArgumentException();

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
        if (!pile) throw new IllegalArgumentException();

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
        drawIds(pile, limit, offset).collect {Entry.get(it)}
    }

    @Override
    List<Pile> getRelatedPiles(final Pile pile, int num, double fromPosition, int lookMin, int lookMax) {
        List<String> pileIds = []
        redisService.withRedis {Jedis redis ->
            List<String> matchingEntries = redis.lrange(pileTopKey(pile), 0, lookMax)

            int matchingEntriesCount = redis.zcount(pileCommonKey(pile), fromPosition.toString(), "+inf")
            if(matchingEntriesCount > lookMax) matchingEntriesCount = lookMax
            if(matchingEntriesCount < lookMin) matchingEntriesCount = Math.min((long)lookMin, redis.zcard(pileCommonKey(pile)))
            matchingEntries.addAll redis.zrange(pileCommonKey(pile), 0, matchingEntriesCount)

            Map<String,Integer> piles = [:]
            matchingEntries.each {entryId->
                redis.smembers(entryPilesKey(entryId)).asList().each {entryPileId->
                    if(!piles.containsKey(entryPileId)) {
                        piles.put(entryPileId, 1)
                    } else {
                        piles[entryPileId]++
                    }
                }
            }
            piles.sort({a, b -> b.value <=> a.value}).take(num).each {pileIds.add(it.key)}
        }
        pileIds.collect {Pile.get(it)}
    }

    List<String> drawIds(final Pile pile, long limit, long offset) {
        if (!limit || !pile) throw new IllegalArgumentException();

        List<String> itemIds = []
        redisService.withRedis { Jedis redis ->
            long topCount = redis.llen(pileTopKey(pile))
            if (topCount <= offset + limit) {
                itemIds.addAll(redis.lrange(pileTopKey(pile), offset, offset + limit - 1))
            }
            if (itemIds.size() < limit) {
                itemIds.addAll(redis.zrevrange(pileCommonKey(pile), offset - topCount - 1, limit - itemIds.size() - 1))
            }
        }
        itemIds
    }

    @Override
    Collection<Pile> getPiles(final Entry item) {
        if (!item) throw new IllegalArgumentException();

        List<String> pilesIds = []
        redisService.withRedis { Jedis redis ->
            pilesIds = redis.smembers(entryPilesKey(item))
        }
        pilesIds.collect {Pile.get(it)}
    }

    @Override
    boolean inPile(final Entry item, final Pile pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        boolean i = false
        redisService.withRedis {Jedis redis ->
            i = redis.sismember(entryPilesKey(item), pile.id)
        }
        i
    }

    @Override
    long sizeOf(final Pile pile) {
        long size = 0
        redisService.withRedis {Jedis redis ->
            size = redis.llen(pileTopKey(pile)) + redis.zcard(pileCommonKey(pile))
        }
        size
    }

    @Override
    void setPosition(final Entry item, final Pile pile, int position) {
        if (!item || !pile || position == null) throw new IllegalArgumentException();

        final String topIndex = pileTopKey(pile)
        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(entryPilesKey(item), pile.id)) {
                // Not in a pile
                return;
            }
            int topCount = redis.llen(topIndex);
            // where it is?
            if (redis.zscore(pileCommonKey(pile), item.id) != null) {
                // it's in commons
                redis.zrem(pileCommonKey(pile), item.id)
                if (position > topCount) {
                    // Move the top of common pile to top list, and our item
                    redis.zrange(pileCommonKey(pile), 0, position - topCount - 2).each {
                        redis.rpush(topIndex, it)
                        redis.zrem(pileCommonKey(pile), it)
                    }
                    redis.lpush(topIndex, item.id)
                } else if (position == topCount) {
                    redis.rpush(topIndex, item.id)
                } else {
                    List<String> tailObjects = []
                    for (int i = topCount; i > position; i--) {
                        tailObjects.add redis.rpop(topIndex)
                    }
                    redis.rpush(topIndex, item.id)
                    tailObjects.reverse().each {
                        redis.rpush(topIndex, it)
                    }
                }
            } else {
                // in top list, so we should rearrange items between an old and new position
                int oldPosition = 0
                while (redis.lindex(topIndex, oldPosition) != item.id && oldPosition <= topCount) {
                    oldPosition++
                }
                if (oldPosition == position) {
                    return
                }
                int min = Math.min(oldPosition, position)
                int max = Math.max(oldPosition, position)
                int move = oldPosition > position ? -1 : 1

                List<String> ids = redis.lrange(topIndex, min, max - 1)
                for (int i = 1; i < ids.size() - 1; i++) {
                    redis.lset(topIndex, i + min + move, ids[i])
                }
                if (move > 0) {
                    redis.lset(topIndex, min, item.id)
                    redis.lset(topIndex, min + 1, ids.first())
                } else {
                    redis.lset(topIndex, max - 1, item.id)
                    redis.lset(topIndex, max - 2, ids.last())
                }
            }
        }
    }

    @Override
    void dropPosition(final Entry item, final Pile pile, boolean withTail) {
        if (!item || !pile) throw new IllegalArgumentException();

        final String topIndex = pileTopKey(pile)

        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(entryPilesKey(item), pile.id)) {
                // Not in a pile
                return;
            }
            if (withTail) {
                // Remove this item and all the following ones
                List<String> removeIds = []
                int i = -1
                String id
                for (
                        id = redis.lindex(topIndex, -1);
                        id && id != item.id;
                        id = redis.lindex(topIndex, --i)
                ) {
                    removeIds.add(id)
                }
                if (id == item.id) {
                    removeIds.add(id)
                    for (String rmId in removeIds) {
                        redis.lrem(topIndex, 0, rmId)
                        redis.zadd(pileCommonKey(pile), Entry.get(rmId)?.pilePosition, rmId)
                    }
                }
            } else {
                // Remove this item only
                redis.lrem(topIndex, 0, item.id)
                redis.zadd(pileCommonKey(pile), item.pilePosition, item.id)
            }
        }
    }

    private String entryPilesKey(final Entry entry) { entryPilesKey(entry.id) }

    private String pileTopKey(final Pile pile) { pileTopKey(pile.id) }

    private String pileCommonKey(final Pile pile) { pileCommonKey(pile.id) }

    private String entryPilesKey(final String entryId) { "entry:${entryId}:piles" }

    private String pileTopKey(final String pileId) { "pile:${pileId}:top" }

    private String pileCommonKey(final String pileId) { "pile:${pileId}:common" }
}
