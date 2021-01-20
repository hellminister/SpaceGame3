package spacegame3.gamedata.player;

import spacegame3.userinterface.startscreen.QuestionBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public final class PlayerStructure {
    private static final Logger LOG = getLogger(PlayerStructure.class.getName());

    private static final String PLAYER_STRUCTURE_URL = "data/objectstructure/PlayerStructure.txt";

    private final Map<String, String> playerStringAttribute;
    private final Map<String, Set<String>> attributeValue;
    private final Map<String, Map<String, Set<String>>> linkedAttributeValue;
    private final Map<String, String> linkedAttribute;
    private final List<String> saveFileName;
    private final List<String> description;

    public PlayerStructure (Path storyPath) {
        Path structurePath = storyPath.resolve(PLAYER_STRUCTURE_URL);

        var temp_playerStringAttribute = new LinkedHashMap<String, String>();
        var temp_attributeValue = new HashMap<String, Set<String>>();
        var temp_linkedAttributeValue = new HashMap<String, Map<String, Set<String>>>();
        var temp_linkedAttribute = new HashMap<String, String>();
        var temp_saveFileName = new ArrayList<String>();
        var temp_description = new ArrayList<String>();


        try (BufferedReader reader = Files.newBufferedReader(structurePath)) {
            String line = reader.readLine();
            String[] words;
            String msg;
            String filling = "";
            int size = 0;

            while (line != null && !line.startsWith("#")) {
                words = line.split(" ?\" ?\"?");

                String[] finalWords = words;
                LOG.fine(() -> Arrays.toString(finalWords));

                switch (words[0]){
                    case "s":
                        msg = words[2];
                        temp_playerStringAttribute.put(words[1], msg);
                        break;
                    case "l":
                        msg = words[3];
                        temp_playerStringAttribute.put(words[1], msg);
                        temp_attributeValue.put(words[1], Set.of(words[2].split("#")));
                        break;
                    case "m":
                        msg = words[3];
                        temp_playerStringAttribute.put(words[1], msg);
                        temp_linkedAttribute.put(words[1], words[2]);
                        temp_linkedAttributeValue.put(words[1], new HashMap<>());
                        filling = words[1];
                        size = temp_attributeValue.get(words[2]).size();
                        break;
                    case "f":
                        msg = String.join(" ", Arrays.copyOfRange(words, 1, words.length-1));
                        temp_saveFileName.add(msg);
                        temp_saveFileName.addAll(Arrays.asList(words[words.length-1].split(",")));
                        break;
                    case "d":
                        msg = String.join(" ", Arrays.copyOfRange(words, 1, words.length-1));
                        temp_description.add(msg);
                        temp_description.addAll(Arrays.asList(words[words.length-1].split(",")));
                        break;
                    default:
                        if (!filling.isEmpty()){
                            temp_linkedAttributeValue.get(filling).put(words[0], Set.of(words[1].split("#")));
                            size--;
                            if (size <= 0){
                                filling = "";
                                size = 0;
                            }
                        } else {
                            LOG.warning(words[0] + " is untreated.");
                        }
                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            LOG.severe(ex::toString);
        }

        playerStringAttribute = Collections.unmodifiableMap(temp_playerStringAttribute);
        attributeValue = Collections.unmodifiableMap(temp_attributeValue);
        linkedAttributeValue = Collections.unmodifiableMap(temp_linkedAttributeValue);
        linkedAttribute = Collections.unmodifiableMap(temp_linkedAttribute);
        saveFileName = Collections.unmodifiableList(temp_saveFileName);
        description = Collections.unmodifiableList(temp_description);
    }

    public String getSaveFileName(Map<String, String> attribs){
        return buildString(attribs, saveFileName);
    }

    public String getDescription(Map<String, String> attribs){
        return buildString(attribs, description);
    }

    public String attribToString(Map<String, String> attribs){
        StringBuilder sb = new StringBuilder();
        attribs.forEach((key, value) -> sb.append("\"").append(key).append("\" \"").append(value).append("\"\n"));
        return sb.toString();
    }

    private static String buildString(Map<String, String> attribs, List<String> stringFormat) {
        boolean first = true;
        String format = "";
        var values = new ArrayList<String>();

        for (String s : stringFormat){
            if (first){
                format = s;
                first = false;
            } else {
                values.add(attribs.get(s));
            }
        }

        return String.format(format, values.toArray());
    }

    public void fillAttributes(Map<String, String> attribs, QuestionBox questionner){
        for (String attribute : playerStringAttribute.keySet()){


            String question = playerStringAttribute.get(attribute);
            Optional<Set<String>> possibleChoice = Optional.empty();

            Set<String> choices = attributeValue.get(attribute);
            if (choices != null && !choices.isEmpty()){
                possibleChoice = Optional.of(Collections.unmodifiableSet(choices));
            } else {
                String linked = linkedAttribute.get(attribute);
                if (linked != null && !linked.isEmpty()){
                    choices = linkedAttributeValue.get(attribute).get(attribs.get(linked));
                    if (choices != null && !choices.isEmpty()){
                        possibleChoice = Optional.of(Collections.unmodifiableSet(choices));
                    } else {
                        throw new RuntimeException(String.join(" ", "Missing elements for ", attribute, linked,
                                attribs.get(linked)));
                    }
                }
            }

            if (possibleChoice.isEmpty()){
                attribs.put(attribute, questionner.getAnswer(question));
            } else {
                attribs.put(attribute, questionner.getAnswer(question, possibleChoice.get()));
            }

        }
    }

}
