package spacegame3.userinterface.startscreen;

import spacegame3.gamedata.GameState;
import spacegame3.util.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Keeps the informations for a player's saves
 * Its folder path
 * Its description
 * The list of saved games
 */
public class PlayerSaveInfo {
    private static final Logger LOG = Logger.getLogger(PlayerSaveInfo.class.getName());
    private static final String DESCRIPTION_FILE = "info.txt";              // the file containing the description of this player
    private static final String ATTRIBUTE_FILE = "PlayerAttributes.txt";    // the file containing the player's attribute
    private static final String RECENT_SAVE = "recent.sav";                 // name of the auto save file
    private static final String PREVIOUS_SAVE_1 = "previous1.sav";          // keeps 3 previous save file also
    private static final String PREVIOUS_SAVE_2 = "previous2.sav";
    private static final String PREVIOUS_SAVE_3 = "previous3.sav";

    private final String playerName;
    private final Path saveFolder;
    private String description;
    private Map<String, Path> savedGames;

    /**
     * Creates a PlayerSaveInfo to be filled
     * @param name          the player's name
     * @param saveFolder    Its save folder path
     */
    public PlayerSaveInfo(String name, Path saveFolder) {
        playerName = name;
        this.saveFolder = saveFolder;
        description = null;
    }

    /**
     * Creates a PlayerSaveInfo for a new player
     * This will also create the necessary folder and files for the player
     * @param saveFolder    the saves folder path
     * @param name          the player's name
     * @param description   the player's description
     * @param attribString  the player's attributes
     */
    public PlayerSaveInfo(Path saveFolder, String name, String description, String attribString) {
        playerName = name;
        this.saveFolder = saveFolder;
        this.description = description;
        Utilities.createFolder(saveFolder);
        Utilities.writeSimpleTextFile(saveFolder.resolve(DESCRIPTION_FILE), description);
        Utilities.writeSimpleTextFile(saveFolder.resolve(ATTRIBUTE_FILE), attribString);
    }

    /**
     * @return the player's description
     */
    public String getDescription(){
        if (description == null){
            description = Utilities.readFile(saveFolder.resolve(DESCRIPTION_FILE));
        }
        return description;
    }

    public String getName() {
        return playerName;
    }

    public String getFolderNumber() {
        return saveFolder.getFileName().toString();
    }

    /**
     * @return The list of saved game names
     */
    public Set<String> getSavedGameList() {
        savedGames = new LinkedHashMap<>();

        try (Stream<Path> walk = Files.walk(saveFolder)){
            var result =
                    walk.filter(file -> file.getFileName().toString().endsWith(".sav"))
                            .sorted(Comparator.comparing( (Path path) -> {
                                                    try {
                                                        return Files.getLastModifiedTime(path);
                                                    } catch (IOException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }).reversed())
                            .collect(Collectors.toList());

            result.forEach(path -> savedGames.put(path.getFileName().toString(), path));
        } catch (IOException e) {
            LOG.severe(e::toString);
        }

        return savedGames.keySet();
    }

    /**
     * this will read th start of a save file and return a preview (ie some informations like possibly StarDate, current system etc.)
     * @param name the file's name
     * @return the file's description
     */
    public String previewFile(String name){
        Path p = savedGames.get(name);
        StringBuilder sb = new StringBuilder();

        // TODO finish this
        sb.append("finish this ").append(name);

        return sb.toString();
    }

    /**
     * Saves the given GameState to the auto save file
     * Also backups the previous save files
     * @param gameState the GameState to save
     */
    public void save(GameState gameState) {
        doBackUps(PREVIOUS_SAVE_2, PREVIOUS_SAVE_3);
        doBackUps(PREVIOUS_SAVE_1, PREVIOUS_SAVE_2);
        doBackUps(RECENT_SAVE, PREVIOUS_SAVE_1);
        save(RECENT_SAVE, gameState, StandardOpenOption.CREATE);
    }

    /**
     * Saves the given GameState in a file with the given name
     * @param filename  the file's name
     * @param gameState the GameState
     * @param soo       the open file options wanted (optional)
     * @return the success of the save
     */
    public boolean save(String filename, GameState gameState, StandardOpenOption... soo){
        try (BufferedWriter bw = Files.newBufferedWriter(saveFolder.resolve(filename), soo)){
            bw.write(gameState.toSaveState());
            bw.flush();
            return true;
        } catch (IOException e) {
            LOG.severe(e::toString);
            return false;
        }
    }

    /**
     * renames a file from old name to new name
     * @param from the file to rename
     * @param to   the new name for the file
     */
    private void doBackUps(String from, String to) {
        try {
            Path old = saveFolder.resolve(from);
            Files.move(old, old.resolveSibling(to), StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException ex) {
            LOG.info(() -> "No file to Backup\n" + ex.toString());
        } catch (IOException ex) {
            LOG.severe(ex::toString);
        } catch (InvalidPathException ex) {
            LOG.warning(ex::toString);
        }
    }

    private static final Set<String> saveAsAnswer = Set.of("Yes", "No");
    /**
     * Save a GameState to a file specified by the player
     * @param filename  The name of the save file
     * @param gameState The GameState
     * @param question  A dialog box to ask if we want to overwrite the file
     */
    public void saveAs(String filename, GameState gameState, QuestionBox question) {
        boolean success = save(filename, gameState, StandardOpenOption.CREATE_NEW);
        if (!success) {
            String answer = question.getAnswer("File already exists; Overwrite?", saveAsAnswer);
            if ("yes".equals(answer)){
                save(filename,gameState, StandardOpenOption.CREATE);
            }
        }
    }

    /**
     * @return The attributes of the player
     */
    public Map<String, String> loadPlayerAttribs() {
        Map<String, String> attribs = new LinkedHashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(saveFolder.resolve(ATTRIBUTE_FILE))) {
            String line = reader.readLine();
            while (line != null){
                String[] data = line.split("\" \"");

                attribs.put(data[0].replaceAll("\"", ""), data[1].replaceAll("\"", ""));

                line = reader.readLine();
            }
        } catch (IOException e) {
            LOG.severe(e::toString);
        }

        return attribs;
    }
}
