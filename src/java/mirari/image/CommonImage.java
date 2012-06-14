package mirari.image;

import ru.mirari.infra.image.ImageCropPolicy;
import ru.mirari.infra.image.ImageFormat;
import ru.mirari.infra.image.ImageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author alari
 * @since 3/6/12 8:27 PM
 */
public interface CommonImage {
    public static final ImageFormat IM_MAX = new ImageFormat("1920*1920", "im-max", ImageCropPolicy.NONE, ImageType.JPG);

    public static final ImageFormat IM_STANDARD = new ImageFormat("600*500", "im-standard", ImageCropPolicy.NONE, ImageType.JPG);

    public static final ImageFormat IM_MEDIUM = new ImageFormat("180*180", "im-medium", ImageCropPolicy.CENTER, ImageType.JPG);
    public static final ImageFormat IM_SMALL = new ImageFormat("120*120", "im-small", ImageCropPolicy.CENTER, ImageType.JPG);
    public static final ImageFormat IM_THUMB = new ImageFormat("90*90", "im-thumb", ImageCropPolicy.CENTER, ImageType.JPG);

    public static final List<ImageFormat> DEFAULT_FORMATS = new ArrayList<ImageFormat>(Arrays.asList(IM_MAX, IM_STANDARD, IM_MEDIUM, IM_SMALL, IM_THUMB));

    public String getThumbSrc();

    public String getSmallSrc();

    public String getMediumSrc();

    public String getStandardSrc();

    public String getMaxSrc();
}
