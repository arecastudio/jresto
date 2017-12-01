package app.view;

import app.ctrl.KategoriProdukModify;
import app.ctrl.Statis;
import app.model.DataKategoriProduk;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


public class KategoriProduk extends VBox {
    private TableView table;
    public KategoriProduk(){
        Inits();

        getChildren().addAll(new Judul("Kategori Produk"),new Separator(Orientation.HORIZONTAL),table);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(500);
        setMaxWidth(400);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

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
        jenisCol.setPrefWidth(100);
        kategoriCol.setPrefWidth(250);

        idCol.setStyle("-fx-alignment:center;");
        jenisCol.setStyle("-fx-alignment:center;");
        //kategoriCol.setStyle("-fx-alignment:center-right;");
        //==========================================================

        table.setItems(new KategoriProdukModify().SetTableItem());
    }

    private void Refresh(){
        table.setItems(new KategoriProdukModify().SetTableItem());
    }

}
