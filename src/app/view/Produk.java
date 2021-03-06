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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

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

    private String tmpIdKat="",tmpTambah="",tmpId="";
    private String path="";

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
        tx_harga.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tx_harga.setText(t1.replaceAll("[^\\d]", ""));
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
                    int i=new ProdukModify().Hapus(Integer.parseInt(tmpId));
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
                if (tmpTambah!="" && tmpIdKat!="" && tx_harga.getText().trim().length()>0 && Integer.parseInt(tx_harga.getText().trim())>0 && tx_nama.getText().trim().length()>0){
                    Task<Integer> task=new Task<Integer>() {
                        @Override
                        protected Integer call() throws Exception {
                            DataProduk dp=new DataProduk();
                            dp.setId(Integer.parseInt(tmpId));
                            dp.setNama(tx_nama.getText().trim());
                            dp.setHarga(Double.parseDouble(tx_harga.getText().trim()));
                            dp.setKategori_id(Integer.parseInt(tmpIdKat));
                            dp.setTambahan(tmpTambah);

                            if (file!=null){
                                FileInputStream fis= null;
                                try {
                                    fis = new FileInputStream(file);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                dp.setGambar(fis);
                            }

                            int i=new ProdukModify().Ubah(dp);
                            return i;
                        }
                    };
                    task.setOnSucceeded(evt->{
                        if (task.getValue()>0)Refresh();
                    });

                    task.run();
                }
            }else {
                if (file!=null && tmpTambah!="" && tmpIdKat!="" && tx_harga.getText().trim().length()>0 && Integer.parseInt(tx_harga.getText().trim())>0 && tx_nama.getText().trim().length()>0){
                    Task<Integer>task=new Task<Integer>() {
                        @Override
                        protected Integer call() throws Exception {
                            DataProduk dp=new DataProduk();
                            dp.setNama(tx_nama.getText().trim());
                            dp.setHarga(Double.parseDouble(tx_harga.getText().trim()));
                            dp.setKategori_id(Integer.parseInt(tmpIdKat));
                            dp.setTambahan(tmpTambah);

                            FileInputStream fis= null;
                            try {
                                fis = new FileInputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            dp.setGambar(fis);

                            int i=new ProdukModify().Simpan(dp);
                            return i;
                        }
                    };
                    task.setOnSucceeded(event->{
                        if (task.getValue()>0)Refresh();
                    });
                    task.run();
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Data belum lengkap.");
                    alert.showAndWait();
                }
            }
        });

        ObservableList<String> opsi= FXCollections.observableArrayList();
        opsi.addAll("TIDAK","YA");
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
                PilihGambar();
            }
        });


        imageView.setOnMouseClicked(e->{
            PilihGambar();
        });

        table=new TabelProduk();
        table.setItems(new ProdukModify().SetTableItem());
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataProduk dp=(DataProduk) table.getSelectionModel().getSelectedItem();
                    tmpId=dp.getId()+"";
                    ket.setText(dp.getId()+"");
                    tx_nama.setText(dp.getNama());
                    int i=(int)dp.getHarga();
                    tx_harga.setText(""+i);
                    cb_tambahan.setValue(dp.getTambahan());

                    DataKategoriProduk dkp=new DataKategoriProduk();
                    dkp.setId(dp.getKategori_id());
                    dkp.setKategori(dp.getKategori());
                    dkp.setJenis(dp.getJenis());
                    cb_kategori.setValue(dkp);

                    path=System.getProperty("java.io.tmpdir").toString();
                    if (path.substring(path.length()-1,path.length())!="/") path+="/";
                    //System.out.println("temp dir: "+path);

                    imageView.setImage(imaji);

                    byte[] b=new ProdukModify().GetImage(tmpId);
                    if (b!=null){
                        Task<Void>task=new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                FileOutputStream fos = new FileOutputStream(path+tmpId+".jpg");
                                fos.write(b);
                                return null;
                            }
                        };
                        task.setOnSucceeded(e->{
                            File f=new File(path+tmpId+".jpg");
                            Image image=new Image(f.toURI().toString());
                            imageView.setImage(image);
                        });

                        Thread t=new Thread(task);
                        t.setDaemon(true);
                        t.start();
                    }


                }
            }
        });
    }

    private void PilihGambar(){
        file=fileChooser.showOpenDialog(Main.stage);
        if (file!=null){
            ket.setText(""+ file);
            Image image=new Image(file.toURI().toString());
            imageView.setImage(image);
        }
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
        tmpId="";

        table.setItems(new ProdukModify().SetTableItem());
    }
}
