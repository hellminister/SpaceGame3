package spacegame3.userinterface.planetscreen;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import spacegame3.gamedata.systems.tabdata.DescriptionTabRecord;
import spacegame3.userinterface.ImageLibrary;

public class DescriptionTab extends TabLike<DescriptionTabRecord>{

    private VBox basePane;

    public DescriptionTab(DescriptionTabRecord record) {
        super(record);

        basePane = new VBox();

        basePane.setStyle("-fx-background-color: darkslategray");

        Label text = new Label(record.getDescription());
        text.setWrapText(true);
        text.setFont(new Font("Cambria", 20));
        text.setTextFill(Color.ALICEBLUE);

        StackPane ima = new StackPane();

        String imageURL = record.getImageURL();

        Image im = "".equals(imageURL) ? null : ImageLibrary.getImage(record.getImageURL());

        ImageView image = new ImageView(im);
        image.setPreserveRatio(true);
        image.fitWidthProperty().bind(basePane.widthProperty());
        image.fitHeightProperty().bind(basePane.heightProperty().multiply(0.75));



        ima.getChildren().add(image);
        ima.setAlignment(Pos.CENTER);
        ima.setStyle("-fx-background-color: black");


        basePane.getChildren().addAll(ima, text);

        this.getChildren().add(basePane);
    }

    @Override
    protected void attachTo() {
        basePane.maxWidthProperty().bind(container.getContentPane().widthProperty());
        basePane.minWidthProperty().bind(container.getContentPane().widthProperty());
        basePane.prefWidthProperty().bind(container.getContentPane().widthProperty());

        basePane.maxHeightProperty().bind(container.getContentPane().heightProperty());
        basePane.minHeightProperty().bind(container.getContentPane().heightProperty());
        basePane.prefHeightProperty().bind(container.getContentPane().heightProperty());
    }
}
