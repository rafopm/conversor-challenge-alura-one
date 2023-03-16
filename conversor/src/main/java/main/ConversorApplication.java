package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

public class ConversorApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        URI uri = Paths.get("src/main/java/main/view/ViewLogin.fxml").toAbsolutePath().toUri();

        Parent root = FXMLLoader.load(uri.toURL());
        Scene scene = new Scene(root);
        URI uri2 = Paths.get("src/main/java/main/resources/img/Recurso 2.png").toAbsolutePath().toUri();
        stage.getIcons().add(new Image(uri2.toURL().openStream()));
        stage.setTitle("Acceso");
        stage.setScene(scene);
        stage.show();


    }

    public static void main()
    {
        launch();
    }
}