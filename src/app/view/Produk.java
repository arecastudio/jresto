package app.view;

import app.ctrl.Statis;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Created by android on 11/23/17.
 */
public class Produk extends VBox {
    public Produk(){
        Inits();
        getChildren().addAll(new Judul("Data Produk"),new Separator(Orientation.HORIZONTAL));
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(500);
        setMaxWidth(1042);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);
    }
}
