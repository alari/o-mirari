package mirari.data.content

/**
 * @author alari
 * @since 1/6/12 6:07 PM
 */
public enum ContentData {
    SOUND_TYPES("sound_types"),
    EXTERNAL_ID("external_id"),
    RENDER_STYLE("render_style"),

    REF_PAGE_ID("ref_page_id"),

    public final String key;

    ContentData(String key) {
        this.key = key
    }

    String getFrom(ContentHolder unit) {
        unit.getAt(key)
    }

    Set<String> getSetFrom(ContentHolder unit) {
        (Set)unit.getAt(key)
    }

    void putTo(ContentHolder unit, String value) {
        unit.putAt(key, value)
    }

    void putTo(ContentHolder unit, Collection<String> collection) {
        unit.putAt(key, collection)
    }
}