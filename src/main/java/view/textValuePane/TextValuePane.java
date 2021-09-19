package view.textValuePane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import view.GraphicsConfig;

public abstract class TextValuePane {
    protected final HBox hBox;
    protected final Label textLabel;
    protected final Label valueLabel;
    public TextValuePane(){
        this.hBox = new HBox();
        this.hBox.setAlignment(Pos.CENTER);
        this.hBox.setSpacing(5);
        this.textLabel = new Label();
        this.valueLabel = new Label();
        this.textLabel.setAlignment(Pos.CENTER_LEFT);
        this.valueLabel.setAlignment(Pos.CENTER_RIGHT);
        this.textLabel.setMaxWidth(Double.MAX_VALUE);
        this.valueLabel.setMaxWidth(Double.MAX_VALUE);
        this.textLabel.setFont(GraphicsConfig.fontMainGame);
        this.valueLabel.setFont(GraphicsConfig.fontMainGame);
        HBox.setHgrow(textLabel, Priority.ALWAYS);
        HBox.setHgrow(valueLabel, Priority.ALWAYS);
        this.hBox.getChildren().addAll(textLabel, valueLabel);

    }

    public void setSize(double width, double height){
        hBox.setPrefSize(width, height);
        hBox.setMaxSize(width, height);
        this.hBox.setPadding(new Insets(0, width/20, 0, width/20));
    }
    public void setText(String text){
        textLabel.setText(text);
    }
    public void setValue(String value){
        valueLabel.setText(value);
    }
    public String getValue(){
        return valueLabel.getText();
    }
    public Label getValueLabel() {
        return valueLabel;
    }
    public HBox gethBox() {
        return hBox;
    }

    public void setVisible(boolean value){
        hBox.setVisible(value);
    }
}
