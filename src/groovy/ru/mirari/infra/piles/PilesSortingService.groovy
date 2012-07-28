package ru.mirari.infra.piles

import grails.plugin.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import redis.clients.jedis.Jedis

/**
 * @author alari
 * @since 7/28/12 11:21 PM
 */
abstract class PilesSortingService<I extends PiledItem, P extends SortablePile> implements PilesManager<I,P> {
    @Override
    void put(final I item, final P pile, boolean first) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.sadd(keyItemPiles(item), pile.id)
            if (first) {
                redis.lrem(keyPileTop(pile), 0, item.id)
                redis.lpush(keyPileTop(pile), item.id )
            } else {
                redis.zadd(keyPileCommon(pile), getItemPilePosition(item), item.id)
            }
        }
    }

    @Override
    void remove(final I item, final P pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.srem(keyItemPiles(item), pile.id)
            redis.lrem keyPileTop(pile), 0, item.id
            redis.zrem keyPileCommon(pile), item.id
        }
    }

    @Override
    void delete(final I item) {
        if (!item) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.smembers(keyItemPiles(item)).each { String pileId ->
                redis.lrem keyPileTop(pileId), 0, item.id
                redis.zrem keyPileCommon(pileId), item.id
            }
            redis.del keyItemPiles(item)
        }
    }

    @Override
    void delete(final P pile) {
        if (!pile) throw new IllegalArgumentException();

        List<String> items = []
        redisService.withRedis { Jedis redis ->
            items.addAll(redis.lrange(keyPileTop(pile), 0, redis.llen(keyPileTop(pile))))
            items.addAll(redis.zrange(keyPileCommon(pile), 0, redis.zcard(keyPileCommon(pile))))
            items.each { String itemId ->
                redis.srem(keyItemPiles(itemId), pile.id)
            }
            redis.del keyPileTop(pile)
            redis.del keyPileCommon(pile)
        }
    }

    @Override
    List<I> draw(final P pile, long limit, long offset) {
        drawIds(pile, limit, offset).collect {getItem(it)}
    }

    @Override
    List<P> getRelatedPiles(final P pile, int num, double fromPosition, int lookMin, int lookMax) {
        List<String> pileIds = []
        redisService.withRedis {Jedis redis ->
            List<String> matchingEntries = redis.lrange(keyPileTop(pile), 0, lookMax)

            int matchingEntriesCount = redis.zcount(keyPileCommon(pile), fromPosition.toString(), "+inf")
            if(matchingEntriesCount > lookMax) matchingEntriesCount = lookMax
            if(matchingEntriesCount < lookMin) matchingEntriesCount = Math.min((long)lookMin, redis.zcard(keyPileCommon(pile)))
            matchingEntries.addAll redis.zrange(keyPileCommon(pile), 0, matchingEntriesCount)

            Map<String,Integer> piles = [:]
            matchingEntries.each {entryId->
                redis.smembers(keyItemPiles(entryId)).asList().each {entryPileId->
                    if(!piles.containsKey(entryPileId)) {
                        piles.put(entryPileId, 1)
                    } else {
                        piles[entryPileId]++
                    }
                }
            }
            piles.sort({a, b -> b.value <=> a.value}).take(num).each {pileIds.add(it.key)}
        }
        pileIds.collect {getPile(it)}
    }

    List<String> drawIds(final P pile, long limit, long offset) {
        if (!limit || !pile) throw new IllegalArgumentException();

        List<String> itemIds = []
        redisService.withRedis { Jedis redis ->
            long topCount = redis.llen(keyPileTop(pile))
            if (topCount <= offset + limit) {
                itemIds.addAll(redis.lrange(keyPileTop(pile), offset, Math.min(offset + limit - 1, topCount)))
            }
            if (itemIds.size() < limit) {
                itemIds.addAll(redis.zrevrange(keyPileCommon(pile), Math.max(offset - topCount - 1, 0), limit - itemIds.size() - 1))
            }
        }
        itemIds
    }

    @Override
    Collection<P> getPiles(final I item) {
        if (!item) throw new IllegalArgumentException();

        List<String> pilesIds = []
        redisService.withRedis { Jedis redis ->
            pilesIds = redis.smembers(keyItemPiles(item))
        }
        pilesIds.collect {getPile(it)}
    }

    @Override
    boolean inPile(final I item, final P pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        boolean i = false
        redisService.withRedis {Jedis redis ->
            i = redis.sismember(keyItemPiles(item), pile.id)
        }
        i
    }

    @Override
    long sizeOf(final P pile) {
        long size = 0
        redisService.withRedis {Jedis redis ->
            size = redis.llen(keyPileTop(pile)) + redis.zcard(keyPileCommon(pile))
        }
        size
    }

    @Override
    void setPosition(final I item, final P pile, int position) {
        if (!item || !pile || position == null) throw new IllegalArgumentException();

        final String topIndex = keyPileTop(pile)
        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(keyItemPiles(item), pile.id)) {
                // Not in a pile
                return;
            }
            int topCount = redis.llen(topIndex);
            // where it is?
            if (redis.zscore(keyPileCommon(pile), item.id) != null) {
                // it's in commons
                redis.zrem(keyPileCommon(pile), item.id)
                if (position > topCount) {
                    // Move the top of common pile to top list, and our item
                    redis.zrange(keyPileCommon(pile), 0, position - topCount - 2).each {
                        redis.rpush(topIndex, it)
                        redis.zrem(keyPileCommon(pile), it)
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
    void dropPosition(final I item, final P pile, boolean withTail) {
        if (!item || !pile) throw new IllegalArgumentException();

        final String topIndex = keyPileTop(pile)

        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(keyItemPiles(item), pile.id)) {
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
                        redis.zadd(keyPileCommon(pile), getItemPilePosition(rmId), rmId)
                    }
                }
            } else {
                // Remove this item only
                redis.lrem(topIndex, 0, item.id)
                redis.zadd(keyPileCommon(pile), getItemPilePosition(item), item.id)
            }
        }
    }

    abstract protected I getItem(final String id)

    abstract protected P getPile(final String id)

    abstract protected double getItemPilePosition(final String id)

    abstract protected double getItemPilePosition(final I entry)

    abstract protected RedisService getRedisService()

    protected String keyItemPiles(final I entry) { keyItemPiles(entry.id) }

    protected String keyPileTop(final P pile) { keyPileTop(pile.id) }

    protected String keyPileCommon(final P pile) { keyPileCommon(pile.id) }

    protected String keyItemPiles(final String entryId) { "entry:${entryId}:piles" }

    protected String keyPileTop(final String pileId) { "pile:${pileId}:top" }

    protected String keyPileCommon(final String pileId) { "pile:${pileId}:common" }
}
