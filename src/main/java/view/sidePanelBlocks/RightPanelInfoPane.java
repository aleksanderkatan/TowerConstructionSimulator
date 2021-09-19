package view.sidePanelBlocks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class RightPanelInfoPane {
    private final VBox vBox;
    private final UpcomingWavePane upcomingWavePane;
    private final RectangularSidePanelBox rectangularSidePanelBox;
    private final GameManipulationPane gameManipulationPane;
    public RightPanelInfoPane(){
        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(10);
        this.vBox.setPadding(new Insets(60,0,0,0));

        this.gameManipulationPane = new GameManipulationPane();

        this.upcomingWavePane = new UpcomingWavePane();
        this.rectangularSidePanelBox = new RectangularSidePanelBox(upcomingWavePane.getvBox());

        this.vBox.getChildren().addAll(rectangularSidePanelBox.getStackPane(), gameManipulationPane.getvBox());
    }

    public VBox getvBox() {
        return vBox;
    }

    public GameManipulationPane getGameManipulationPane() {
        return gameManipulationPane;
    }

    public RectangularSidePanelBox getRectangularSidePanelBox() {
        return rectangularSidePanelBox;
    }
    public UpcomingWavePane getUpcomingWavePane() {
        return upcomingWavePane;
    }
    public void setUpcomingWavePaneSize(double width, double height){
        rectangularSidePanelBox.prepare(width, height, 0.9);
    }
}
