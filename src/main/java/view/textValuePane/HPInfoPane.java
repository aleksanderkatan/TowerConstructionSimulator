package view.textValuePane;

public class HPInfoPane extends TextValuePane{
    public HPInfoPane(){
        super();
        this.setText("HP");
    }
    public void setHP(int hp){
        this.setValue(String.valueOf(hp));
    }
}
