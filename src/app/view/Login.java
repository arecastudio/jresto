package app.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import app.Main;
import app.ctrl.Statis;

public class Login extends VBox {
    private TextField text_user;
    private PasswordField text_pass;
    private Button button_ok,button_cancel;

    public Login(){
        Inits();

        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.add(new Label("User name"),0,0);
        grid.add(text_user,1,0);

        grid.add(new Label("Password"),0,1);
        grid.add(text_pass,1,1);

        HBox hbox=new HBox(button_ok,button_cancel);
        hbox.setSpacing(5);hbox.setAlignment(Pos.CENTER);

        getChildren().addAll(new Judul("Login User"),new Separator(Orientation.HORIZONTAL),grid,hbox,new Separator(Orientation.HORIZONTAL),new Label("Masukkan User name dan Password\nuntuk mengakses menu utama."));

        text_user.requestFocus();
    }

    private void Inits() {
        setSpacing(15);
        setPadding(new Insets(5,5,5,5));
        setMaxHeight(250);
        setMaxWidth(400);
        setStyle(new Statis().getSTYLE_BOX());
        setAlignment(Pos.TOP_CENTER);

        text_user=new TextField("1");
        text_pass=new PasswordField();
        text_pass.setText("1");


        button_cancel=new Button("Cancel");
        button_cancel.setPrefWidth(100);
        button_cancel.setOnAction(e->{
            Platform.exit();
        });

        button_ok=new Button("Login");
        button_ok.setPrefWidth(100);
        button_ok.setOnAction(e->{
            if (text_user.getText().trim().length()>0 && text_pass.getText().trim().length()>0){
                if (text_user.getText().trim().equals("1") && text_pass.getText().trim().equals("1")){
                    VBox vbox=new VBox(new Judul("Welcome."));
                    vbox.setAlignment(Pos.CENTER);
                    Main.borderPane.setCenter(vbox);
                    Main.borderPane.setTop(Main.vbox);
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert Dialog");
                    alert.setHeaderText("Terjadi kesalahan.");
                    alert.setContentText("Informasil login yang dimasukkan tidak valid.");
                    alert.showAndWait();
                }
            }
        });
    }
}
