package com.example.baitaonienluan;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class  Banco implements Initializable {
    @FXML
    private ImageView quaylai1;
    @FXML
    private AnchorPane gp1;
    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label tongo;
    @FXML
    private Label tongx;
    @FXML
    private Label luot_o ;
    @FXML
    private Label luot_x ;
    @FXML
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,button19,button20,button21,button22,button23,button24,button25,button26,button27,button28,button29,button30,button31,button32,button33,button34,button35,button36,button37,button38,button39,button40,button41,button42,button43,button44,button45,button46,button47,button48,button49;
    Random random =new Random();
    ArrayList<Button> buttons;
    private  Stage stage;
    private Scene scene;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private MediaPlayer mediabt,mediawinner,mediaclick;
    boolean ditruoc;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    public void luot(){
        if (random.nextInt(2)==0){
            ditruoc =true;
            luot_o.setText("Lượt O");
        }
        else{
            ditruoc=false;
            luot_x.setText("Lượt X");
        }
    }

    public void danhco (Button button) throws IOException {
        connection =dbconnect.getConnection();
        int dem=0;
                if (ditruoc==true){Soundbutton();
                    if (button.getText()==""){
                        button.setTextFill(Paint.valueOf("green"));
                        button.setText("O");
                        ditruoc=false;
                        luot_o.setText("");
                        luot_x.setText("Lượt X");
                        if (checkwin()==true) {
                            alert.setContentText("0 WIN");
                            show();
                            int owin= Integer.parseInt(tongo.getText());
                            int tongdiem=owin+3;
                            int demo=owin+=1;
                            tongo.setText(String.valueOf(demo));

                        }
                        else if (checkdraw()==true){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Draw");

                            DialogPane dialog = alert.getDialogPane();
                            dialog.setMinSize(200,150);
                            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                            dialog.getStyleClass().add("dialog");
                            alert.initStyle(StageStyle.UNDECORATED);
                            alert.setResizable(false);

                            alert.showAndWait();
                        }

                    }
                }

                else {
                    Soundbutton();
                    if (button.getText()==""){
                        button.setTextFill(Paint.valueOf("red"));
                        button.setText("X");
                        ditruoc=true;
                        luot_x.setText("");
                        luot_o.setText("Lượt O");
                        if (checkwin()==true){
                            alert.setContentText("X WIN");
                            show();
                            int xwin= Integer.parseInt(tongx.getText());
                            int tongdiem=xwin+3;
                            int demx=xwin+=1;
                            tongx.setText(String.valueOf(demx));
                        }
                        else if (checkdraw()==true){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Draw");

                            DialogPane dialog = alert.getDialogPane();
                            dialog.setMinSize(200,150);
                            dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                            dialog.getStyleClass().add("dialog");
                            alert.initStyle(StageStyle.UNDECORATED);
                            alert.setResizable(false);
                            alert.showAndWait();
                        }

                    }
                }
            }

    public void setButton(Button button){
        button.setOnMouseClicked(mouseEvent -> {
            try {
                danhco(button);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void show() throws IOException {
        BoxBlur blur = new BoxBlur(2,2,2);

        alert.setHeaderText("Bạn có muốn tiếp tục không ?");
        gp1.setEffect(blur);


        DialogPane dialog = alert.getDialogPane();
        dialog.setMinSize(200,150);
        dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
        dialog.getStyleClass().add("dialog");
        alert.setResizable(false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== ButtonType.OK) {
            alert.close();
            buttons.forEach(this::resetButton);
            gp1.setEffect(null);

        }
        else if (result.get()== ButtonType.CANCEL){
            back();
        }

    }
    public void back() throws IOException{
        Soundbutton_click();
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        Parent root =loader.load();
        root.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        main main =loader.getController();
        main.tendangnhap(user1.getText());
        Scene scene = new Scene(root);
        stage =(Stage) quaylai1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void user(String ten1,String ten2){
        user1.setText(ten1);
        user2.setText(ten2);
    }

    @FXML
    void restart(){
        Soundbutton_click();
        buttons.forEach(this::resetButton);
    }
    public void resetButton(Button button){

        button.setDisable(false);
        button.setText("");
        luot_x.setText("");
        luot_o.setText("");
        luot();
    }

    public boolean checkdraw(){
        int dem=0;
        for (int i=0;i<49;i++){
            if (buttons.get(i).getText().equals("X")||buttons.get(i).getText().equals("O"))
                dem++;
        }
        if (dem==49) return true;
        return false;
    }
    public boolean checkwin(){
        if (checkcol()==true||checkrow()==true||checkcheo()==true) {
            Soundwinner();
            return true;
        }
        return false;
    }
    public boolean checkrow(){
        int i=0;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 1).getText()) && buttons.get(i).getText().equals(buttons.get(i + 2).getText()) && buttons.get(i).getText().equals(buttons.get(i + 3).getText()) && buttons.get(i).getText().equals(buttons.get(i + 4).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        i=1;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 1).getText()) && buttons.get(i).getText().equals(buttons.get(i + 2).getText()) && buttons.get(i).getText().equals(buttons.get(i + 3).getText()) && buttons.get(i).getText().equals(buttons.get(i + 4).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;

        }
        i=2;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 1).getText()) && buttons.get(i).getText().equals(buttons.get(i + 2).getText()) && buttons.get(i).getText().equals(buttons.get(i + 3).getText()) && buttons.get(i).getText().equals(buttons.get(i + 4).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        return false;
    }
    public boolean checkcol(){
        int i=0;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 7).getText()) && buttons.get(i).getText().equals(buttons.get(i + 14).getText()) && buttons.get(i).getText().equals(buttons.get(i + 21).getText()) && buttons.get(i).getText().equals(buttons.get(i + 28).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i++;
        }
        i=7;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 7).getText()) && buttons.get(i).getText().equals(buttons.get(i + 14).getText()) && buttons.get(i).getText().equals(buttons.get(i + 21).getText()) && buttons.get(i).getText().equals(buttons.get(i + 28).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i++;
        }
        i=14;
        for (int j=0;j<7;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 7).getText()) && buttons.get(i).getText().equals(buttons.get(i + 14).getText()) && buttons.get(i).getText().equals(buttons.get(i + 21).getText()) && buttons.get(i).getText().equals(buttons.get(i + 28).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i++;
        }
        return false;
    }
    public boolean checkcheo(){
        // Check duong cheo duoi \
        int i=0;
        for (int j=0;j<3;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 8).getText()) && buttons.get(i).getText().equals(buttons.get(i + 16).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && buttons.get(i).getText().equals(buttons.get(i + 32).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        i=8;
        for (int j=0;j<2;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 8).getText()) && buttons.get(i).getText().equals(buttons.get(i + 16).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && buttons.get(i).getText().equals(buttons.get(i + 32).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        i=16;
        for (int j=0;j<1;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 8).getText()) && buttons.get(i).getText().equals(buttons.get(i + 16).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && buttons.get(i).getText().equals(buttons.get(i + 32).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        // Check duong cheo tren \
        i=1;
        for (int j=0;j<2;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 8).getText()) && buttons.get(i).getText().equals(buttons.get(i + 16).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && buttons.get(i).getText().equals(buttons.get(i + 32).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=8;
        }
        i=2;
        for (int j=0;j<1;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 8).getText()) && buttons.get(i).getText().equals(buttons.get(i + 16).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && buttons.get(i).getText().equals(buttons.get(i + 32).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i++;
        }
        // Check duong cheo duoi /
        i=6;
        for (int j=0;j<3;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 6).getText()) && buttons.get(i).getText().equals(buttons.get(i + 12).getText()) && buttons.get(i).getText().equals(buttons.get(i + 18).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        i=12;
        for (int j=0;j<2;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 6).getText()) && buttons.get(i).getText().equals(buttons.get(i + 12).getText()) && buttons.get(i).getText().equals(buttons.get(i + 18).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        i=18;
        for (int j=0;j<1;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i + 6).getText()) && buttons.get(i).getText().equals(buttons.get(i + 12).getText()) && buttons.get(i).getText().equals(buttons.get(i + 18).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=7;
        }
        // Check duong cheo tren /
        i=5;
        for (int j=0;j<2;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i +6).getText()) && buttons.get(i).getText().equals(buttons.get(i + 12).getText()) && buttons.get(i).getText().equals(buttons.get(i + 18).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
            i+=6;
        }
        i=4;
        for (int j=0;j<1;j++) {
            if (buttons.get(i).getText().equals(buttons.get(i +6).getText()) && buttons.get(i).getText().equals(buttons.get(i + 12).getText()) && buttons.get(i).getText().equals(buttons.get(i + 18).getText()) && buttons.get(i).getText().equals(buttons.get(i + 24).getText()) && (buttons.get(i).getText() != "")){
                return true;
            }
        }
        return false;
    }

    public void Soundbutton(){
        Thread thread1 =new Thread(new Runnable() {
            @Override
            public void run() {
                String soundbg ="click.mp3";
                Media hit = new Media(new File(soundbg).toURI().toString());
                mediabt = new MediaPlayer(hit);
                mediabt.setVolume(0.25);
                mediabt.play();
            }
        });
        thread1.start();
    }
    public void Soundwinner(){
        Thread thread2 =new Thread(new Runnable() {
            @Override
            public void run() {
                String soundwn ="winner.mp3";
                Media winner = new Media(new File(soundwn).toURI().toString());
                mediawinner = new MediaPlayer(winner);
                mediawinner.setVolume(1);
                mediawinner.play();
            }
        });
        thread2.start();
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
        alert.initStyle(StageStyle.TRANSPARENT);
        luot();
        buttons =new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,button19,button20,button21,button22,button23,button24,button25,button26,button27,button28,button29,button30,button31,button32,button33,button34,button35,button36,button37,button38,button39,button40,button41,button42,button43,button44,button45,button46,button47,button48,button49));
        buttons.forEach(button -> {
            setButton(button);
            //button.setFocusTraversable(false);
        });
    }

}