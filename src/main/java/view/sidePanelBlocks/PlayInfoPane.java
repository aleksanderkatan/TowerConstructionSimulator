package view.sidePanelBlocks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.textValuePane.GoldInfoPane;
import view.textValuePane.HPInfoPane;
import view.textValuePane.TextValueBox;

public class PlayInfoPane {
    private final VBox vBox;
    private final TextValueBox goldInfoBox;
    private final FocusedTowerPane focusedTowerPane;
    private final TextValueBox HPInfoBox;
    private final RectangularSidePanelBox rectangularSidePanelBox;
    public PlayInfoPane(){
        this.vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(60,0,0,0));

        this.goldInfoBox = new TextValueBox(new GoldInfoPane());
        this.HPInfoBox = new TextValueBox(new HPInfoPane());

        this.focusedTowerPane = new FocusedTowerPane();
        this.rectangularSidePanelBox = new RectangularSidePanelBox(focusedTowerPane.getvBox());

        this.vBox.getChildren().addAll(HPInfoBox.getStackPane(), goldInfoBox.getStackPane(),
                rectangularSidePanelBox.getStackPane());
    }
    public void setFocusedTowerPaneSize(double size){
        rectangularSidePanelBox.prepare(size, size * 2.8, 0.9);
    }
    public void setBoxSizes(double width, double height){
        double widthModifier = 0.9;
        double heightModifier = 1.2;
        this.goldInfoBox.setSize( width * widthModifier, height * heightModifier);
        this.HPInfoBox.setSize(width * widthModifier, height * heightModifier);
    }
    public void setBoxColors(Color color){
        this.HPInfoBox.setColor(color);
        this.goldInfoBox.setColor(color);
    }
    public void setGold(int value){
        ((GoldInfoPane)this.goldInfoBox.getTextValuePane()).setGold(value);
    }
    public void setHP(int value){
        ((HPInfoPane)this.HPInfoBox.getTextValuePane()).setHP(value);
    }
    public FocusedTowerPane getFocusedTowerPane(){
        return focusedTowerPane;
    }
    public VBox getvBox() {
        return vBox;
    }
    public double getWidth(){
        return vBox.getWidth();
    }
    public double getHeight(){
        return vBox.getHeight();
    }
}
