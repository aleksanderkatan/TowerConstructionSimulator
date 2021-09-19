package view.gameSubPane;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.GraphicsConfig;

public abstract class GameSubPane {
    protected final StackPane stackPane;
    private final Rectangle backgroundRectangle;
    public GameSubPane(){
        this.stackPane = new StackPane();
        this.backgroundRectangle = new Rectangle();
        this.backgroundRectangle.setX(0);
        this.backgroundRectangle.setY(0);
        this.backgroundRectangle.setFill(GraphicsConfig.placeableTileColor);
        this.backgroundRectangle.setStrokeWidth(5);
        this.backgroundRectangle.setStroke(Color.BLACK);
        this.stackPane.getChildren().add(backgroundRectangle);
        this.stackPane.setAlignment(Pos.CENTER);
    }

    public StackPane getStackPane(){
        return stackPane;
    }
    public void setPrefSize(double prefWidth, double prefHeight){
        this.stackPane.setPrefSize(prefWidth, prefHeight);
        this.backgroundRectangle.setWidth(prefWidth);
        this.backgroundRectangle.setHeight(prefHeight);
        double arcSize = (prefHeight > prefWidth) ? prefHeight /10 : prefWidth /10;
        this.backgroundRectangle.setArcHeight(arcSize);
        this.backgroundRectangle.setArcWidth(arcSize);
    }
    public double getPrefWidth(){
        return stackPane.getPrefWidth();
    }

}
