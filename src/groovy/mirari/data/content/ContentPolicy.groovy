package mirari.data.content

import ru.mirari.infra.file.FileInfo
import ru.mirari.infra.ApplicationContextHolder

/**
 * @author alari
 * @since 1/6/12 7:50 PM
 */
public enum ContentPolicy {
    TEXT("text"),
    IMAGE("image"),
    SOUND("sound"),
    YOUTUBE("youTube"),
    RUSSIARU("russiaRu"),

    static private final Map<String, ContentPolicy> byName = [:]

    static {
        for (ContentPolicy cp in ContentPolicy.values()) byName.put(cp.name, cp)
    }

    static ContentPolicy getByName(String name) {
        byName.get(name)
    }

    static ContentPolicy findForFileInfo(FileInfo info) {
        values().find {it.getStrategy().isContentFileSupported(info)}
    }

    static ContentPolicy findForUrl(String url) {
        values().find {it.getStrategy().isUrlSupported(url)}
    }

    ContentStrategy getStrategy() {
        if (!strategy) {
            strategy = (ContentStrategy) ApplicationContextHolder.getBean(name.concat("ContentStrategy"))
        }
        strategy
    }

    private ContentStrategy strategy
    final String name

    private ContentPolicy(String name) {
        this.name = name
    }

    String toString() {
        "Content Policy \"${name}\""
    }
}