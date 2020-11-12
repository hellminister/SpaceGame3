package spacegame3.gamedata.player.currencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

/**
 * Contains the list of currencies used in this story as well as any constraint it has
 */
public class CurrenciesStructure {
    private static final Logger LOG = Logger.getLogger(CurrenciesStructure.class.getName());
    private static final String CURRENCIES_URL = "data/objectstructure/currencies.txt";

    private final Map<String, CurrencyConstraint> currencies;


    public CurrenciesStructure(Path storyPath) {
        this.currencies = new HashMap<>();

        Path structurePath = storyPath.resolve(CURRENCIES_URL);

        try (BufferedReader reader = Files.newBufferedReader(structurePath)) {
            String line = reader.readLine();
            String[] words;
            CurrencyConstraint cc = null;

            while(line != null && !line.startsWith("#")){
                words = line.split(" ");

                if (words.length == 1){
                    currencies.put(words[0], new NoConstraint());
                } else if (words.length > 0){
                    String[] constraint;
                    for (int i = 1; i < words.length; i++) {
                        constraint = words[i].split(":");
                        try {
                            switch (constraint[0]) {
                                case "max" -> cc = new MaxConstraint(cc, Integer.parseInt(constraint[1]));
                                case "maxR" -> cc = new MaxRevertConstraint(cc, Integer.parseInt(constraint[1]));
                                case "min" -> cc = new MinConstraint(cc, Integer.parseInt(constraint[1]));
                                case "minR" -> cc = new MinRevertConstraint(cc, Integer.parseInt(constraint[1]));
                                case "complex" -> cc = new ComplexConstraint(cc, words[i]);
                                default -> {
                                    String[] finalConstraint = constraint;
                                    LOG.severe(() -> finalConstraint[0] + " is untreatable " + Arrays.toString(finalConstraint));
                                }
                            }
                        } catch (Exception e){
                            LOG.severe(e::toString);
                        }

                    }
                    currencies.put(words[0], cc);
                }

                cc = null;
                line = reader.readLine();
            }

        } catch (IOException ex) {
            LOG.severe(ex::toString);
        }
    }

    /**
     * generates the currencies data for the player
     * @return the Currencies
     */
    public Currencies generate(){
        var map = new HashMap<String, Currency>();

        currencies.forEach((s, cc) -> map.put(s, new Currency(cc)));

        return new Currencies(map);
    }
}
