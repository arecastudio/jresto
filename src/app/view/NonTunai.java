package app.view;

import app.ctrl.NonTunaiModify;
import app.ctrl.Statis;
import app.model.DataNonTunai;
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

        getChildren().addAll(new Judul("Non Tunai"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox,table);
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
        bt_simpan.setOnAction(e->{
            if (tmpId!=""){
                //update
                if (tx_nama.getText().trim().length()>0){
                    DataNonTunai dkp=new DataNonTunai();
                    dkp.setId(Integer.parseInt(tmpId));
                    dkp.setNama(tx_nama.getText().trim());
                    int i=new NonTunaiModify().Ubah(dkp);
                    if (i>0) Refresh();
                }
            }else{
                //insert
                if (tx_nama.getText().trim().length()>0){
                    DataNonTunai dkp=new DataNonTunai();
                    dkp.setNama(tx_nama.getText().trim());
                    int i=new NonTunaiModify().Simpan(dkp);
                    if (i>0) Refresh();
                }
            }
        });

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_refresh.setOnAction(e->{
            Refresh();
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
                    int i=new NonTunaiModify().Hapus(Integer.parseInt(tmpId));
                    if (i>0) Refresh();
                }
            }
        });

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
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataNonTunai dnt=(DataNonTunai)table.getSelectionModel().getSelectedItem();
                    tmpId=dnt.getId()+"";
                    tx_nama.setText(dnt.getNama());
                }
            }
        });
    }

    private void Refresh(){
        table.setItems(new NonTunaiModify().SetTableItem());
        tx_nama.setText("");
        tx_nama.requestFocus();
        tmpId="";
    }
}
