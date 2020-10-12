package spacegame3.userinterface;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 2016-12-20.
 */
public final class ImageLibrary {
    private static final String IMAGE_LIBRARY_FILE_PATH = "/resources/commons/images/";
    private static final String IMAGE_STORY_FILE_PATH = "data/images/";

    private static final ImageLibrary library = new ImageLibrary();
    private Path storyTelling = null;

    private final Map<String, Image> theLibrary;
    private Map<String, Image> storyImages = new ConcurrentHashMap<>();

    private ImageLibrary() {
        theLibrary = new ConcurrentHashMap<>();
    }

    public static Image getImage(String imagePath) {

        return library.storyImages.computeIfAbsent(imagePath,
                key -> {
                    Image im = null;
                        try (InputStream is = Files.newInputStream(library.storyTelling.resolve(key));){
                            im = new Image(is);
                        } catch (IOException | NullPointerException e) {
                            im =
                                    library.theLibrary.computeIfAbsent(imagePath, key2 ->
                                            new Image(library.getClass().getResourceAsStream(IMAGE_LIBRARY_FILE_PATH + key2)));
                        }
                    return im;
                });
    }

    public static void setStoryTellingPath(Path storyTellingPath){
        library.storyTelling = storyTellingPath.resolve(IMAGE_STORY_FILE_PATH);
        System.out.println(library.storyTelling);
        library.storyImages = new ConcurrentHashMap<>();
    }


}
