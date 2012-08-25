package ru.mirari.infra.piles

import grails.plugin.redis.RedisService
import redis.clients.jedis.Jedis

/**
 * @author alari
 * @since 7/28/12 11:21 PM
 */
abstract class PilesSortingService<I extends PiledItem, P extends SortablePile> implements PilesManager<I, P> {
    abstract CommonPilesManager<I,P> getCommonPilesManager()

    @Override
    void put(final I item, final P pile, boolean first) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            if (redis.sadd(keyItemPiles(item), pile.stringId)) {
                if (first) {
                    redis.lrem(keyPileTop(pile), 0, item.stringId)
                    redis.lpush(keyPileTop(pile), item.stringId)
                } else {
                    commonPilesManager.put(item, pile)
                }
            } else {
                if (first) {
                    fix(item, pile, 0)
                } else {
                    unfix(item, pile, false)
                }
            }
        }
    }

    @Override
    void remove(final I item, final P pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.srem(keyItemPiles(item), pile.stringId)
            redis.lrem keyPileTop(pile), 0, item.stringId
        }
        commonPilesManager.remove(item, pile)
    }

    @Override
    void delete(final I item) {
        if (!item) throw new IllegalArgumentException();

        redisService.withRedis { Jedis redis ->
            redis.smembers(keyItemPiles(item)).each { String pileId ->
                redis.lrem keyPileTopById(pileId), 0, item.stringId
                commonPilesManager.remove(item, pileId)
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
            items.addAll(commonPilesManager.drawIds(pile, 0, 0))
            items.each { String itemId ->
                redis.srem(keyItemPilesById(itemId), pile.stringId)
            }
            redis.del keyPileTop(pile)
        }
        commonPilesManager.unwireItems(pile)
    }

    @Override
    List<I> draw(final P pile, long limit, long offset) {
        drawIds(pile, limit, offset).collect {getItemById(it)}
    }

    @Override
    List<P> getRelatedPiles(final P pile, int num, double fromPosition, int lookMin, int lookMax) {
        List<String> pileIds = []
        redisService.withRedis {Jedis redis ->
            List<String> matchingEntries = redis.lrange(keyPileTop(pile), 0, lookMax)

            matchingEntries.addAll commonPilesManager.getRelatedPiles(pile, fromPosition, lookMin, lookMax)

            Map<String, Integer> piles = [:]
            matchingEntries.each {entryId ->
                redis.smembers(keyItemPilesById(entryId)).asList().each {entryPileId ->
                    if (!piles.containsKey(entryPileId)) {
                        piles.put(entryPileId, 1)
                    } else {
                        piles[entryPileId]++
                    }
                }
            }
            piles.sort({a, b -> b.value <=> a.value}).take(num).each {pileIds.add(it.key)}
        }
        pileIds.collect {getPileById(it)}
    }

    @Override
    boolean isFixed(final I item, final P pile) {
        boolean fixed = false
        redisService.withRedis {Jedis redis ->
            final String topIndex = keyPileTop(pile)
            long topCount = redis.llen(keyPileTop(pile))
            int i=0
            String id
            for (
                    id = redis.lindex(topIndex, 0);
                    i<topCount && id && id != item.stringId;
                    id = redis.lindex(topIndex, i)
            ) {
                i++
            }
            fixed = (id == item.stringId)
        }
        fixed
    }

    @Override
    void hide(final I item, final P pile) {
        redisService.withRedis {Jedis redis->
            if(!commonPilesManager.hide(item, pile)) {
                redis.lrem(keyPileTop(pile), 0, item.stringId)
            }
        }
    }

    @Override
    boolean isHidden(final I item, final P pile) {
        if (!inPile(item, pile)) return false
        if (isFixed(item, pile)) return false
        commonPilesManager.isHidden(item, pile)
    }

    @Override
    void reveal(final I item, final P pile) {
        if (isHidden(item, pile)) {
            commonPilesManager.reveal(item, pile)
        }
    }

    List<String> drawIds(final P pile, long limit, long offset) {
        if (!limit || !pile) throw new IllegalArgumentException();

        List<String> itemIds = drawFixedIds(pile, limit, offset)
        redisService.withRedis { Jedis redis ->
            long topCount = redis.llen(keyPileTop(pile))
            if (itemIds.size() < limit) {
                itemIds.addAll commonPilesManager.drawIds(pile, limit - itemIds.size(), Math.max(offset - topCount - 1, 0))
            }
        }
        itemIds
    }

    @Override
    List<I> drawFixed(final P pile, long limit, long offset) {
        drawFixedIds(pile, limit, offset).collect {getItemById(it)}
    }

    List<String> drawFixedIds(final P pile, long limit, long offset) {
        if (!limit || !pile) throw new IllegalArgumentException();

        List<String> itemIds = []
        redisService.withRedis { Jedis redis ->
            long topCount = redis.llen(keyPileTop(pile))
            if (topCount <= offset + limit) {
                itemIds = redis.lrange(keyPileTop(pile), offset, Math.min(offset + limit - 1, topCount))
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
        pilesIds.collect {getPileById(it)}
    }

    @Override
    boolean inPile(final I item, final P pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        boolean i = false
        redisService.withRedis {Jedis redis ->
            i = redis.sismember(keyItemPiles(item), pile.stringId)
        }
        i
    }

    @Override
    long sizeOf(final P pile) {
        long size = 0
        redisService.withRedis {Jedis redis ->
            size = redis.llen(keyPileTop(pile))
            size += commonPilesManager.sizeOf(pile, size)
        }
        size
    }

    @Override
    void fix(final I item, final P pile, int position) {
        if (!item || !pile || position == null) throw new IllegalArgumentException();

        final String topIndex = keyPileTop(pile)
        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(keyItemPiles(item), pile.stringId)) {
                // Not in a pile
                return;
            }
            int topCount = redis.llen(topIndex);
            // where it is?
            if (isFixed(item, pile)) {
                // in top list, so we should rearrange items between an old and new position
                int oldPosition = 0
                while (redis.lindex(topIndex, oldPosition) != item.stringId && oldPosition <= topCount) {
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
                    redis.lset(topIndex, min, item.stringId)
                    redis.lset(topIndex, min + 1, ids.first())
                } else {
                    redis.lset(topIndex, max - 1, item.stringId)
                    redis.lset(topIndex, max - 2, ids.last())
                }
            } else {
                // it's in commons
                remove(item, pile)
                if (position > topCount) {
                    // Move the top of common pile to top list, and our item
                    commonPilesManager.drawIds(pile, 0, position-topCount-1).each {
                        redis.rpush(topIndex, it)
                        commonPilesManager.remove(it, pile)
                    }
                    redis.lpush(topIndex, item.stringId)
                } else if (position == topCount) {
                    redis.rpush(topIndex, item.stringId)
                } else {
                    List<String> tailObjects = []
                    for (int i = topCount; i > position; i--) {
                        tailObjects.add redis.rpop(topIndex)
                    }
                    redis.rpush(topIndex, item.stringId)
                    tailObjects.reverse().each {
                        redis.rpush(topIndex, it)
                    }
                }
            }
        }
    }

    @Override
    void unfix(final I item, final P pile, boolean withTail) {
        if (!item || !pile) throw new IllegalArgumentException();

        final String topIndex = keyPileTop(pile)

        redisService.withRedis { Jedis redis ->
            if (!redis.sismember(keyItemPiles(item), pile.stringId)) {
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
                        id && id != item.stringId;
                        id = redis.lindex(topIndex, --i)
                ) {
                    removeIds.add(id)
                }
                if (id == item.stringId) {
                    removeIds.add(id)
                    for (String rmId in removeIds) {
                        redis.lrem(topIndex, 0, rmId)
                        commonPilesManager.put(rmId, pile)
                    }
                }
            } else {
                // Remove this item only
                redis.lrem(topIndex, 0, item.stringId)
                commonPilesManager.put(item, pile)
            }
        }
    }

    abstract protected I getItemById(final String id)

    abstract protected P getPileById(final String id)

    abstract protected RedisService getRedisService()

    protected String keyItemPiles(final I entry) { keyItemPilesById(entry.stringId) }

    protected String keyPileTop(final P pile) { keyPileTopById(pile.stringId) }

    protected String keyItemPilesById(final String entryId) { "entry:${entryId}:piles" }

    protected String keyPileTopById(final String pileId) { "pile:${pileId}:top" }
}
