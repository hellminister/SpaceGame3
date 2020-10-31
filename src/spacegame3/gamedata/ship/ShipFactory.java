package spacegame3.gamedata.ship;

import spacegame3.gamedata.StoryTellingScheme;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ShipFactory {
    private static final Logger LOG = Logger.getLogger(ShipFactory.class.getName());

    private static final String shipsPath = "data/ships/";

    private final StoryTellingScheme story;


    private final Map<String, ShipBlueprint> blueprints;

    public ShipFactory(StoryTellingScheme story) {
        this.story = story;
        this.blueprints = new HashMap<>();
    }

    public Ship getShip(String name, String variant){
        return blueprints.computeIfAbsent(name, this::loadShipBlueprint).buildShip(variant);
    }

    private ShipBlueprint loadShipBlueprint(String name) {
        Path systemPath = story.getStoryPath().resolve(shipsPath + name + ".txt");

        ShipBlueprint blueprint = null;

        try (BufferedReader read = Files.newBufferedReader(systemPath)) {

            String line = read.readLine();
            while (line != null){
                if (!line.isBlank() && !line.startsWith("#")) {
                    // TODO read and load data
                }
                line = read.readLine();
            }

            blueprint = new ShipBlueprint();


        } catch (IOException e) {
            LOG.severe(() -> "Trouble loading ship " + name + " " + e.toString());
        }

        return blueprint;
    }

    private static class ShipBlueprint {
        public Ship buildShip(String variant) {
            return new Ship();
        }
    }
}
