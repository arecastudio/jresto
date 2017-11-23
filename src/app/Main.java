package app;

import app.ctrl.Statis;
import app.view.Footer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public BorderPane borderPane;
    public Stage stage;

    public static MenuBar menuBar;
    public static MenuItem pokok_kampung,pilih_kampung,log_out;
    public static MenuItem penggunaan_lahan,produksi_pertanian_perkebunan,kehutanan,peternakan,sumber_air,obyek_wisata,sumber_daya_mc;
    public static MenuItem rep_penggunaan_lahan,rep_produksi_pertanian_perkebunan,rep_kehutanan,rep_peternakan,rep_sumber_air,rep_obyek_wisata,rep_sumber_daya_mc;
    public static MenuItem set_user,set_role,set_db,manual_book,abouts;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage=stage;

        Inits();

        borderPane.setTop(menuBar);
        borderPane.setBottom(new Footer());

        stage.setTitle("Hello World");
        stage.setScene(new Scene(borderPane, 1024, 500));
        //stage.setMaximized(true);
        stage.show();
    }

    private void Inits() {
        borderPane=new BorderPane();
        borderPane.setStyle(new Statis().getSTYLE_MAIN());

        menuBar = new MenuBar();

        Menu menuBerkas = new Menu("Berkas");
        pokok_kampung=new MenuItem("Profil Kampung");
        pilih_kampung=new MenuItem("Pilih Kampung");
        log_out=new MenuItem("Log out");
        menuBerkas.getItems().addAll(pokok_kampung,pilih_kampung,new SeparatorMenuItem(),log_out);

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
            //borderPane.setCenter(new Login());
        });

        pokok_kampung.setOnAction(event -> {
            //borderPane.setCenter(new Kampung());
        });

        set_user.setOnAction(e->{
            //borderPane.setCenter(new SetUser());
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
