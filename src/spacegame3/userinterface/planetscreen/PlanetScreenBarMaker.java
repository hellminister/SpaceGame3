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

public class PlanetScreenBarMaker {
    private static final Logger LOG = getLogger(PlanetScreenBarMaker.class.getName());
    private static final String PLANET_SCREEN_URL = "data/gui/planetscreen.txt";

    private List<LabelCreator> topBar;
    private List<LabelCreator> bottomBar;

    public PlanetScreenBarMaker(Path storyPath){
        topBar = new LinkedList<>();
        bottomBar = new LinkedList<>();

        List<LabelCreator> section = null;

        try (BufferedReader br = Files.newBufferedReader(storyPath.resolve(PLANET_SCREEN_URL))){
            // TODO read file
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
                        // desired effect if section is null
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



    public void updateTopBar(PlanetScreen ps){
        for (LabelCreator lc : topBar){
            ps.setInTopGrid(lc.getColumnNum(), lc.getRowNum(), lc.createLabel(ps));
        }
    }

    public void updateBottomBar(PlanetScreen ps){
        for (LabelCreator lc : bottomBar){
            ps.setInBottomGrid(lc.getColumnNum(), lc.getRowNum(), lc.createLabel(ps));
        }
    }


    private static class LabelCreator {
        private final int rowNum;
        private final int columnNum;
        private final String format;
        private final List<Argument> args;

        public LabelCreator(int column, int row, String formatString, String argString){
            args = new LinkedList<>();
            rowNum = row;
            columnNum = column;
            format = formatString;

            for (String s: argString.split(" ")){
                args.add(new Argument(s));
            }

        }



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

        public int getColumnNum() {
            return columnNum;
        }

        public int getRowNum() {
            return rowNum;
        }

        private static class Argument {
            private static final Logger LOG = getLogger(Argument.class.getName());
            private final String[] splitted;

            public Argument(String arg){
                splitted = arg.split(":");
            }

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
