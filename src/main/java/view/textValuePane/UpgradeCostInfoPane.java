package view.textValuePane;

public class UpgradeCostInfoPane extends view.textValuePane.TextValuePane {
    public UpgradeCostInfoPane() {
        super();
        this.setText("Upgrade\ncost");
    }
    public void setCost(int cost){
        this.setValue(String.valueOf(cost));
    }
}
