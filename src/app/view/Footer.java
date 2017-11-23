package app.view;

import app.ctrl.Statis;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by android on 11/23/17.
 */
public class Footer extends HBox {
    public Footer(){
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(3,3,3,3));
        setSpacing(5);

        setStyle(new Statis().getSTYLE_FOOTER());

        getChildren().addAll(new Label("Status |"));
    }
}
