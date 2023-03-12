package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

public class ConversorApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

       //URI uri = Paths.get("src/main/java/main/view/ViewUsuario.fxml").toAbsolutePath().toUri();
        //URI uri = Paths.get("src/main/java/com/challenge/conversor/view/ViewLogin.fxml").toAbsolutePath().toUri();
        //URI uri = Paths.get("src/main/java/main/view/ViewDivisa.fxml").toAbsolutePath().toUri();
        //URI uri = Paths.get("src/main/java/main/view/ViewCambio.fxml").toAbsolutePath().toUri();
        URI uri = Paths.get("src/main/java/main/view/ViewMain.fxml").toAbsolutePath().toUri();
        //Parent root = FXMLLoader.load(uri.toURL());
        //Scene scene = new Scene(root);
        //stage.setTitle("Acceso");
        //stage.setScene(scene);
        //stage.show();

        //URL url = getClass().getResource("/com/challenge/conversor/view/ViewDivisa.fxml");
        //URI uri = Paths.get("src/main/java/com/challenge/conversor/view/ViewDivisa.fxml").toAbsolutePath().toUri();
        //URI uri = Paths.get("src/main/java/com/challenge/conversor/view/prueba.fxml").toAbsolutePath().toUri();

        System.out.println("URL:"+ uri);
        Parent root = FXMLLoader.load(uri.toURL());
        Scene scene = new Scene(root);
        stage.setTitle("Acceso");
        stage.setScene(scene);
        stage.show();


    }

    public static void main()
    {
        launch();
    }
}