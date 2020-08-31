package spacegame3.gamedata;

import spacegame3.gamedata.player.Player;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.userinterface.startscreen.PlayerList;
import spacegame3.userinterface.startscreen.PlayerSaveInfo;
import spacegame3.userinterface.startscreen.QuestionBox;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GameScheme {
    private static final Logger LOG = Logger.getLogger(GameScheme.class.getName());

    private final StoryTellingScheme storyTellingScheme;

    private PlayerList playerList;
    private PlayerSaveInfo currentPlayer;

    private GameState gameState;

    public GameScheme(Path resourcesPath) {
        storyTellingScheme = new StoryTellingScheme(resourcesPath);
        this.playerList = null;
    }

    public void setCurrentPlayer(PlayerSaveInfo selectedPlayer) {
        currentPlayer = selectedPlayer;
    }

    public PlayerList getPlayerList(){
        if (playerList == null) {
            playerList = new PlayerList(this);
        }
        return playerList;
    }

    public PlayerSaveInfo getCurrentPlayer(){
        return currentPlayer;
    }

    public void save(){
        currentPlayer.save(gameState);
    }

    public void saveAs(String filename, QuestionBox question){
        currentPlayer.saveAs(filename, gameState, question);
    }

    public String getStoryName() {
        return storyTellingScheme.getStoryName();
    }

    public String getDescription() {
        return storyTellingScheme.getDescription();
    }

    public void createNewPlayer(QuestionBox questionner) {
        Map<String, String> playerAttribs = new HashMap<>();
        storyTellingScheme.getPlayerStructure().fillAttributes(playerAttribs, questionner);
        getPlayerList().addPlayer(playerAttribs);
    }

    public Path getStoryPath() {
        return storyTellingScheme.getStoryPath();
    }

    public StoryTellingScheme getStoryTellingScheme(){
        return storyTellingScheme;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        gameState.setGameScheme(this);
    }

    public void newGameState(Player player) {
        gameState = new GameState(player);
        gameState.setGameScheme(this);

        storyTellingScheme.startState(gameState);
    }

    public GameState getGameState() {
        return gameState;
    }
}
