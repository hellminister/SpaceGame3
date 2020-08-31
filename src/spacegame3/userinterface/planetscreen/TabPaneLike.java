package spacegame3.userinterface.planetscreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class TabPaneLike extends Pane {

    private static final int BUTTON_MAX_HEIGHT = 25;
    private static final int BUTTON_PREF_HEIGHT = 25;
    private static final int BUTTON_MIN_HEIGHT = 25;
    private static final int BUTTON_MAX_WIDTH = 150;
    private static final int BUTTON_PREF_WIDTH = 150;
    private static final int BUTTON_MIN_WIDTH = 150;

    private BorderPane bp;
    private VBox nameTab;
    private StackPane tabContent;

    private List<TabLike> tabs;

    public TabPaneLike(){
        bp = new BorderPane();

        getChildren().add(bp);

        bp.maxWidthProperty().bind(widthProperty());
        bp.minWidthProperty().bind(widthProperty());
        bp.prefWidthProperty().bind(widthProperty());

        bp.maxHeightProperty().bind(heightProperty());
        bp.minHeightProperty().bind(heightProperty());
        bp.prefHeightProperty().bind(heightProperty());

        nameTab = new VBox();
        tabContent = new StackPane();
        tabs = new LinkedList<>();

        nameTab.setStyle("-fx-background-color: lightgrey");

        bp.setCenter(tabContent);
        bp.setRight(nameTab);
    }

    public void clear(){
        nameTab.getChildren().clear();
        tabContent.getChildren().clear();
        tabs.clear();
    }

    public Pane getContentPane(){
        return tabContent;
    }

    public void setTopBar(Pane top){
        bp.setTop(top);
    }

    public void add(TabLike tab){
        Button btn = createButton(tab.getName());

        tab.setVisible(tabs.isEmpty());

        tabs.add(tab);
        nameTab.getChildren().add(btn);
        tabContent.getChildren().add(tab);
        tab.setContainer(this);
    }

    public Button createButton(String name){
        Button btn = new Button(name);

        btn.setMaxSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
        btn.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        btn.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        btn.setOnAction(new Action(name));

        return btn;
    }

    private class Action implements EventHandler<ActionEvent> {
        private String name;

        public Action(String name) {
            this.name = name;
        }

        /**
         * Invoked when a specific event of the type for which this handler is
         * registered happens.
         *
         * @param event the event which occurred
         */
        @Override
        public void handle(ActionEvent event) {
            for (var tab : tabs){
                tab.setVisible(name.equals(tab.getName()));
            }
        }
    }

}
