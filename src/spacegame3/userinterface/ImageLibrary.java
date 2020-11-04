package spacegame3.userinterface;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by hellminister on 2016-12-20.
 *
 * This class serves as an image library so an image is loaded only once
 *
 * It will first look in the chosen story folder for the image before looking in the commons folder
 */
public final class ImageLibrary {
    private static final Logger LOG = Logger.getLogger(ImageLibrary.class.getName());

    private static final String IMAGE_LIBRARY_FILE_PATH = "/resources/commons/images/";
    private static final String IMAGE_STORY_FILE_PATH = "data/images/";

    private static final ImageLibrary library = new ImageLibrary();
    private Path storyTelling = null;

    // stores the Images from the common resources folder
    private final Map<String, Image> theLibrary;

    // stores the Images that were asked from the story resources if present
    // else from the common resources folder
    private Map<String, Image> storyImages;

    private ImageLibrary() {
        theLibrary = new ConcurrentHashMap<>();
        storyImages = new ConcurrentHashMap<>();
    }

    /**
     * Returns an Image from a given name first looking in the story resources folder
     * and if absent looking in the common resources folder
     *
     * @param imagePath the image path and name following the images folder
     * @return the asked Image (might return null if the file doesnt exist)
     */
    public static Image getImage(String imagePath) {

        return library.storyImages.computeIfAbsent(imagePath,
                key -> {
                    Image im;
                        try (InputStream is = Files.newInputStream(library.storyTelling.resolve(key))){
                            im = new Image(is);
                        } catch (IOException | NullPointerException e) {
                            im =
                                    library.theLibrary.computeIfAbsent(imagePath, key2 ->
                                            new Image(library.getClass().getResourceAsStream(IMAGE_LIBRARY_FILE_PATH + key2)));
                        }
                    return im;
                });
    }

    /**
     * Sets the library story path to the given path if it is different to the previous one
     * if different, also creates an empty library
     *
     * @param storyTellingPath The path to the story resources
     */
    public static void setStoryTellingPath(Path storyTellingPath){
        Path newPath = storyTellingPath.resolve(IMAGE_STORY_FILE_PATH);
        if (!newPath.equals(library.storyTelling)) {
            library.storyTelling = storyTellingPath.resolve(IMAGE_STORY_FILE_PATH);
            LOG.info(() -> library.storyTelling.toString());
            library.storyImages = new ConcurrentHashMap<>();
        }
    }


}
