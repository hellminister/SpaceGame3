package spacegame3.userinterface.planetscreen;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import spacegame3.gamedata.time.StarDateFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * This class helps generate the text to be shown on the PlanetScreen at the top and the bottom
 * The text file STORYFOLDER/data/gui/planetscreen.txt is used to determine what is shown and this class transform that file to actual interface
 *
 * the text file has 2 sections a TopBar and a BottomBar
 * each section is comprised of lines describing a label
 * a line is of format column,row "text" "arguments"
 * there are 4 columns available for the top bar and 3 columns for the bottom bar
 * the "text" part uses the java String format
 * the "argument" part uses this format object:property with 1 exceptions (for the StarDate)
 *
 * file starting with # are not treated. The # can be used to place comments in the text file
 *
 * the possible objects are :
 *                  cb : this is the celestial body shown by the PlanetScreen
 *                  ss : this is the star system containing the planet
 *                  stardate : this is the current time
 *
 *                  the format for stardate is :stardate:calendarName:time_format
 *                  time_format is one of the value defined in the calendarName file
 *                  a small exemple of a file defining a top bar :
 *
 *                  TopBar
 *                  0,0 "Currently on %s %s of the %s system" "cb:type cb:name ss:name"
 *                  1,0 "" ""
 *                  2,0 "Stardate - %s" "stardate:iso8601:date_time_show"
 *
 */
public class PlanetScreenBarMaker {
    private static final Logger LOG = getLogger(PlanetScreenBarMaker.class.getName());
    private static final String PLANET_SCREEN_URL = "data/gui/planetscreen.txt";

    private final List<LabelCreator> topBar;
    private final List<LabelCreator> bottomBar;

    /**
     * Loads the informations to create the top and bottom bar for the given story
     * @param storyPath The path to the story's folder
     */
    public PlanetScreenBarMaker(Path storyPath){
        topBar = new LinkedList<>();
        bottomBar = new LinkedList<>();

        List<LabelCreator> section = null;

        try (BufferedReader br = Files.newBufferedReader(storyPath.resolve(PLANET_SCREEN_URL))){
            String line = br.readLine();

            while (line != null && !line.startsWith("#")){
                if (line.equals("TopBar")){
                    section = topBar;
                } else if (line.equals("BottomBar")){
                    section = bottomBar;
                } else {
                    String[] split = line.split(" \"");
                    String[] pos = split[0].split(",");
                    int col = Integer.parseInt(pos[0]);
                    int row = Integer.parseInt(pos[1]);

                    if (col == 3 && row == 0 && section == bottomBar){
                        LOG.warning(() -> "row 0, column 3 is already used, cannot make this label");
                    } else {
                        // desired effect if section is null, which might happen if the file is malformed
                        section.add(new LabelCreator(col, row,
                                split[1].replaceAll("\"", ""),
                                split[2].replaceAll("\"", "")));}
                }


                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.severe(() -> "problem reading file " + e.toString());
        } catch (NullPointerException e) {
            LOG.severe(() -> "got a null pointer, file is probably malformed");
        }
    }

    /**
     * Sets the top bar of the planet screen
     * @param ps The PlanetScreen
     */
    public void updateTopBar(PlanetScreen ps){
        for (LabelCreator lc : topBar){
            ps.setInTopGrid(lc.getColumnNum(), lc.getRowNum(), lc.createLabel(ps));
        }
    }

    /**
     * Sets the bottom bar of the planet screen
     * @param ps The PlanetScreen
     */
    public void updateBottomBar(PlanetScreen ps){
        for (LabelCreator lc : bottomBar){
            ps.setInBottomGrid(lc.getColumnNum(), lc.getRowNum(), lc.createLabel(ps));
        }
    }


    /**
     * Groups the info for the label to be put in a Cell
     */
    private static class LabelCreator {
        private final int rowNum;
        private final int columnNum;
        private final String format;
        private final List<Argument> args;

        /**
         * stores the info to create a label for a cell
         * @param column        the column of the cell
         * @param row           the row of the cell
         * @param formatString  the text to be shown in the label (java String format)
         * @param argString     the arguments for the text
         */
        public LabelCreator(int column, int row, String formatString, String argString){
            args = new LinkedList<>();
            rowNum = row;
            columnNum = column;
            format = formatString;

            for (String s: argString.split(" ")){
                args.add(new Argument(s));
            }

        }

        /**
         * Creates the label using informations from the PlanetScreen
         * @param ps The PlanetScreen
         * @return the label
         */
        public Label createLabel(PlanetScreen ps){
            Label l = new Label();

            Object[] args = new Object[this.args.size()];

            int i = 0;
            for (Argument arg : this.args){
                args[i++] = arg.get(ps);
            }

            l.textProperty().bind(Bindings.format(format, args));

            return l;
        }

        /**
         * @return the column number this goes in
         */
        public int getColumnNum() {
            return columnNum;
        }

        /**
         * @return the row number this goes in
         */
        public int getRowNum() {
            return rowNum;
        }

        /**
         * This class stores the argument description and generates the object specified by that description when asked for
         */
        private static class Argument {
            private static final Logger LOG = getLogger(Argument.class.getName());
            private final String[] splitted;

            /**
             * Splits and stores the object's description
             * @param arg
             */
            public Argument(String arg){
                splitted = arg.split(":");
            }

            /**
             * generates the object described by this argument based on the info in the PlanetScreen
             * @param ps the PlanetScreen
             * @return   the object described
             */
            public Object get(PlanetScreen ps) {

                return switch (splitted[0]){
                    case "cb": yield ps.getLandedOn().getAttribValue(splitted[1]);
                    case "ss": yield ps.getLandedOn().getInSystem().getAttribute(splitted[1]);
                    case "stardate":
                        StarDateFormatter sdf =
                                ps.getGameScheme().getStoryTellingScheme().getFormatter(splitted[1]);
                        yield ps.getGameScheme().getGameState().getCurrentTime().toString(sdf, splitted[2]);
                    default:
                        LOG.severe(splitted[0] + " is untreated, sending null");
                        yield null;
                };

            }
        }
    }
}
