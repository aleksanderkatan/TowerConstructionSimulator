package view.textValuePane;

public class TowerLevelInfoPane extends TextValuePane{
    public TowerLevelInfoPane(){
        super();
        this.setText("Level");
    }
    public void setLevel(int level){
        this.setValue(String.valueOf(level));
    }
    public int getLevel(){
        return Integer.parseInt(this.getValue());
    }
}
