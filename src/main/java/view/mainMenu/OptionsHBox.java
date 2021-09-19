package view.mainMenu;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import java.util.List;

public class OptionsHBox {
    private final List<ToggleButton> buttons;
    private int index;
    public OptionsHBox(List<ToggleButton> elements, int i) {
        buttons = elements;
        index = i;
    }
    public HBox getHBox() {
        HBox result = new HBox();
        for(ToggleButton button: buttons) {
            button.setOnAction(e -> {
                buttons.get(index).setSelected(false);
                //noinspection SuspiciousMethodCalls
                index = buttons.indexOf(e.getSource());
                buttons.get(index).setSelected(true);
            });
        }
        buttons.get(index).setSelected(true);
        result.getChildren().addAll(buttons);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(5);
        return result;
    }
    public int getIndex() {
        return index;
    }
}
