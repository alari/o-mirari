package mirari.data.content

/**
 * @author alari
 * @since 1/6/12 6:04 PM
 */
public enum SoundType {
    MP3("mpeg", "sound.mp3"),
    WEBM("webm", "sound.webm"),
    OGG("ogg", "sound.ogg");

    static private Map<String, SoundType> byName = [:]

    static {
        for (SoundType t in SoundType.values()) byName.put(t.name, t)
    }

    final String name
    final String filename

    private SoundType(String t, String fn) {
        name = t
        filename = fn
    }

    static public SoundType forName(String name) {
        byName.get(name.toLowerCase())
    }
}