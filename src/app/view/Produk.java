package app.view;

import app.ctrl.ProdukModify;
import app.ctrl.Statis;
import app.model.DataKategoriProduk;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Created by android on 11/23/17.
 */
public class Produk extends VBox {
    private TableView table;
    private GridPane grid;
    private TextField tx_nama,tx_harga;
    private ComboBox<DataKategoriProduk> cb_kategori;
    private HBox hbox;
    private Button bt_simpan,bt_hapus,bt_refresh;

    private String tmpIdKat="";

    public Produk(){
        Inits();

        grid.add(new Label("Nama"),0,0);
        grid.add(tx_nama,1,0);

        grid.add(new Label("Harga"),0,1);
        grid.add(tx_harga,1,1);

        grid.add(new Label("Kategori"),0,2);
        grid.add(cb_kategori,1,2);

        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        getChildren().addAll(new Judul("Data Produk"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox,table);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(550);
        setMaxWidth(700);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        grid=new GridPane();
        grid.setVgap(5);grid.setHgap(5);

        hbox=new HBox(5);
        hbox.setAlignment(Pos.CENTER);

        table=new TabelProduk();
        table.setItems(new ProdukModify().SetTableItem());

        tx_nama=new TextField();
        tx_harga=new TextField();

        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_refresh.setOnAction(e->{
            Refresh();
        });

        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);

        cb_kategori=new ComboBox<>();
        cb_kategori.setItems(new ProdukModify().SetComboItem());
        cb_kategori.setConverter(new StringConverter<DataKategoriProduk>() {
            @Override
            public String toString(DataKategoriProduk dkp) {
                return dkp.getKategori();
            }

            @Override
            public DataKategoriProduk fromString(String s) {
                return cb_kategori.getItems().stream().filter(ap ->
                        ap.getKategori().equals(s)).findFirst().orElse(null);
            }
        });

        cb_kategori.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                tmpIdKat=newval.getId()+"";
                //System.out.println("Kategori: " + newval.getKategori());
            }
        });
    }

    private void Refresh(){
        tx_harga.setText("");
        tx_nama.setText("");
        tx_nama.requestFocus();

    }
}
