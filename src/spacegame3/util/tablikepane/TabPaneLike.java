package spacegame3.util.tablikepane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

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
    private final Pane nameTab;
    private final StackPane tabContent;

    private final List<TabLike> tabs;

    private Button selected;

    public TabPaneLike(TabSide side){
        bp = new BorderPane();

        getChildren().add(bp);

        bp.maxWidthProperty().bind(widthProperty());
        bp.minWidthProperty().bind(widthProperty());
        bp.prefWidthProperty().bind(widthProperty());

        bp.maxHeightProperty().bind(heightProperty());
        bp.minHeightProperty().bind(heightProperty());
        bp.prefHeightProperty().bind(heightProperty());


        tabContent = new StackPane();
        tabs = new LinkedList<>();

        switch(side){
            case TOP -> {
                nameTab = new HBox();
                nameTab.setPrefHeight(BUTTON_PREF_HEIGHT);
                nameTab.setMinHeight(BUTTON_MIN_HEIGHT);
                nameTab.setMaxHeight(BUTTON_MAX_HEIGHT);
                bp.setTop(nameTab);
            }
            case BOTTOM -> {
                nameTab = new HBox();
                nameTab.setPrefHeight(BUTTON_PREF_HEIGHT);
                nameTab.setMinHeight(BUTTON_MIN_HEIGHT);
                nameTab.setMaxHeight(BUTTON_MAX_HEIGHT);
                bp.setBottom(nameTab);
            }
            case RIGHT -> {
                nameTab = new VBox();
                nameTab.setPrefWidth(BUTTON_PREF_WIDTH);
                nameTab.setMinWidth(BUTTON_MIN_WIDTH);
                nameTab.setMaxWidth(BUTTON_MAX_WIDTH);
                bp.setRight(nameTab);
            }
            case LEFT -> {
                nameTab = new VBox();
                nameTab.setPrefWidth(BUTTON_PREF_WIDTH);
                nameTab.setMinWidth(BUTTON_MIN_WIDTH);
                nameTab.setMaxWidth(BUTTON_MAX_WIDTH);
                bp.setLeft(nameTab);
            }
            default -> {
                nameTab = new VBox();
            }

        }

        bp.setCenter(tabContent);

        nameTab.setStyle("-fx-background-color: lightgrey");
    }

    public void clear(){
        nameTab.getChildren().clear();
        tabContent.getChildren().clear();
        tabs.clear();
    }

    public Pane getContentPane(){
        return tabContent;
    }

    public void add(TabLike tab){
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

    public enum TabSide {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        ;
    }
}
