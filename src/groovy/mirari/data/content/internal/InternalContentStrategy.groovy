package mirari.data.content.internal

import mirari.data.content.ContentStrategy
import mirari.data.content.ContentHolder

/**
 * @author alari
 * @since 1/6/12 7:19 PM
 */
abstract class InternalContentStrategy extends ContentStrategy {
    boolean isExternal() {
        false
    }

    @Override
    void buildContentByUrl(ContentHolder unit, String url) {
        void
    }

    @Override
    boolean isUrlSupported(String url) {
        false
    }
}
