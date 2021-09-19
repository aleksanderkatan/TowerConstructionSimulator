package view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import view.gameSubPane.BottomSubPane;
import view.gameSubPane.CenterSubPane;
import view.gameSubPane.LeftSubPane;
import view.gameSubPane.RightSubPane;
import view.sidePanelBlocks.FlowTowerPane;
import view.sidePanelBlocks.PlayInfoPane;

import java.util.Calendar;

public class GamePane {
    private final BorderPane pane;
    private final CenterSubPane centerSubPane;
    private final BottomSubPane bottomSubPane;
    private final LeftSubPane leftSubPane;
    private final RightSubPane rightSubPane;
    public GamePane(double width, double height){
        super();
        this.pane = new BorderPane();
        this.pane.setPadding(new Insets(20, 50, 20 ,50));
        this.pane.setPrefSize(width, height);
        this.centerSubPane = new CenterSubPane(width - 500, height - 200);
        this.pane.setCenter(centerSubPane.getStackPane());
        this.bottomSubPane = new BottomSubPane((width-500) * 2/3f, 100);
        this.pane.setBottom(bottomSubPane.getStackPane());
        this.leftSubPane = new LeftSubPane(150, height - 200);
        this.pane.setLeft(leftSubPane.getStackPane());
        this.rightSubPane = new RightSubPane(150, height - 200);
        this.pane.setRight(rightSubPane.getStackPane());
        Calendar calendar = Calendar.getInstance();
        boolean isDay = (calendar.get(Calendar.HOUR_OF_DAY) >= 7 && calendar.get(Calendar.HOUR_OF_DAY) < 19);
        if(isDay){
            pane.setBackground(new Background(GraphicsConfig.background_day_blur));
        }
        else{
            pane.setBackground(new Background(GraphicsConfig.background_night_blur));
        }

    }

    public BorderPane getPane() {
        return pane;
    }
    public GridTilePane getGridTilePane(){
        return centerSubPane.getGridTilePane();
    }
    public CanvasPane getCanvasPane(){return centerSubPane.getCanvasPane();}
    public FlowTowerPane getFlowTowerPane() {
        return bottomSubPane.getFlowTowerPane();
    }
    public PlayInfoPane getPlayInfoPane(){return leftSubPane.getPlayInfoPane();}
    public LeftSubPane getLeftSubPane(){return leftSubPane;}
    public RightSubPane getRightSubPane() {
        return rightSubPane;
    }
}
