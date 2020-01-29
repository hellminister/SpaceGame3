package spacegame3.userinterface;

import javafx.scene.image.Image;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 2016-12-20.
 */
public final class ImageLibrary {
    private static final String IMAGE_LIBRARY_FILE_PATH = "/resources/commons/images/";

    private static final ImageLibrary library = new ImageLibrary();

    private final Map<String, Image> theLibrary;

    private ImageLibrary() {
        theLibrary = new ConcurrentHashMap<>();
    }

    public static Image getImage(String imagePath) {
        return library.theLibrary.computeIfAbsent(imagePath, key -> new Image(library.getClass().getResourceAsStream(IMAGE_LIBRARY_FILE_PATH + key)));
    }


}
