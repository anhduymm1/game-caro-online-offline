package com.example.baitaonienluan;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro implements Initializable {
    @FXML
    private Button boqua;
    @FXML
    private MediaView intro;
    @FXML
    private ProgressBar processbar;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPane;
    private MediaPlayer mediaclick;
    private Stage stage;
    private Scene scene;
    public void intro() {
        Thread thread1 =new Thread(new Runnable() {
            @Override
            public void run() {
                String intro1 = "CARO.mp4";
                Media media = new Media(new File(intro1).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                intro.setMediaPlayer(mediaPlayer);
            }
        });
        thread1.start();
    }
    public void boqua(ActionEvent event)throws IOException{
        Soundbutton_click();
        Parent root = FXMLLoader.load(getClass().getResource("dangnhap.fxml"));
        root.getStylesheets().add(getClass().getResource("dangnhap.css").toExternalForm());
        Scene scene = boqua.getScene();
        root.translateYProperty().set(scene.getHeight());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();

    }
    public void fill() {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                int counter =0;
                while(counter<=1000) {
                    processbar.setProgress(counter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    counter +=1;
                }
            }
        });
        thread.start();
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
        boqua.setVisible(true);
        if (processbar.getProgress()==1000)
            boqua.setVisible(false);
        processbar.setStyle("-fx-accent: green");
        intro();
        fill();

    }
}
