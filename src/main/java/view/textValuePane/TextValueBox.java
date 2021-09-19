package view.textValuePane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TextValueBox {
    private final TextValuePane textValuePane;
    private final StackPane stackPane;
    private final Rectangle rectangle;

    public TextValueBox(TextValuePane textValuePane){
        this.textValuePane = textValuePane;
        this.stackPane = new StackPane();
        this.rectangle = new Rectangle();
        this.rectangle.setStroke(Color.BLACK);
        this.rectangle.setStrokeWidth(5);
        this.stackPane.getChildren().addAll(rectangle, textValuePane.gethBox());
    }

    public void setSize(double width, double height){
        this.rectangle.setWidth(width);
        this.rectangle.setHeight(height);
        double arcSize = (width > height) ? width/10 : height/10;
        this.rectangle.setArcHeight(arcSize);
        this.rectangle.setArcWidth(arcSize);
        this.stackPane.setPrefSize(width, height);
        this.textValuePane.setSize(width, height);
    }

    public void setColor(Color color){
        this.rectangle.setFill(color);
    }

    public StackPane getStackPane() {
        return stackPane;
    }
    public TextValuePane getTextValuePane(){
        return textValuePane;
    }
}
