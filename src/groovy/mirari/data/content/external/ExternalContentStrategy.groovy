package mirari.data.content.external

import ru.mirari.infra.file.FileInfo
import mirari.data.content.ContentStrategy
import mirari.data.content.ContentHolder
import mirari.data.content.ContentData

/**
 * @author alari
 * @since 1/6/12 6:47 PM
 */
abstract class ExternalContentStrategy extends ContentStrategy {
    /*@Override
    void attachContentToViewModel(ContentHolder holder, BlockVM blockVM) {
        blockVM.params.put("externalId", getExternalId(holder))
    }

    @Override
    void setViewModelContent(ContentHolder holder, BlockVM blockVM) {
        if (blockVM.params?.externalUrl && isUrlSupported(blockVM.params.externalUrl)) {
            buildContentByUrl(holder, blockVM.params.externalUrl)
        }
    }   */

    @Override
    boolean isExternal() {
        true
    }

    @Override
    void deleteContent(ContentHolder unit) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        void
    }

    @Override
    boolean isContentFileSupported(FileInfo info) {
        false
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    void setExternalId(ContentHolder unit, String id) {
        ContentData.EXTERNAL_ID.putTo(unit, id)
    }

    String getExternalId(ContentHolder unit) {
        ContentData.EXTERNAL_ID.getFrom(unit)
    }
}
