package com.example.baitaonienluan;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dangnhap implements Initializable {
    @FXML
    private TextField textTaikhoan;
    @FXML
    private TextField textMatkhau;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private Button btndn;

    private Stage stage;
    private Scene scene;
    private String taikhoan;
    private MediaPlayer mediaclick;
    Connection connection = null;
    PreparedStatement preparedStatement = null;


    public void dangnhap(ActionEvent event) throws IOException, InterruptedException {
        Soundbutton_click();
        connection =dbconnect.getConnection();
        String hoten = textTaikhoan.getText();
        String matkhau = textMatkhau.getText();
        if (hoten.isEmpty() || matkhau.isEmpty() ) {
            BoxBlur blur = new BoxBlur(2,2,2);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin");
            anchorPane1.setEffect(blur);


            DialogPane dialog = alert.getDialogPane();
            dialog.setMinSize(200,150);
            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setResizable(false);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK) {
                alert.close();
                anchorPane1.setEffect(null);
            }

        }
        else if (ktdangnhap()==true) {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            main Main =loader.getController();
            taikhoan=Main.tendangnhap(hoten);
            Scene scene = btndn.getScene();
            root.translateXProperty().set(scene.getWidth());
            StackPane stackPane =(StackPane)scene.getRoot();
            stackPane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                stackPane.getChildren().remove(anchorPane1);
            });
            timeline.play();

        }
        else
        {
            BoxBlur blur = new BoxBlur(2,2,2);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Đăng nhập thất bại");

            DialogPane dialog = alert.getDialogPane();
            dialog.setMinSize(200,150);
            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setResizable(false);
            anchorPane1.setEffect(blur);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK) {
                alert.close();
                anchorPane1.setEffect(null);
            }
        }


    }

    public void dangki(ActionEvent event) throws IOException{
        Soundbutton_click();
        Parent root = FXMLLoader.load(getClass().getResource("dangki.fxml"));
        root.getStylesheets().add(getClass().getResource("dangki.css").toExternalForm());
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public boolean ktdangnhap(){
        try {
            String query1 = "SELECT taikhoan FROM caro.user WHERE taikhoan=? AND matkhau=?";
            preparedStatement =connection.prepareStatement(query1);
            preparedStatement.setString(1,textTaikhoan.getText());
            preparedStatement.setString(2,textMatkhau.getText());
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
}
