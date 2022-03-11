package com.example.baitaonienluan;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Online implements Initializable {
    @FXML
    private Pane paness;
    @FXML
    private Button SS;
    @FXML
    private Button KSS;
    @FXML
    private Label tongo;
    @FXML
    private Label tongx;
    @FXML
    private ImageView quaylai1;
    @FXML
    private Pane banco;
    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label luot_o ;
    @FXML
    private Label luot_x ;
    @FXML
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,button19,button20,button21,button22,button23,button24,button25,button26,button27,button28,button29,button30,button31,button32,button33,button34,button35,button36,button37,button38,button39,button40,button41,button42,button43,button44,button45,button46,button47,button48,button49;
    Random random =new Random();
    ArrayList<Button> buttons;
    boolean turno = true;
    boolean turnx = false;
    private int ox;
    private Stage stage;
    private Scene scene;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private MediaPlayer mediabt,mediawinner,mediaclick;
    boolean ditruoc;
    int nhan=1;
    private static final String EXCHANGE_NAME = "logs";
    private static final String CLOUDAMQP_URL = "amqps://zmrghrsm:NlfITHQpEpEphn3kBavnYDeOs2D7ZcvQ@gerbil.rmq.cloudamqp.com/zmrghrsm";
    private static final String QUEUE_Nhan = "duy1";
    private static final String QUEUE_Gui = "duy2";
    String queueName;
    ConnectionFactory factory = new ConnectionFactory();
    com.rabbitmq.client.Connection connection1;
    Channel channel;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    public void online() throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        System.out.println("Create a ConnectionFactory");
        factory.setUri(CLOUDAMQP_URL);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        System.out.println("Create a Connection");
        System.out.println("Create a Channel");
        connection1 = factory.newConnection();
        channel = connection1.createChannel();
        System.out.println("Create a queue " + QUEUE_Nhan);
        channel.queueDeclare(QUEUE_Nhan, false, false, false, null);
        System.out.println("Start sending messages ... ");
    }
    public void online1() throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        System.out.println("Create a ConnectionFactory");
        factory.setUri(CLOUDAMQP_URL);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        System.out.println("Create a Connection");
        System.out.println("Create a Channel");
        connection1 = factory.newConnection();
        channel = connection1.createChannel();
        System.out.println("Create a queue " + QUEUE_Gui);
        channel.queueDeclare(QUEUE_Gui, false, false, false, null);
        System.out.println("Start sending messages ... ");
    }

    public void nhanluot() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(CLOUDAMQP_URL);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        com.rabbitmq.client.Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName1 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName1, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            String s [] = message.split("duy");
            System.out.println(" [x] Received '" + message + "'");
            /*if (s[0].equals(queueName)&&s[1].equals("false")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        user2.setText(s[2]);
                        luot_x.setText("Lượt X");
                    }
                });
            }*/
            if (!s[0].equals(queueName)&&s[1].equals("false")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SS.setDisable(true);
                        user2.setText(s[2]);

                    }
                });
            }
            /*if (s[0].equals(queueName)&&s[1].equals("true")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        user1.setText(s[2]);
                        luot_x.setText("Lượt X");
                    }
                });
            }*/
            if (!s[0].equals(queueName)&&s[1].equals("true")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //user2.setText(user1.getText());
                        KSS.setDisable(true);
                        user1.setText(s[2]);
                    }
                });
            }

        };
        channel.basicConsume(queueName1, true, deliverCallback, consumerTag -> { });


    }

    public void chonluot() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        SS.setOnAction(event -> {
            ditruoc=turnx;
            paness.setVisible(false);
            banco.setDisable(false);
            banco.setEffect(null);
            ConnectionFactory factory = new ConnectionFactory();
            try {
                factory.setUri(CLOUDAMQP_URL);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);
            try (com.rabbitmq.client.Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                queueName = channel.queueDeclare().getQueue();
                String message = queueName + "duy"+turnx+"duy"+user2.getText();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            nhancoo();
        });
        KSS.setOnAction(event -> {
            ditruoc=turno;
            paness.setVisible(false);
            banco.setDisable(false);
            banco.setEffect(null);
            ConnectionFactory factory = new ConnectionFactory();
            try {
                factory.setUri(CLOUDAMQP_URL);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);
            try (com.rabbitmq.client.Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                queueName = channel.queueDeclare().getQueue();
                String message = queueName + "duy"+turno+"duy"+user1.getText();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            nhancox();
        });

    }

    public void nhancoo() {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received: '" + message + "'");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 49; i++) {
                        if (buttons.get(i).getId().equals(message)) {
                            buttons.get(i).setText("O");
                            buttons.get(i).setTextFill(Paint.valueOf("green"));
                            nhan=1;
                            luot_x.setText("Lượt X");
                            luot_o.setText("");
                            if (checkwin() == true) {
                                alert.setContentText("0");
                                int owin= Integer.parseInt(tongo.getText());
                                int tongdiem=owin+3;
                                int demo=owin+=1;
                                tongo.setText(String.valueOf(demo));
                                try {
                                    show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else if (checkdraw() == true) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText(null);
                                alert.setContentText("Draw");

                                DialogPane dialog = alert.getDialogPane();
                                dialog.setMinSize(200, 150);
                                dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                                dialog.getStyleClass().add("dialog");
                                alert.initStyle(StageStyle.UNDECORATED);
                                alert.setResizable(false);

                                alert.showAndWait();

                            }
                        }
                    }
                }
            });

        };
        CancelCallback cancelCallback = consumerTag -> {
        };
        String consumerTag = null;
        try {
            consumerTag = channel.basicConsume(QUEUE_Gui, true, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void nhancox() {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received: '" + message + "'");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 49; i++) {
                        if (buttons.get(i).getId().equals(message)) {
                            buttons.get(i).setText("X");
                            buttons.get(i).setTextFill(Paint.valueOf("red"));
                            nhan=1;
                            luot_o.setText("Lượt O");
                            luot_x.setText("");
                            if (checkwin() == true) {
                                alert.setContentText("X");
                                int xwin= Integer.parseInt(tongx.getText());
                                int tongdiem=xwin+3;
                                int demx=xwin+=1;
                                tongx.setText(String.valueOf(demx));
                                try {
                                    show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else if (checkdraw() == true) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText(null);
                                alert.setContentText("Draw");

                                DialogPane dialog = alert.getDialogPane();
                                dialog.setMinSize(200, 150);
                                dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                                dialog.getStyleClass().add("dialog");
                                alert.initStyle(StageStyle.UNDECORATED);
                                alert.setResizable(false);

                                alert.showAndWait();

                            }
                        }
                    }
                }
            });

        };
        CancelCallback cancelCallback = consumerTag -> {
        };
        String consumerTag = null;
        try {
            consumerTag = channel.basicConsume(QUEUE_Nhan, true, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void danhco (Button button) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        connection = dbconnect.getConnection();
        int dem = 0;
        if (ditruoc == turno&&nhan==1){
            Soundbutton();
            if (button.getText() == "") {
                button.setTextFill(Paint.valueOf("green"));
                button.setText("O");
                if (button.getText().equals("O")) {
                    channel.basicPublish("", QUEUE_Gui, null, button.getId().getBytes());
                    System.out.println(" [x] Sent: '" + button.getId() + "'");
                }
                luot_o.setText("");
                luot_x.setText("Lượt X");
                nhan=0;
                //nhancox();
                if (checkwin() == true) {
                    alert.setContentText("0");
                    int owin= Integer.parseInt(tongo.getText());
                    int tongdiem=owin+3;
                    int demo=owin+=1;
                    tongo.setText(String.valueOf(demo));
                    try {
                        show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (checkdraw() == true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Draw");

                    DialogPane dialog = alert.getDialogPane();
                    dialog.setMinSize(200, 150);
                    dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
                    dialog.getStyleClass().add("dialog");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.setResizable(false);

                    alert.showAndWait();

                }

            }
        }
        if (ditruoc == turnx&&nhan==1){
            if (button.getText() == "") {
                button.setTextFill(Paint.valueOf("red"));
                button.setText("X");
                if (button.getText().equals("X")) {
                    channel.basicPublish("", QUEUE_Nhan, null, button.getId().getBytes());
                    System.out.println(" [x] Sent: '" + button.getId() + "'");
                }
                luot_x.setText("");
                luot_o.setText("Lượt O");
                nhan=0;
               // nhancoo();
                if (checkwin() == true) {
                    alert.setContentText("X");
                    int xwin= Integer.parseInt(tongx.getText());
                    int tongdiem=xwin+3;
                    int demx=xwin+=1;
                    tongx.setText(String.valueOf(demx));
                    try {
                        show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (checkdraw() == true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Draw");

                    DialogPane dialog = alert.getDialogPane();
                    dialog.setMinSize(200, 150);
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
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
    }

    public void show() throws IOException {
        BoxBlur blur = new BoxBlur(2,2,2);

        alert.setHeaderText("Bạn có muốn tiếp tục không ?");
        banco.setEffect(blur);


        DialogPane dialog = alert.getDialogPane();
        dialog.setMinSize(200,150);
        dialog.getStylesheets().add(getClass().getResource("AlertCSS.css").toString());
        dialog.getStyleClass().add("dialog");
        alert.setResizable(false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== ButtonType.OK) {
            alert.close();
            buttons.forEach(this::resetButton);
            banco.setEffect(null);

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
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        main main =loader.getController();
        main.tendangnhap(user1.getText());
        Scene scene = new Scene(root);
        stage =(Stage) quaylai1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void user1(String ten){
        user1.setText(ten);
        user2.setText(ten);
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
        BoxBlur blur = new BoxBlur(2,2,2);
        alert.initStyle(StageStyle.TRANSPARENT);
        banco.setDisable(true);
        banco.setEffect(blur);
        buttons =new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,button19,button20,button21,button22,button23,button24,button25,button26,button27,button28,button29,button30,button31,button32,button33,button34,button35,button36,button37,button38,button39,button40,button41,button42,button43,button44,button45,button46,button47,button48,button49));
        buttons.forEach(button -> {
            setButton(button);
        });
        restart();
        try {
            online1();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            online();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            nhanluot();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            chonluot();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
