package app.view;

import javafx.scene.control.Label;
import app.ctrl.Statis;

public class Judul extends Label {

    public Judul(String text){
        setText(text);
        setStyle(new Statis().getSTYLE_JUDUL());
    }
}
