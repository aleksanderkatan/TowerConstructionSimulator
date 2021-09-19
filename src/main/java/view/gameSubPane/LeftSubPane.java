package view.gameSubPane;

import view.sidePanelBlocks.PlayInfoPane;

public class LeftSubPane extends GameSubPane{
    private final PlayInfoPane playInfoPane;
    public LeftSubPane(double width, double height){
        super();
        this.setPrefSize(width, height);
        this.playInfoPane = new PlayInfoPane();
        this.stackPane.getChildren().add(playInfoPane.getvBox());
    }

    public PlayInfoPane getPlayInfoPane() {
        return playInfoPane;
    }
}
