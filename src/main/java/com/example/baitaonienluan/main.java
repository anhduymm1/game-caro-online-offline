package com.example.baitaonienluan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class main implements Initializable {
    @FXML
    private AnchorPane anchor1;
    @FXML
    private Pane pane_nhap;
    @FXML
    private TextField nguoichoi1;
    @FXML
    private TextField nguoichoi2;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private Label id_user;

    @FXML
    private ImageView music_mute;
    private Stage stage;
    private Scene scene;
    private MediaPlayer mediabg;
    private MediaPlayer mediaclick;
    private int mute;
    Connection connection = null;


    public void choivoiban(ActionEvent event) throws IOException {
        Soundbutton_click();
        PauseSound();
        pane_nhap.setVisible(true);
            ok.setOnAction(event1 -> {
                String nc1 = nguoichoi1.getText();
                String nc2 = nguoichoi2.getText();
                if (nc1.isEmpty()||nc2.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Vui l??ng ??i???n ?????y ????? th??ng tin");
                    DialogPane dialog = alert.getDialogPane();
                    dialog.setMinSize(200, 150);
                    dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                    dialog.getStyleClass().add("dialog");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.setResizable(false);
                    alert.showAndWait();
                }
                else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("banco.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                        root.getStylesheets().add(getClass().getResource("banco.css").toExternalForm());
                        Banco banco = loader.getController();
                        String ten1 = nguoichoi1.getText();
                        String ten2 = nguoichoi2.getText();
                        banco.user(ten1,ten2);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        cancel.setOnAction(event1 -> {
            pane_nhap.setVisible(false);
        });




    }
    public void choionline(ActionEvent event) throws IOException {
        Soundbutton_click();
        PauseSound();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("online.fxml"));
        Parent root= loader.load();
        root.getStylesheets().add(getClass().getResource("banco.css").toExternalForm());
        Online online = loader.getController();
        String ten=id_user.getText();
        online.user1(ten);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public String tendangnhap(String ten){
        id_user.setText(ten);
        return ten;
    }
    public void exit(ActionEvent event){
        Soundbutton_click();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("TH??NG B??O");
        alert.setHeaderText("B???n ch???c ch???n mu???n tho??t");

        ButtonType bttypeyes =new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType bttypeno =new ButtonType("NO",ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(bttypeyes,bttypeno);

        DialogPane dialog = alert.getDialogPane();
        dialog.setMinSize(200,150);
        dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
        dialog.getStyleClass().add("dialog");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setResizable(false);
        Optional<ButtonType> kq=alert.showAndWait();

        if (kq.get().getButtonData()==ButtonBar.ButtonData.YES) {
            System.exit(0);
        }


    }
    public void help(MouseEvent event)throws IOException{
        Soundbutton_click();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HELP");
        alert.setHeaderText(null);
        alert.setContentText("1.\tGi???i thi???u\n" +
                "???\tCaro c?? th??? n??i ?????n l?? m???t tr?? ch??i r???t quen thu???c ?????i v???i t???t c??? m???i ng?????i t???ng tr???i qua trong th???i h???c sinh, sinh vi??n. Nh??ng trong ??i???u ki???n c??ng ngh??? th??ng tin ph??t tri???n m???nh nh?? hi???n nay m???i ng?????i ?????u c?? thi???t b??? th??ng minh ri??ng v?? th??? m?? nhu c???u ch??i game m???i l??c, m???i n??i v???i b???n b?? hay nh???ng ng?????i tr??n m???ng c??ng tr??? n??n r???t ph??? bi???n. ???? l?? l?? do cho s??? ra m???t c???a game CARO ONLINE.\n" +
                "2.\tLu???t ch??i\n" +
                "???\tTr?? ch??i n??y ???????c ch??i tr??n b??n c??? g???m c??c ?? vu??ng nh?? ?? t???p c???a ch??ng ta. Hai ng?????i ch??i s??? thay phi??n nhau t??ch v??o nh???ng ?? ch??a ???????c ????nh ??? tr??n b??n c???. M???i ng?????i s??? c?? 1 k?? hi???u X ho???c O, v?? d??? m???t X th?? ng?????i c??n l???i l?? O.\n" +
                "???\tNg?????i ch??i s??? ph???i t???o th??nh m???t h??ng ngang, h??ng d???c, ho???c ???????ng ch??o c?? ????? 5 qu??n c??? c???a m??nh, ng?????i chi???n th???ng l?? ng?????i t???o ???????c h??ng, c???t ho???c ???????ng ch??o ????? 5 n?????c ??i c???a m??nh tr?????c. N???u nh?? 2 ng?????i ch??i ???? ??i h???t ?? tr??n b??n c??? m?? v???n ch??a t??m ra ng?????i th???ng th?? hai b??n h??a.\n");

        DialogPane dialog = alert.getDialogPane();
        dialog.setMinSize(200,150);
        dialog.getStylesheets().add(getClass().getResource("help.css").toString());
        dialog.getStyleClass().add("dialog");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setResizable(false);
        alert.showAndWait();

    }
    public void Soundbackground(){
        Thread thread1 =new Thread(new Runnable() {
            @Override
            public void run() {
                String soundbg ="StardewValleyOverture-VA-5776100.mp3";
                Media hit = new Media(new File(soundbg).toURI().toString());
                mediabg = new MediaPlayer(hit);
                mediabg.setVolume(0.25);
                mediabg.play();
                mute=1;
            }
        });
        thread1.start();
    }
    public void PauseSound() throws FileNotFoundException {

        Soundbutton_click();
       if (mute==1) {
           mediabg.stop();
           mute = 0;
           FileInputStream fileInputStream = new FileInputStream("img/mute.png");
           music_mute.setImage(new Image(fileInputStream));
       }
       else {
           mute = 1;
           mediabg.play();
           FileInputStream fileInputStream = new FileInputStream("img/music.png");
           music_mute.setImage(new Image(fileInputStream));
       }
    }
    public void Soundbutton_click(){
        Thread thread1 =new Thread(new Runnable() {
            @Override
            public void run() {
                String soundbg ="button_click.mp3";
                Media hit = new Media(new File(soundbg).toURI().toString());
                mediaclick = new MediaPlayer(hit);
                mediaclick.setVolume(0.25);
                mediaclick.play();
            }
        });
        thread1.start();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane_nhap.setVisible(false);
        connection =dbconnect.getConnection();
        Soundbackground();
    }

}
