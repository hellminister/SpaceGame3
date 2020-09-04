package spacegame3.userinterface.planetscreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import spacegame3.gamedata.systems.tabdata.TabRecord;

import java.util.LinkedList;
import java.util.List;

public class TabPaneLike extends Pane {

    private static final int BUTTON_MAX_HEIGHT = 25;
    private static final int BUTTON_PREF_HEIGHT = 25;
    private static final int BUTTON_MIN_HEIGHT = 25;
    private static final int BUTTON_MAX_WIDTH = 150;
    private static final int BUTTON_PREF_WIDTH = 150;
    private static final int BUTTON_MIN_WIDTH = 150;

    private final BorderPane bp;
    private final VBox nameTab;
    private final StackPane tabContent;

    private final List<TabLike<? extends TabRecord>> tabs;

    private Button selected;

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

    public void add(TabLike<? extends TabRecord> tab){
        Button btn = createButton(tab.getName());

        tab.setVisible(tabs.isEmpty());
        btn.setDisable(tabs.isEmpty());
        if (tabs.isEmpty()){
            selected = btn;
        }

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

        btn.setOnAction(new Action(name, btn));

        return btn;
    }

    public void setBottomBar(Pane bottomBar) {
        bp.setBottom(bottomBar);
    }

    private class Action implements EventHandler<ActionEvent> {
        private final String name;
        private final Button me;

        public Action(String name, Button me) {
            this.name = name;
            this.me = me;
        }

        @Override
        public void handle(ActionEvent event) {
            selected.setDisable(false);
            me.setDisable(true);
            selected = me;
            for (var tab : tabs){
                tab.setVisible(name.equals(tab.getName()));
            }
        }
    }

}
