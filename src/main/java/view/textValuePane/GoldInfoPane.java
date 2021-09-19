package view.textValuePane;

public class GoldInfoPane extends TextValuePane{
    public GoldInfoPane(){
        super();
        this.setText("Gold");
    }
    public void setGold(int value){
        this.setValue(String.valueOf(value));
    }

}
