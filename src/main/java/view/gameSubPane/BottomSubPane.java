package view.gameSubPane;

import view.sidePanelBlocks.FlowTowerPane;

public class BottomSubPane extends GameSubPane{
    private final FlowTowerPane flowTowerPane;
    public BottomSubPane(double width, double height){
        super();
        this.setPrefSize(width, height);
        this.flowTowerPane = new FlowTowerPane();
        this.stackPane.getChildren().add(flowTowerPane.getPane());
    }

    public FlowTowerPane getFlowTowerPane() {
        return flowTowerPane;
    }
}
