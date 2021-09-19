package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class CanvasPane{
    private final Pane pane;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;

    public CanvasPane(){
        this.pane = new Pane();
        this.canvas = new Canvas();
        graphicsContext = canvas.getGraphicsContext2D();
        this.pane.getChildren().clear();
        this.pane.getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Pane getPane(){return pane;}

    public void setSize(double prefWidth, double prefHeight){
        this.pane.setPrefSize(prefWidth, prefHeight);
        this.pane.setMaxSize(prefWidth, prefHeight);
        this.canvas.setWidth(prefWidth);
        this.canvas.setHeight(prefHeight);
    }

}
