package spacegame3.userinterface.planetscreen.tabs;

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
import spacegame3.util.tablikepane.TabLikeWithRecord;

public class DescriptionTab extends TabLikeWithRecord<DescriptionTabRecord> {

    public DescriptionTab(DescriptionTabRecord record) {
        super(record, new VBox());

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

    }

}
