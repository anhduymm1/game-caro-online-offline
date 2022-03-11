package com.example.baitaonienluan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dangki implements Initializable {
    @FXML
    private TextField textHoten;

    @FXML
    private TextField textMatkhau;

    @FXML
    private TextField textNLMatkhau;

    private MediaPlayer mediaclick;
    private Stage stage;
    private Scene scene;
    Connection connection = null;
    String query = null;
    PreparedStatement preparedStatement = null;

    public void trove(ActionEvent event) throws IOException{
        Soundbutton_click();
        Parent root = FXMLLoader.load(getClass().getResource("dangnhap.fxml"));
        root.getStylesheets().add(getClass().getResource("dangnhap.css").toExternalForm());
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void themtaikhoan(ActionEvent event) throws IOException {
        Soundbutton_click();
        connection =dbconnect.getConnection();
        String hoten = textHoten.getText();
        String matkhau = textMatkhau.getText();
        String nhaplaimatkhau =textNLMatkhau.getText();
        if (hoten.isEmpty() || matkhau.isEmpty() || nhaplaimatkhau.isEmpty() ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin");
            DialogPane dialog = alert.getDialogPane();
            dialog.setMinSize(200,150);
            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setResizable(false);
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("dangki.fxml"));
            root.getStylesheets().add(getClass().getResource("dangki.css").toExternalForm());
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        }
        else if (!matkhau.equals(nhaplaimatkhau)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Mật khẩu phải trùng với nhập lại mật khẩu");

            DialogPane dialog = alert.getDialogPane();
            dialog.setMinSize(200,150);
            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setResizable(false);
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("dangki.fxml"));
            root.getStylesheets().add(getClass().getResource("dangki.css").toExternalForm());
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        }
        else if (ktdangki()==false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản đã tồn tại");

            DialogPane dialog = alert.getDialogPane();
            dialog.setMinSize(200,150);
            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setResizable(false);
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("dangki.fxml"));
            root.getStylesheets().add(getClass().getResource("dangki.css").toExternalForm());
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        }
        else {
            insertQuery();
            insert();
            clean();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Thông báo");
            alert.setContentText("Đăng kí thành công");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("dangnhap.fxml"));
            root.getStylesheets().add(getClass().getResource("dangnhap.css").toExternalForm());
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void clean() {
        textHoten.setText(null);
        textMatkhau.setText(null);
        textNLMatkhau.setText(null);
    }
    private void insertQuery(){
        query = "INSERT INTO `caro`.`user` (`taikhoan`, `matkhau`) VALUES (?, ?);";
    }

    public void insert() {

        try {

            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,textHoten.getText());
            preparedStatement.setString(2,textMatkhau.getText());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean ktdangki(){
        try {
            String query1 = "SELECT taikhoan FROM caro.user WHERE taikhoan=?";
            preparedStatement =connection.prepareStatement(query1);
            preparedStatement.setString(1,textHoten.getText());
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
                return false;
            else
                return true;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
