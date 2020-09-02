package spacegame3.gamedata.objectstructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public class CelestialBodyStructure {
    private static final Logger LOG = Logger.getLogger(CelestialBodyStructure.class.getName());
    private static final String CELESTIAL_BODY_STRUCTURE_URL = "data/objectstructure/CelestialBodyStructure.txt";

    private final Map<String, Set<String>> attributeValue;

    public CelestialBodyStructure(Path storyPath) {
        Path structurePath = storyPath.resolve(CELESTIAL_BODY_STRUCTURE_URL);

        var temp_attributeValue = new HashMap<String, Set<String>>();
        try (BufferedReader reader = Files.newBufferedReader(structurePath)) {
            String line = reader.readLine();
            String[] words;
            String[] values;

            while (line != null && !line.startsWith("#")) {
                words = line.split(":");
                values = words[1].split(",");

                temp_attributeValue.put(words[0], Set.of(values));

                line = reader.readLine();
            }
        } catch (IOException ex) {
            LOG.severe(ex::toString);
        }

        attributeValue = Collections.unmodifiableMap(temp_attributeValue);
    }

    public boolean validateValue(String attrib, String value){
        return attributeValue.get(attrib).contains(value);
    }
}
