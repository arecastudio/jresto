package app.view;

import app.Main;
import app.ctrl.ProdukModify;
import app.ctrl.Statis;
import app.model.DataKategoriProduk;
import app.model.DataProduk;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;

/**
 * Created by android on 11/23/17.
 */
public class Produk extends VBox {
    private TableView table;
    private GridPane grid;
    private TextField tx_nama,tx_harga;

    private ComboBox<DataKategoriProduk> cb_kategori;
    private ComboBox cb_tambahan;

    private HBox hbox,hbox1;
    private Button bt_simpan,bt_hapus,bt_refresh;
    private ImageView imageView;
    private Label ket;

    private Image imaji;

    private FileChooser fileChooser;
    private File file;

    private Button button_browse;

    private String tmpIdKat="",tmpTambah="";

    public Produk(){
        Inits();

        grid.add(new Label("Nama"),0,0);
        grid.add(tx_nama,1,0);

        grid.add(new Label("Harga"),0,1);
        grid.add(tx_harga,1,1);

        grid.add(new Label("Kategori"),0,2);
        grid.add(cb_kategori,1,2);

        grid.add(new Label("Produk Tambahan"),0,3);
        grid.add(cb_tambahan,1,3);

        grid.add(new Label("Foto"),0,4);
        grid.add(button_browse,1,4);

        hbox.getChildren().addAll(bt_simpan,bt_refresh,new Separator(Orientation.VERTICAL),bt_hapus);

        hbox1.getChildren().addAll(grid,new Separator(Orientation.VERTICAL),imageView);

        getChildren().addAll(new Judul("Data Produk"),new Separator(Orientation.HORIZONTAL),hbox1,new Separator(Orientation.HORIZONTAL),hbox,table,new HBox(new Label("Keterangan: "),ket));
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(550);
        setMaxWidth(700);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        ket=new Label("");

        grid=new GridPane();
        grid.setVgap(5);grid.setHgap(5);
        //grid.setGridLinesVisible(true);

        hbox=new HBox(5);
        hbox.setAlignment(Pos.CENTER);

        hbox1=new HBox(5);
        hbox1.setAlignment(Pos.TOP_LEFT);

        imaji= new Image(getClass().getResourceAsStream("/app/asset/noimage.png"));

        imageView=new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(350);

        imageView.setImage(imaji);

        tx_nama=new TextField();

        tx_harga=new TextField();
        tx_harga.setMaxWidth(100);
        tx_harga.setAlignment(Pos.CENTER_RIGHT);
        /*tx_harga.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tx_harga.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });*/
        tx_harga.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    int i=Integer.parseInt(t1);
                }catch (Exception e){
                    tx_harga.setText(s);
                }
            }
        });

        bt_hapus=new Button("Hapus");
        bt_hapus.setPrefWidth(100);

        bt_refresh=new Button("Refresh");
        bt_refresh.setPrefWidth(100);
        bt_refresh.setOnAction(e->{
            Refresh();
        });

        bt_simpan=new Button("Simpan");
        bt_simpan.setPrefWidth(100);
        bt_simpan.setOnAction(e->{
            if (tmpTambah!="" && tmpIdKat!="" && tx_harga.getText().trim().length()>0 && Integer.parseInt(tx_harga.getText().trim())>0 && tx_nama.getText().trim().length()>0){
                System.out.println("Tersimpan.");
                Refresh();
            }
        });

        ObservableList<String> opsi= FXCollections.observableArrayList();
        opsi.addAll("YA","TIDAK");
        cb_tambahan=new ComboBox(opsi);
        cb_tambahan.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1!=null){
                    tmpTambah=t1+"";
                    //System.out.println(tmpTambah);
                }
            }
        });

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

        fileChooser=new FileChooser();
        fileChooser.setTitle("Pilih Gambar");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg"));

        button_browse=new Button("Pilih Gambar");
        button_browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                file=fileChooser.showOpenDialog(Main.stage);
                if (file!=null){
                    ket.setText(""+ file);
                    Image image=new Image(file.toURI().toString());
                    imageView.setImage(image);
                }
            }
        });

        table=new TabelProduk();
        table.setItems(new ProdukModify().SetTableItem());
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataProduk dp=(DataProduk) table.getSelectionModel().getSelectedItem();
                    tx_nama.setText(dp.getNama());
                    tx_harga.setText(""+dp.getHarga());
                    cb_tambahan.setValue(dp.getTambahan());
                    //cb_kategori.setValue(dp.getKategori());
                }
            }
        });
    }

    private void Refresh(){
        tx_harga.setText("");
        tx_nama.setText("");
        tx_nama.requestFocus();
        //imageView.setImage(null);
        imageView.setImage(imaji);
        ket.setText("");

        cb_tambahan.getSelectionModel().clearSelection();
        cb_kategori.getSelectionModel().clearSelection();

        tmpIdKat="";
        tmpTambah="";
    }
}
