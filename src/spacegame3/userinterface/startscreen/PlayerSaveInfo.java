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

public class PlayerSaveInfo {
    private static final Logger LOG = Logger.getLogger(PlayerSaveInfo.class.getName());
    private static final String DESCRIPTION_FILE = "info.txt";
    private static final String ATTRIBUTE_FILE = "PlayerAttributes.txt";
    private static final String RECENT_SAVE = "recent.sav";
    private static final String PREVIOUS_SAVE_1 = "previous1.sav";
    private static final String PREVIOUS_SAVE_2 = "previous2.sav";
    private static final String PREVIOUS_SAVE_3 = "previous3.sav";

    private final String playerName;
    private final Path saveFolder;
    private String description;
    private Map<String, Path> savedGames;

    public PlayerSaveInfo(String name, Path saveFolder) {
        playerName = name;
        this.saveFolder = saveFolder;
        description = null;
    }

    public PlayerSaveInfo(Path saveFolder, String name, String description, String attribString) {
        playerName = name;
        this.saveFolder = saveFolder;
        this.description = description;
        Utilities.createFolder(saveFolder);
        Utilities.writeSimpleTextFile(saveFolder.resolve(DESCRIPTION_FILE), description);
        Utilities.writeSimpleTextFile(saveFolder.resolve(ATTRIBUTE_FILE), attribString);
    }

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

    public String previewFile(String name){
        Path p = savedGames.get(name);
        StringBuilder sb = new StringBuilder();

        // TODO finish this
        sb.append("finish this ").append(name);

        return sb.toString();
    }

    public void save(GameState gameState) {
        doBackUps(PREVIOUS_SAVE_2, PREVIOUS_SAVE_3);
        doBackUps(PREVIOUS_SAVE_1, PREVIOUS_SAVE_2);
        doBackUps(RECENT_SAVE, PREVIOUS_SAVE_1);
        save(RECENT_SAVE, gameState, StandardOpenOption.CREATE);
    }

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
    public void saveAs(String filename, GameState gameState, QuestionBox question) {
        boolean success = save(filename, gameState, StandardOpenOption.CREATE_NEW);
        if (!success) {
            String answer = question.getAnswer("File already exists; Overwrite?", saveAsAnswer);
            if ("yes".equals(answer)){
                save(filename,gameState, StandardOpenOption.CREATE);
            }
        }
    }

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
