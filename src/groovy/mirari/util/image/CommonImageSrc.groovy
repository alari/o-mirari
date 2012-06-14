package mirari.util.image

import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.image.ImageHolder
import mirari.image.CommonImage
import ru.mirari.infra.ApplicationContextHolder

/**
 * @author alari
 * @since 3/6/12 7:11 PM
 */
class CommonImageSrc implements CommonImage{
    private final ImageHolder image

    private static ImageStorageService imageStorageService

    static {
        imageStorageService = (ImageStorageService)ApplicationContextHolder.getBean("imageStorageService")
    }

    CommonImageSrc(final ImageHolder image) {
        this.image = image
    }

    @Override
    String getMediumSrc() {
        imageStorageService.getUrl(image, IM_MEDIUM)
    }

    @Override
    String getStandardSrc() {
        imageStorageService.getUrl(image, IM_STANDARD)
    }

    @Override
    String getMaxSrc() {
        imageStorageService.getUrl(image, IM_MAX)
    }

    @Override
    String getThumbSrc() {
        imageStorageService.getUrl(image, IM_THUMB)
    }

    @Override
    String getSmallSrc() {
        imageStorageService.getUrl(image, IM_SMALL)
    }

}
