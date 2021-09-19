package view;

import javafx.scene.image.Image;

import java.io.File;

public class Getter {
    public static File getFile(String name) {
        return new File("src/main/resources/"+name);
    }
    public static Image getImage(String name) {
        return new Image(getFile(name).toURI().toString());
    }
}
