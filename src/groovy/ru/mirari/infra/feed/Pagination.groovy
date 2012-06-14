package ru.mirari.infra.feed

/**
 * @author alari
 * @since 12/23/11 1:16 PM
 */
class Pagination {
    final private int total
    final private int current

    final public boolean empty

    Pagination(int total, int current) {
        this.total = total
        this.current = current > 0 && current < total ? current : 0
        empty = !this.current && total <= 1
    }

    boolean hasPrevious() {
        current > 0
    }

    int getPrevious() {
        current ? current - 1 : null
    }

    boolean hasNext() {
        total - 1 > current
    }

    int getNext() {
        hasNext() ? current + 1 : null
    }

    boolean isActive(int page) {
        page == current
    }

    IntRange getRange(int size = 2) {
        new IntRange((current - size >= 0 ? current - size : 0), (current + size < total - 1 ? current + size : total - 1))
    }
}
