package view.gameSubPane;

import view.CanvasPane;
import view.GridTilePane;

public class CenterSubPane extends GameSubPane {
    private final GridTilePane gridTilePane;
    private final CanvasPane canvasPane;
    public CenterSubPane(double width, double height){
        super();
        this.setPrefSize(width, height);
        this.gridTilePane = new GridTilePane();
        this.canvasPane = new CanvasPane();
        this.stackPane.getChildren().addAll(gridTilePane.getGridPane(), canvasPane.getPane());
    }

    public CanvasPane getCanvasPane() {
        return canvasPane;
    }
    public GridTilePane getGridTilePane() {
        return gridTilePane;
    }

}
