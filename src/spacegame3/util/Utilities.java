package spacegame3.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

public final class Utilities {
    private static final Logger LOG = Logger.getLogger(Utilities.class.getName());
    private Utilities(){}

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

    public static void writeSimpleTextFile(Path file, String description) {
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.CREATE)){
            bw.write(description);
            bw.flush();
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
    }

    public static void createFolder(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
    }
}
