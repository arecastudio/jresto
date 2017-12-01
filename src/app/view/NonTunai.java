package app.view;

import app.ctrl.NonTunaiModify;
import app.ctrl.Statis;
import app.model.DataNonTunai;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NonTunai extends VBox {
    private TableView table;
    private GridPane grid;
    private TextField tx_nama;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private String tmpId="";

    public NonTunai(){
        Inits();
        grid.add(new Label("Nama Bank"),0,0);
        grid.add(tx_nama,1,0);

        HBox hbox=new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        getChildren().addAll(new Judul("Transaksi Non Tunai"),new Separator(Orientation.HORIZONTAL),grid,hbox,table);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(500);
        setMaxWidth(400);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        tx_nama=new TextField();
        tx_nama.setPrefWidth(250);

        grid=new GridPane();
        grid.setHgap(5);grid.setVgap(5);

        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);

        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);

        //==========================================================
        table=new javafx.scene.control.TableView();
        table.setEditable(false);
        table.setColumnResizePolicy((param) -> true );

        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama Bank");

        table.getColumns().addAll(idCol,namaCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataNonTunai,Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataNonTunai,String>("nama"));


        idCol.setPrefWidth(50);
        namaCol.setPrefWidth(300);

        idCol.setStyle("-fx-alignment:center;");
        namaCol.setStyle("-fx-alignment:center-left;");
        //kategoriCol.setStyle("-fx-alignment:center-right;");
        //==========================================================
        table.setItems(new NonTunaiModify().SetTableItem());
    }

    private void Refresh(){
        table.setItems(new NonTunaiModify().SetTableItem());
        tx_nama.setText("");
        tmpId="";
    }
}
