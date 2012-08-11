package mirari.data.content.internal

import mirari.data.content.ContentHolder
import mirari.image.CommonImage
import mirari.util.image.CommonImageSrc
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileInfo
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageStorageService

/**
 * @author alari
 * @since 1/6/12 6:30 PM
 */
class ImageContentStrategy extends InternalContentStrategy {
    @Autowired
    private ImageStorageService imageStorageService

    private ImgHolder getImageHolder(ContentHolder unit) {
        new ImgHolder(unit.id)
    }

    /*@Override
    void attachContentToViewModel(ContentHolder holder, BlockVM blockVM) {
        CommonImage holder = getImageHolder(holder)
        blockVM.image = CommonImageVM.build(holder)
    }

    @Override
    void setViewModelContent(ContentHolder holder, BlockVM unitVM) {
        void
    }  */

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        if (!isContentFileSupported(fileInfo)) return;
        imageStorageService.format(getImageHolder(unit), fileInfo.file)
        unit.title = fileInfo.title
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        type.mediaType == "image"
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    @Override
    void deleteContent(ContentHolder unit) {
        imageStorageService.delete(getImageHolder(unit))
    }

    static public class ImgHolder implements ImageHolder, CommonImage {
        private final String unitId
        public final String imagesPath
        public final String imagesBucket = "mirariimages"

        ImgHolder(String unitId) {
            this.unitId = unitId
            imagesPath = "i/".concat(unitId)
        }

        @Override
        String getImagesPath() {
            imagesPath
        }

        @Override
        String getImagesBucket() {
            imagesBucket
        }

        @Override
        List<ImageFormat> getImageFormats() {
            DEFAULT_FORMATS
        }

        @Override
        ImageFormat getDefaultImageFormat() {
            IM_STANDARD
        }

        @Delegate private final CommonImageSrc src = new CommonImageSrc(this)
    }
}
