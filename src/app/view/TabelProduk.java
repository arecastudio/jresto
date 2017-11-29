package app.view;

import app.model.DataProduk;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by android on 11/23/17.
 */
public class TabelProduk extends TableView {
    public TabelProduk(){
        setEditable(false);
        setColumnResizePolicy((param) -> true );

        /*
        *
    private int id,kategori_id;
    private String nama,waktu_buat,waktu_ubah,operator,tambahan;
    private double harga;
    private byte gambar;
        * */

        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama Produk");
        TableColumn hargaCol = new TableColumn("Harga");
        TableColumn kategoriCol = new TableColumn("Kategori");
        TableColumn tambahanCol=new TableColumn("Tambahan");

        getColumns().addAll(idCol,namaCol,hargaCol,kategoriCol,tambahanCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataProduk,Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataProduk,String>("nama"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<DataProduk,Double>("harga"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<DataProduk,String>("kategori"));
        tambahanCol.setCellValueFactory(new PropertyValueFactory<DataProduk,String>("tambahan"));


        idCol.setPrefWidth(50);
        namaCol.setPrefWidth(250);
        hargaCol.setPrefWidth(100);
        kategoriCol.setPrefWidth(150);
        tambahanCol.setPrefWidth(100);

        idCol.setStyle("-fx-alignment:center;");
        hargaCol.setStyle("-fx-alignment:center-right;");
        kategoriCol.setStyle("-fx-alignment:center;");
        tambahanCol.setStyle("-fx-alignment:center;");

    }
}
