package spacegame3.userinterface.planetscreen.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import spacegame3.gamedata.systems.tabdata.SpaceportPerson;
import spacegame3.gamedata.systems.tabdata.SpaceportTabRecord;
import spacegame3.util.Utilities;
import spacegame3.util.tablikepane.TabLikeWithRecord;

public class Spaceport extends TabLikeWithRecord<SpaceportTabRecord> {

    private static final int BUTTON_MAX_HEIGHT = 25;
    private static final int BUTTON_PREF_HEIGHT = 25;
    private static final int BUTTON_MIN_HEIGHT = 25;
    private static final int BUTTON_MAX_WIDTH = 150;
    private static final int BUTTON_PREF_WIDTH = 150;
    private static final int BUTTON_MIN_WIDTH = 150;

    private final TilePane tp;

    private Button selected;


    public Spaceport(SpaceportTabRecord record) {
        super(record, new HBox());

        VBox leftSide = new VBox();
        StackPane rightSide = new StackPane();
        rightSide.setStyle("-fx-background-color: darkslategray");

        Utilities.attach(leftSide, basePane.widthProperty().multiply(0.66), basePane.heightProperty());
        Utilities.attach(rightSide, basePane.widthProperty().multiply(0.34), basePane.heightProperty());

        ScrollPane sp = new ScrollPane();
        Utilities.attach(sp, leftSide.widthProperty(), leftSide.heightProperty().multiply(0.66));

        tp = new TilePane();

        tp.setHgap(10);
        tp.setVgap(10);

        sp.setContent(tp);

        sp.setFitToWidth(true);

        leftSide.getChildren().addAll(sp);

        basePane.getChildren().addAll(leftSide, rightSide);

    }

    public void add(SpaceportPerson spaceportPerson){
        Button btn = createButton(spaceportPerson);

        tp.getChildren().add(btn);

    }

    public Button createButton(SpaceportPerson person){
        Button btn = new Button();

        btn.setMaxSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
        btn.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        btn.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        btn.setOnAction(new Action(person, btn));

        return btn;
    }


    private class Action implements EventHandler<ActionEvent> {
        private final SpaceportPerson person;
        private final Button me;

        public Action(SpaceportPerson person, Button me) {
            this.person = person;
            this.me = me;
        }

        @Override
        public void handle(ActionEvent event) {
            selected.setDisable(false);
            me.setDisable(true);
            selected = me;
        }
    }

}
