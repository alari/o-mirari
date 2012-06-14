package mirari.data.content.external

import mirari.data.content.ContentHolder

/**
 * @author alari
 * @since 1/6/12 7:33 PM
 */
class RussiaRuContentStrategy extends ExternalContentStrategy {
    @Override
    void buildContentByUrl(ContentHolder unit, String url) {
        if (!isUrlSupported(url)) return;
        URL u = new URL(url)
        //http://russia.ru/video/diskurs_12854/
        // TODO: validate characters in external id!
        setExternalId(unit, u.path.substring(7, u.path.size() - 1))
    }

    @Override
    boolean isUrlSupported(String url) {
        URL u = new URL(url)
        u.host in ["russia.ru", "tv.russia.ru", "www.russia.ru"]
    }
}
