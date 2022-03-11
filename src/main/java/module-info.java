
module com.example.baitaonienluan {
    requires java.sql;
    requires javafx.media;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.rabbitmq.client;
    opens com.example.baitaonienluan to javafx.fxml;
    exports com.example.baitaonienluan;
}