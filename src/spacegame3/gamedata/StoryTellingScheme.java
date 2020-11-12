package spacegame3.gamedata;

import spacegame3.gamedata.player.currencies.CurrenciesStructure;
import spacegame3.gamedata.systems.CelestialBodyStructure;
import spacegame3.gamedata.player.PlayerStructure;
import spacegame3.gamedata.ship.ShipFactory;
import spacegame3.gamedata.systems.Universe;
import spacegame3.gamedata.time.StarDateFormatter;
import spacegame3.userinterface.planetscreen.PlanetScreenBarMaker;
import spacegame3.util.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class StoryTellingScheme {
    private static final Logger LOG = Logger.getLogger(StoryTellingScheme.class.getName());

    private static final String DESCRIPTION_FILE = "info.txt";
    private static final String START_FILE = "data/start.txt";

    private final Path resourcesPath;
    private String description;
    private final String storyName;
    private PlayerStructure playerStructure;
    private CurrenciesStructure currenciesStructure;
    private Universe universe;
    private final Map<String, StarDateFormatter> timeFormatters;
    private CelestialBodyStructure celestialBodyStructure;
    private PlanetScreenBarMaker psbm;
    private ShipFactory shipFactory;

    public StoryTellingScheme(Path resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.description = null;
        this.playerStructure = null;
        this.storyName = resourcesPath.getFileName().toString();
        this.timeFormatters = new HashMap<>();
        this.shipFactory = null;
    }

    public PlanetScreenBarMaker getPlanetScreenBarMaker(){
        if (psbm == null){
            psbm = new PlanetScreenBarMaker(resourcesPath);
        }
        return psbm;
    }

    public StarDateFormatter getFormatter(String calendarName){
        StarDateFormatter formatter = timeFormatters.get(calendarName);
        if (formatter == null){
            formatter = StarDateFormatter.build(resourcesPath, calendarName);
            timeFormatters.put(calendarName, formatter);
        }
        return formatter;
    }

    public PlayerStructure getPlayerStructure(){
        if (playerStructure == null){
            playerStructure = new PlayerStructure(resourcesPath);
        }
        return playerStructure;
    }

    public CurrenciesStructure getCurrenciesStructure(){
        if (currenciesStructure == null){
            currenciesStructure = new CurrenciesStructure(resourcesPath);
        }
        return currenciesStructure;
    }

    public CelestialBodyStructure getCelestialBodyStructure(){
        if (celestialBodyStructure == null){
            celestialBodyStructure = new CelestialBodyStructure(resourcesPath);
        }
        return celestialBodyStructure;
    }

    public Universe getUniverse(){
        if (universe == null){
            universe = new Universe(this);
        }
        return universe;
    }

    public ShipFactory getShipFactory() {
        if (shipFactory == null){
            shipFactory = new ShipFactory(this);
        }
        return shipFactory;
    }

    public String getDescription() {
        if (description == null){
            description = Utilities.readFile(resourcesPath.resolve(DESCRIPTION_FILE));
        }
        return description;
    }

    public String getStoryName(){
        return storyName;
    }

    public Path getStoryPath() {
        return resourcesPath;
    }

    public void startState(GameState gameState) {
        try (BufferedReader br = Files.newBufferedReader(resourcesPath.resolve(START_FILE))){
            String line = br.readLine();

            while (line != null){
                String[] split = line.split(":");
                switch (split[0]){
                    case "InSystem" -> gameState.setSystem(getUniverse().getStarSystem(split[1]));
                    case "OnPlanet" -> gameState.setPlanet(split[1]);
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.severe(() -> "Problem while loading start.txt file : " + e.toString());
        }
    }


}
