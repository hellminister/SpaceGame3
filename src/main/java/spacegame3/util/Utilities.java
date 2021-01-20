package spacegame3.util;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

/**
 * A simple class to hold utility methods until a better place for them is decided
 */
public final class Utilities {
    private static final Logger LOG = Logger.getLogger(Utilities.class.getName());
    private Utilities(){}

    /**
     * Transfer the text from a file into a String
     * @param file The file to read
     * @return The text from the file
     */
    public static String readFile(Path file){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = reader.readLine();
            while (line != null){
                sb.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
        return sb.toString();
    }

    /**
     * Creates a file and writes the given text in it
     * @param file the file to be created
     * @param description the text to be saved in the file
     */
    public static void writeSimpleTextFile(Path file, String description) {
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.CREATE)){
            bw.write(description);
            bw.flush();
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
    }

    /**
     * Creates the given folder
     * @param path The folder to be created
     */
    public static void createFolder(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
    }

    /**
     * Bind the size of a pane (min, max, pref) to some Binding expressions
     * @param pane   The pane to set size
     * @param width  The binding expression for width
     * @param height The binding expression for height
     */
    public static void attach(Region pane, DoubleExpression width, DoubleExpression height){
        pane.maxWidthProperty().bind(width);
        pane.minWidthProperty().bind(width);
        pane.prefWidthProperty().bind(width);

        pane.maxHeightProperty().bind(height);
        pane.minHeightProperty().bind(height);
        pane.prefHeightProperty().bind(height);
    }
}
