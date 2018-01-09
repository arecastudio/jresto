package app.view;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Meja extends VBox {
    private TableView table;
    private GridPane grid;
    private TextField tx_nama;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private String tmpId="";

    public Meja(){
        Inits();
    }

    private void Inits() {
        //
    }
}
