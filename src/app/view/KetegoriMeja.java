package app.view;

import app.ctrl.KategoriMejaModify;
import app.ctrl.Statis;
import app.model.DataKategoriMeja;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class KetegoriMeja extends VBox {
    private GridPane grid;
    private TextField tx_nama,tx_tarif;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private TableView table;
    private String tmpId="";

    public KetegoriMeja(){
        Inits();

        grid.add(new Label("Nomor/Klas"),0,0);
        grid.add(tx_nama,1,0);

        grid.add(new Label("Tarif"),0,1);
        grid.add(tx_tarif,1,1);

        HBox hbox=new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        getChildren().addAll(new Judul("Kategori Meja"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox,table);
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
        tx_nama.setPrefWidth(250);

        tx_tarif=new TextField();
        tx_tarif.setMaxWidth(150);
        tx_tarif.setAlignment(Pos.CENTER_RIGHT);
        tx_tarif.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tx_tarif.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });


        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);
        //bt_hapus.setStyle("-fx-background-color: linear-gradient(#F00,#900);");
        bt_hapus.setOnAction(e->{
            if (tmpId!=""){
                Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Konfirmasi");
                a.setHeaderText("Konfirmasi Hapus.");
                a.setContentText("Yakin hapus data ini?");

                Optional<ButtonType> o=a.showAndWait();
                if (o.get()==ButtonType.OK){
                    //System.out.println("hapus");
                    int i=new KategoriMejaModify().Hapus(Integer.parseInt(tmpId));
                    if (i>0) Refresh();
                }
            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText("Pilih menu/produk dulu.");
                alert.showAndWait();
            }
        });

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_refresh.setOnAction(e->{
            Refresh();
        });

        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);
        bt_simpan.setOnAction(e->{
            if (tmpId!=""){
                //update
                if (tx_nama.getText().trim()!="" && tx_tarif.getText().trim()!="" && Integer.parseInt(tx_tarif.getText().trim())>0){
                    DataKategoriMeja dkm=new DataKategoriMeja();
                    dkm.setTarif(Double.parseDouble(tx_tarif.getText().trim()));
                    dkm.setNama(tx_nama.getText().trim());
                    dkm.setId(Integer.parseInt(tmpId));
                    int i=new KategoriMejaModify().Ubah(dkm);
                    if (i>0) Refresh();
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Data belum lengkap.");
                    alert.showAndWait();
                }
            }else{
                //insert
                if (tx_nama.getText().trim()!="" && tx_tarif.getText().trim()!=""){
                    DataKategoriMeja dkm=new DataKategoriMeja();
                    dkm.setTarif(Double.parseDouble(tx_tarif.getText().trim()));
                    dkm.setNama(tx_nama.getText().trim());
                    int i=new KategoriMejaModify().Simpan(dkm);
                    if (i>0) Refresh();
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Data belum lengkap.");
                    alert.showAndWait();
                }
            }
        });

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
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataKategoriMeja dkm= (DataKategoriMeja) table.getSelectionModel().getSelectedItem();
                    tx_nama.setText(dkm.getNama());
                    int i=(int) dkm.getTarif();
                    tx_tarif.setText(""+i);
                    tmpId=""+dkm.getId();
                    //System.out.println(tmpId);
                }
            }
        });
    }

    private void Refresh(){
        tx_tarif.setText("");
        tx_nama.setText("");
        table.setItems(new KategoriMejaModify().SetTableItem());
        tx_nama.requestFocus();
        tmpId="";
    }
}
