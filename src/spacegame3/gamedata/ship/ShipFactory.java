package spacegame3.gamedata.ship;

import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;
import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.userinterface.ImageLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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

        ShipBlueprint blueprint = new ShipBlueprint();

        try (BufferedReader read = Files.newBufferedReader(systemPath)) {

            String variant = "";
            String line = read.readLine();
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            while (line != null){
                if (!line.isBlank() && !line.startsWith("#")) {
                    String[] split = line.split(" ", 2);

                    if ("variant".equals(split[0])){
                        finalizeSection(data, variant, blueprint);
                        data = new HashMap<>();
                        variant = split[1];
                    } else {
                        data.put(split[0], split[1]);
                    }
                }
                line = read.readLine();
            }

            finalizeSection(data, variant, blueprint);

            // TODO treat malformed files

        } catch (IOException e) {
            LOG.severe(() -> "Trouble loading ship " + name + " " + e.toString());
        }

        return blueprint;
    }

    private void finalizeSection(Map<String, String> data, String variant, ShipBlueprint blueprint) {
        if ("".equals(variant)){
            LOG.info(() -> "ImageUrl " + data.get("icon"));
            Image image = ImageLibrary.getImage(data.get("icon"));
            String name = data.get("name");
            Chassis chassis = new Chassis(image, name, data);
            blueprint.setChassis(chassis);
        } else {
            LoadOut loadout = new LoadOut(data);
            blueprint.addVariant(variant, loadout);
        }
    }

    private static class ShipBlueprint {
        private Chassis chassis;

        private Map<String, LoadOut> variants;

        public ShipBlueprint(){
            variants = new HashMap<>();
        }

        public Ship buildShip(String variant) {
            Ship ship = new Ship(chassis);
            LoadOut loadOut = variants.getOrDefault(variant, empty);
            return ship;
        }

        public void setChassis(Chassis chassis) {
            this.chassis = chassis;
        }

        boolean hasChassis(){
            return chassis != null;
        }

        public void addVariant(String variant, LoadOut loadout) {
            variants.put(variant, loadout);
        }
    }

    private static final LoadOut empty = new LoadOut();

    /**
     * This class lists the outfits, weapons, cargo, etc. that the variant possess
     */
    private static class LoadOut {


        public LoadOut(Map<String, String> data) {
            // TODO treat the data to generate the load out of a variant
        }

        public LoadOut() {

        }
    }
}
