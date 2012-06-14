package mirari.data.content.internal

import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.file.FileStorageService
import mirari.data.content.ContentHolder

/**
 * @author alari
 * @since 1/6/12 5:53 PM
 */
abstract class FilesHolderContentStrategy extends InternalContentStrategy {
    @Autowired
    protected FileStorageService fileStorageService

    protected Holder getFileHolder(ContentHolder unit) {
        new Holder(unit.id)
    }

    static public class Holder implements FileHolder {
        final public String unitId
        final public String filesPath

        List<String> fileNames = []
        String filesBucket = null

        Holder(String unitId) {
            this.unitId = unitId
            this.filesPath = "f/".concat(unitId)
        }

        String getFilesPath() {
            filesPath
        }
    }
}
