package view.objectBoxes;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public abstract class ObjectBox {

    protected final Rectangle rectangle;
    protected final VBox vBox;

    public ObjectBox(double size){
        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.CENTER);

        this.rectangle = new Rectangle();
        this.rectangle.setWidth(size);
        this.rectangle.setHeight(size);
        this.vBox.getChildren().addAll(this.rectangle);
    }

    public VBox getvBox() {
        return vBox;
    }
}
