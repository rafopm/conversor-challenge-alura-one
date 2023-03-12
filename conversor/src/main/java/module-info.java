module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires mysql.connector.j;
    requires java.sql;

    opens main.model to javafx.base; // Ruta para acceder al paquete modelo
    opens main.controller to javafx.fxml; //Ruta de controladores
    exports main;
}