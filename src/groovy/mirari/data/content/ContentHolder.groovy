package mirari.data.content

import mirari.Site
import mirari.data.BlockContent
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 8:13 PM
 */
public interface ContentHolder {
    String getId()

    String getTitle()

    void setTitle(String s)

    BlockContent getContent()

    void setContent(BlockContent content)

    void setContentFile(FileInfo fileInfo)

    void setContentUrl(String url)

    void deleteContent()

    Site getOwner()
}