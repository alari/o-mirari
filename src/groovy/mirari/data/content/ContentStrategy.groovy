package mirari.data.content

import ru.mirari.infra.file.FileInfo
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 1/6/12 5:35 PM
 */
@Component
abstract class ContentStrategy {
    //abstract void attachContentToViewModel(ContentHolder holder, BlockVM blockVM)

    //abstract void setViewModelContent(ContentHolder holder, BlockVM blockVM)

    void setContentFile(ContentHolder unit, FileInfo fileInfo){}

    boolean isContentFileSupported(FileInfo info){false}

    void saveContent(ContentHolder unit){}

    void deleteContent(ContentHolder unit){}

    boolean isInternal() {
        !external
    }

    boolean isEmpty(ContentHolder unit) {
        false
    }

    abstract boolean isExternal()

    abstract void buildContentByUrl(ContentHolder unit, String url);

    abstract boolean isUrlSupported(String url);
}
