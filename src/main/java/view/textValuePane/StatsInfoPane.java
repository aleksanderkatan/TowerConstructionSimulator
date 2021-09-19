package view.textValuePane;

public class StatsInfoPane extends TextValuePane{
    public StatsInfoPane(String stat){
        super();
        setText(stat);
    }
    public void setStatValue(String value){
        setValue(value);
    }
}
