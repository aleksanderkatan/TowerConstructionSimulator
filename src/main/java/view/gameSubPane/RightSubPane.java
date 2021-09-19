package view.gameSubPane;

import view.sidePanelBlocks.RightPanelInfoPane;

public class RightSubPane extends GameSubPane{
    private final RightPanelInfoPane rightPanelInfoPane;
    public RightSubPane(double width, double height){
        super();
        this.setPrefSize(width, height);
        this.rightPanelInfoPane = new RightPanelInfoPane();
        this.stackPane.getChildren().add(rightPanelInfoPane.getvBox());
    }
    public RightPanelInfoPane getRightPanelInfoPane() {
        return rightPanelInfoPane;
    }
}
