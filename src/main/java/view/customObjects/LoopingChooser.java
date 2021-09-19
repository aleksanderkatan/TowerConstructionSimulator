package view.customObjects;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import view.GraphicsConfig;

public class LoopingChooser {
    private final HBox hBox;
    private final Button leftButton;
    private final Button rightButton;
    private final Label currentChoice;
    public LoopingChooser(){
        this.hBox = new HBox();
        this.hBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(GraphicsConfig.arrowLeft);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        this.leftButton = new Button("", imageView);
        this.leftButton.setBackground(Background.EMPTY);
        ImageView imageView2 = new ImageView(GraphicsConfig.arrowRight);
        imageView2.setFitHeight(15);
        imageView2.setFitWidth(15);
        this.rightButton = new Button("", imageView2);
        this.rightButton.setBackground(Background.EMPTY);
        this.currentChoice = new Label("choice");
        this.currentChoice.setFont(GraphicsConfig.fontMainGame);
        this.currentChoice.setTextAlignment(TextAlignment.CENTER);

        this.hBox.getChildren().addAll(leftButton, currentChoice, rightButton);
        HBox.setHgrow(currentChoice, Priority.ALWAYS);
        HBox.setHgrow(leftButton, Priority.NEVER);
        HBox.setHgrow(rightButton, Priority.NEVER);

    }
    public void setWidth(double width){
        this.hBox.setPrefWidth(width);
    }
    public HBox gethBox() {
        return hBox;
    }
    public void changeChoice(String value){
        currentChoice.setText(value);
    }
    public Button getLeftButton(){
        return leftButton;
    }
    public Button getRightButton() {
        return rightButton;
    }
}
