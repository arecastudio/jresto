package app;

import app.ctrl.Statis;
import app.view.*;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static BorderPane borderPane;
    public static Stage stage;
    public static VBox vbox;

    private ToolBar toolBar;

    public static MenuBar menuBar;
    public static MenuItem data_produk,data_kategori_produk,data_meja,data_kategori_meja,non_tunai,log_out;
    public static MenuItem penggunaan_lahan,produksi_pertanian_perkebunan,kehutanan,peternakan,sumber_air,obyek_wisata,sumber_daya_mc;
    public static MenuItem rep_penggunaan_lahan,rep_produksi_pertanian_perkebunan,rep_kehutanan,rep_peternakan,rep_sumber_air,rep_obyek_wisata,rep_sumber_daya_mc;
    public static MenuItem set_user,set_role,set_db,manual_book,abouts;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage=stage;

        Inits();

        vbox.getChildren().addAll(menuBar,toolBar);

        borderPane.setTop(vbox);
        borderPane.setBottom(new Footer());

        stage.setTitle("JResto POS Payment 2.0");
        stage.setScene(new Scene(borderPane, 1024, 500));
        stage.setMaximized(true);
        stage.show();
    }

    private void Inits() {
        borderPane=new BorderPane();
        borderPane.setStyle(new Statis().getSTYLE_MAIN());

        vbox=new VBox();
        vbox.setAlignment(Pos.CENTER_LEFT);

        menuBar = new MenuBar();

        toolBar=new ToolBar(new Button("Pembayaran"),new Separator(Orientation.VERTICAL),new Button("List Pesanan"));

        Menu menuBerkas = new Menu("Berkas");
        data_produk=new MenuItem("Data Produk");
        data_kategori_produk=new MenuItem("Kategori Produk");
        data_meja=new MenuItem("Data Meja");
        data_kategori_meja=new MenuItem("Kategori Meja");
        non_tunai=new MenuItem("Non Tunai");
        log_out=new MenuItem("Log out");
        menuBerkas.getItems().addAll(data_produk,data_kategori_produk,data_meja,data_kategori_meja,new SeparatorMenuItem(),non_tunai,new SeparatorMenuItem(),log_out);

        Menu menuProses=new Menu("Proses");
        penggunaan_lahan=new MenuItem("Penggunaan Lahan");
        menuProses.getItems().addAll(penggunaan_lahan);

        Menu menuReport=new Menu("Laporan");
        rep_penggunaan_lahan=new MenuItem("Penggunaan Lahan");
        menuReport.getItems().addAll(rep_penggunaan_lahan);

        Menu menuSetting=new Menu("Pengaturan");
        set_user=new MenuItem("Pengaturan User");
        menuSetting.getItems().addAll(set_user);


        menuBar.getMenus().addAll(menuBerkas,menuProses,menuReport,menuSetting);
        
        ClickMenu();
    }

    private void ClickMenu() {
        log_out.setOnAction(event -> {
            borderPane.setTop(null);
            //borderPane.setCenter(null);
            borderPane.setCenter(new Login());
        });

        data_produk.setOnAction(event -> {
            borderPane.setCenter(new Produk());
        });

        data_kategori_produk.setOnAction(e->{
            borderPane.setCenter(new KategoriProduk());
        });

        data_kategori_meja.setOnAction(e->{

            borderPane.setCenter(new KetegoriMeja());
        });

        non_tunai.setOnAction(e->{
            borderPane.setCenter(new NonTunai());
        });

        set_user.setOnAction(e->{
            //borderPane.setCenter(new SetUser());
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
