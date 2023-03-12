package main.controller;

import main.dao.UsuarioDao;
import main.model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewLoginController implements Initializable {
    ViewMainController ViewMainController;
    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;
    private UsuarioDao usuarioDao;
    private Usuario usuarioSelect;


    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();
    }
    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        Usuario usuario = new Usuario();
        usuario.setUsuario(txtUsuario.getText());
        usuario.setPassword((txtPassword.getText()));

        Usuario usuarioViajero = this.usuarioDao.login(usuario.getUsuario(), usuario.getPassword());
        System.out.println("Estado "+usuarioViajero);

        if (usuarioViajero != null ){
            Object eventSource = event.getSource();
            Node sourceAsNode = (Node) eventSource ;
            Scene oldScene = sourceAsNode.getScene();
            Window window = oldScene.getWindow();
            Stage stage = (Stage) window ;
            stage.hide();

/*
            Parent root = FXMLLoader.load(uri.toURL());
            ViewMainController mainControllerInstancia = (ViewMainController)loader.getController();
            System.out.println("fdfdf"+state);
            //mainControllerInstancia.recibeParametros(state);

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Main");

            newStage.setScene(scene);
            newStage.show();*/

            FXMLLoader loader = new FXMLLoader();
            URI uri = Paths.get("src/main/java/com/challenge/conversor/view/ViewMain.fxml").toAbsolutePath().toUri();
            loader.setLocation(uri.toURL());
            Parent root = loader.load();
            ViewMainController mainControllerInstancia = loader.getController();
            //String state = "mi estado";
            mainControllerInstancia.recibeParametros(usuarioViajero);

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Main");
            newStage.setScene(scene);
            newStage.show();

            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, verifique su usuario o contraseña");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }
    }


}
