package ru.mirari.infra.piles

import grails.plugin.redis.RedisService
import redis.clients.jedis.Jedis

/**
 * @author alari
 * @since 8/26/12 12:43 AM
 */
abstract class CommonRedisPilesService<I extends PiledItem, P extends SortablePile> implements CommonPilesManager<I, P> {
    abstract protected double getItemPileScoreById(final String id, final String pileId)

    abstract protected double getItemPileScoreById(final String entryId, final P pile)

    abstract protected double getItemPileScore(final I entry, final P pile)

    abstract protected RedisService getRedisService()

    protected String keyPile(final P pile) { keyPileById(pile.stringId) }

    protected String keyPileById(final String pileId) { "pile:${pileId}:common" }

    @Override
    public put(final I item, final P pile) {
        if (!item || !pile) throw new IllegalArgumentException();

        put(item.stringId, pile)
    }

    @Override
    public put(final String itemId, final P pile) {
        redisService.zadd(keyPile(pile), getItemPileScoreById(itemId, pile), itemId)
    }

    @Override
    public remove(final I item, final P pile) {
        remove(item, pile.stringId)
    }

    @Override
    public remove(final I item, final String pileId) {
        redisService.zrem keyPileById(pileId), item.stringId
    }

    @Override
    public remove(final String itemId, final P pile) {
        redisService.zrem keyPile(pile), itemId
    }

    @Override
    public unwireItems(final P pile) {
        List<String> items = []
        redisService.withRedis { Jedis redis ->
            redis.del keyPile(pile)
        }
    }

    @Override
    public List<String> getRelatedPiles(final P pile, double fromPosition, int lookMin, int lookMax) {
        List<String> items = []
        redisService.withRedis { Jedis redis ->
            int matchingEntriesCount = redis.zcount(keyPile(pile), fromPosition.toString(), "+inf")
            if (matchingEntriesCount > lookMax) matchingEntriesCount = lookMax
            if (matchingEntriesCount < lookMin) matchingEntriesCount = Math.min((long) lookMin, redis.zcard(keyPile(pile)))
            items.addAll redis.zrange(keyPile(pile), 0, matchingEntriesCount)
        }
        items
    }

    @Override
    public boolean hide(final I item, final P pile) {
        redisService.zrem(keyPile(pile), item.stringId)
    }

    @Override
    public boolean isHidden(final I item, final P pile) {
        !redisService.zscore(keyPile(pile), item.stringId)
    }

    @Override
    public reveal(final I item, final P pile) {
        redisService.withRedis {Jedis redis ->
            redis.zadd(keyPile(pile), getItemPileScore(item, pile), item.stringId)
        }
    }

    @Override
    public List<String> drawIds(final P pile, long limit, long offset) {
        List itemIds = []
        itemIds.addAll redisService.zrevrange(keyPile(pile), offset, limit - 1)
        itemIds
    }

    @Override
    public long sizeOf(final P pile, long countFixed) {
        redisService.zcard(keyPile(pile))
    }
}
