package ru.mirari.infra.feed

/**
 * @author alari
 * @since 1/4/12 4:13 PM
 */
class FeedQuery<T> implements Iterable<T> {
    private query
    private long total
    private int page
    private int perPage
    private int pageCount

    private Pagination pagination

    FeedQuery(query) {
        this.query = query
        total = query.countAll()
    }

    FeedQuery paginate(int page, int perPage = 5) {
        if (!perPage) perPage = 5
        query = query.offset(page * perPage).limit(perPage)
        total = query.countAll()
        pagination = new Pagination((int) Math.ceil(total / perPage), page)
        this
    }

    Pagination getPagination() {
        pagination
    }

    long getTotal() {
        if (total == null) {
            total = query.countAll()
        }
        total
    }

    @Override
    Iterator<T> iterator() {
        query.fetch().iterator()
    }
}