package view.sidePanelBlocks;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.GraphicsConfig;

public class RectangularSidePanelBox {
    private final StackPane stackPane;
    private final Rectangle rectangle;
    private final VBox vBox;
    public RectangularSidePanelBox(VBox vBox){
        this.vBox = vBox;
        this.stackPane = new StackPane();

        this.rectangle = new Rectangle();
        this.rectangle.setFill(GraphicsConfig.startTileColor);
        this.rectangle.setStroke(Color.BLACK);
        this.rectangle.setStrokeWidth(5);

        this.stackPane.setAlignment(Pos.CENTER);
        this.stackPane.getChildren().addAll(rectangle, vBox);

    }
    public void prepare(double width, double height, double modifier){
        this.rectangle.setWidth(width * modifier);
        this.rectangle.setHeight(height * modifier);
        double arcSize = ((height > width) ? this.rectangle.getHeight() : this.rectangle.getWidth()) / 10;
        this.rectangle.setArcWidth(arcSize);
        this.rectangle.setArcHeight(arcSize);
        this.stackPane.setMaxSize(width * modifier, height*modifier);
        this.vBox.setMaxSize(width*modifier, height*modifier);
    }
    public void modifyArcSize(double arcSize){
        this.rectangle.setArcWidth(arcSize);
        this.rectangle.setArcHeight(arcSize);
    }

    public StackPane getStackPane() {
        return stackPane;
    }
    public void changeColor(Color color){
        rectangle.setFill(color);
    }
    public VBox getvBox() {
        return vBox;
    }
}
