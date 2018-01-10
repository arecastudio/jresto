package app.view;

import app.ctrl.KategoriProdukModify;
import app.ctrl.Statis;
import app.model.DataKategoriProduk;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;


public class KategoriProduk extends VBox {
    private TableView table;
    private GridPane grid;
    private ComboBox<String> cb_jenis;
    private TextField tx_kategori;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private String tmpId="";

    public KategoriProduk(){
        Inits();

        grid.add(new Label("Jenis"),0,1);
        grid.add(cb_jenis,1,1);

        grid.add(new Label("Kategori"),0,0);
        grid.add(tx_kategori,1,0);

        HBox hbox=new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        getChildren().addAll(new Judul("Kategori Produk"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox,table);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(500);
        setMaxWidth(400);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        grid=new GridPane();
        grid.setHgap(5);grid.setVgap(5);

        tx_kategori=new TextField();
        tx_kategori.setPrefWidth(280);

        ObservableList<String> opsi= FXCollections.observableArrayList();
        opsi.addAll("MAKAN","MINUM","LAINNYA");
        cb_jenis=new ComboBox<>(opsi);

        //==========================================================
        table=new javafx.scene.control.TableView();
        table.setEditable(false);
        table.setColumnResizePolicy((param) -> true );

        TableColumn idCol = new TableColumn("ID");
        TableColumn jenisCol = new TableColumn("Jenis");
        TableColumn kategoriCol = new TableColumn("Kategori");

        table.getColumns().addAll(idCol,jenisCol,kategoriCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataKategoriProduk,Integer>("id"));
        jenisCol.setCellValueFactory(new PropertyValueFactory<DataKategoriProduk,String>("jenis"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<DataKategoriProduk,String>("kategori"));


        idCol.setPrefWidth(50);
        jenisCol.setPrefWidth(80);
        kategoriCol.setPrefWidth(230);

        idCol.setStyle("-fx-alignment:center;");
        jenisCol.setStyle("-fx-alignment:center;");
        //kategoriCol.setStyle("-fx-alignment:center-right;");
        //==========================================================
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataKategoriProduk dkp=(DataKategoriProduk) table.getSelectionModel().getSelectedItem();
                    tmpId=dkp.getId()+"";
                    tx_kategori.setText(dkp.getKategori());
                    cb_jenis.setValue(dkp.getJenis());
                }
            }
        });

        table.setItems(new KategoriProdukModify().SetTableItem());

        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);
        bt_simpan.setOnAction(e->{
            if (tmpId!=""){
                //update
                if (cb_jenis.getValue()!="" && tx_kategori.getText().trim().length()>0){
                    DataKategoriProduk dkp=new DataKategoriProduk();
                    dkp.setId(Integer.parseInt(tmpId));
                    dkp.setJenis(cb_jenis.getValue());
                    dkp.setKategori(tx_kategori.getText().trim());
                    int i=new KategoriProdukModify().Ubah(dkp);
                    if (i>0) Refresh();
                }
            }else{
                //insert
                if (cb_jenis.getValue()!="" && tx_kategori.getText().trim().length()>0){
                    DataKategoriProduk dkp=new DataKategoriProduk();
                    dkp.setJenis(cb_jenis.getValue());
                    dkp.setKategori(tx_kategori.getText().trim());
                    int i=new KategoriProdukModify().Simpan(dkp);
                    if (i>0) Refresh();
                }
            }
        });

        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);
        bt_hapus.setOnAction(e->{
            if (tmpId!=""){
                Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Konfirmasi");
                a.setHeaderText("Konfirmasi Hapus.");
                a.setContentText("Yakin hapus data ini?");

                Optional<ButtonType> o=a.showAndWait();
                if (o.get()==ButtonType.OK){
                    //System.out.println("hapus");
                    int i=new KategoriProdukModify().Hapus(Integer.parseInt(tmpId));
                    if (i>0) Refresh();
                }
            }
        });

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_refresh.setOnAction(e->{
            Refresh();
        });
    }

    private void Refresh(){
        table.setItems(new KategoriProdukModify().SetTableItem());
        tmpId="";
        cb_jenis.getSelectionModel().clearSelection();
        tx_kategori.setText("");
        tx_kategori.requestFocus();
    }

}
