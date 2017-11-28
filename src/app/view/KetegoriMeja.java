package app.view;

import app.ctrl.KategoriMejaModify;
import app.ctrl.Statis;
import app.model.DataKategoriMeja;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KetegoriMeja extends VBox {
    private GridPane grid;
    private TextField tx_nama,tx_tarif;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private TableView table;

    public KetegoriMeja(){
        Inits();

        grid.add(new Label("Nomor/Klas"),0,0);
        grid.add(tx_nama,1,0);

        grid.add(new Label("Tarif"),0,1);
        grid.add(tx_tarif,1,1);

        HBox hbox=new HBox(5);
        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        getChildren().addAll(new Judul("Kategori Meja"),new Separator(Orientation.HORIZONTAL),grid,hbox,table);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(500);
        setMaxWidth(400);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        grid=new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        tx_nama=new TextField();
        tx_tarif=new TextField();
        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);
        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);

        //==========================================================
        table=new TableView();
        table.setEditable(false);
        table.setColumnResizePolicy((param) -> true );

        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nomor / Klas");
        TableColumn tarifCol = new TableColumn("Tarif");
        
        table.getColumns().addAll(idCol,namaCol,tarifCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataKategoriMeja,Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataKategoriMeja,String>("nama"));
        tarifCol.setCellValueFactory(new PropertyValueFactory<DataKategoriMeja,Double>("tarif"));


        idCol.setPrefWidth(50);
        namaCol.setPrefWidth(200);
        tarifCol.setPrefWidth(100);

        idCol.setStyle("-fx-alignment:center;");
        tarifCol.setStyle("-fx-alignment:center-right;");
        //==========================================================
        table.setItems(new KategoriMejaModify().SetTableItem());
    }
}
