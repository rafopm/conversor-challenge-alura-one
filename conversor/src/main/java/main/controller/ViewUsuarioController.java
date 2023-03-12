package main.controller;
import main.dao.UsuarioDao;
import main.model.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewUsuarioController implements Initializable {
    @FXML
    private TextField txtIdusuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<String> cbbPermisos;
    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private TableColumn<Usuario, Integer> idusuarioCol;
    @FXML
    private TableColumn<Usuario, String> usuarioCol;
    @FXML
    private TableColumn<Usuario, String> nombreCol;
    @FXML
    private TableColumn<Usuario, String> permisosCol;
    @FXML
    private TableColumn<Usuario, String> passwordCol;
    private UsuarioDao usuarioDao;
    private ContextMenu cmOpciones;
    private Usuario usuarioSelect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] opciones = {"Admin", "User"};
        ObservableList<String> items = FXCollections.observableArrayList(opciones);
        cbbPermisos.setItems(items);
        cbbPermisos.setValue("Seleccione");

        this.usuarioDao = new UsuarioDao();
        cargarUsuarios();

        cmOpciones = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");

        cmOpciones.getItems().addAll(miEditar, miEliminar);
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = tvUsuario.getSelectionModel().getSelectedIndex();
                usuarioSelect = tvUsuario.getItems().get(index);
                System.out.println("Usuario select" + usuarioSelect);
                txtNombre.setText(usuarioSelect.getNombre());
                txtUsuario.setText(usuarioSelect.getUsuario());
                cbbPermisos.setValue(usuarioSelect.getPermisos());
                txtPassword.setText(usuarioSelect.getPassword());
                //txtIdusuario.setNum(usuarioSelect.getIdusuario());

                btnCancelar.setDisable(false);

            }
        });

        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = tvUsuario.getSelectionModel().getSelectedIndex();
                Usuario usuarioEliminar = tvUsuario.getItems().get(index);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Realmente desea eliminar el usuario: "+usuarioEliminar.getNombre()+" ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK){
                    boolean rsp = usuarioDao.eliminar(usuarioEliminar.getIdusuario());

                    if (rsp) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Éxito");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Se eliminó correctamente el usuario");
                        alert2.initStyle(StageStyle.UTILITY);
                        alert2.showAndWait();

                        cargarUsuarios();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Hubo un error, consulte con el administrador");
                        alert2.initStyle(StageStyle.UTILITY);
                        alert2.showAndWait();
                    }

                }
            }
        });
        tvUsuario.setContextMenu(cmOpciones);
    }

    @FXML
    void btnGuardarOnAction(ActionEvent event) {

        if (usuarioSelect == null) {
            Usuario usuario = new Usuario();
            usuario.setNombre(txtNombre.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setPassword((txtPassword.getText()));
            usuario.setPermisos(cbbPermisos.getSelectionModel().getSelectedItem());

            System.out.println(usuario.toString());

            boolean rsp = this.usuarioDao.registrar(usuario);
            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Se registró correctamente el usuario");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();

                limpiarCampos();

                cargarUsuarios();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error, consulte con el administrador");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        } else {
            usuarioSelect.setNombre(txtNombre.getText());
            usuarioSelect.setUsuario(txtUsuario.getText());
            usuarioSelect.setPassword(txtPassword.getText());
            usuarioSelect.setPermisos(cbbPermisos.getSelectionModel().getSelectedItem());
            //usuarioSelect.setIdusuario(txtIdusuario.());
            boolean rsp = this.usuarioDao.editar(usuarioSelect);
            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Se guardó correctamente el usuario");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();

                limpiarCampos();
                cargarUsuarios();
                usuarioSelect = null;
                btnCancelar.setDisable(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error, consulte con el administrador");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }

        }

    }

    private void limpiarCampos() {
        txtIdusuario.setText("");
        txtNombre.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        cbbPermisos.getSelectionModel().select("Seleccione");
    }

    public void cargarUsuarios() {
        tvUsuario.getItems().clear();
        tvUsuario.getColumns().clear();
        List<Usuario> usuariosDB = this.usuarioDao.listar();
        ObservableList<Usuario> data = FXCollections.observableArrayList(usuariosDB);

        idusuarioCol.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("idusuario"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
        permisosCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("permisos"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("password"));

        System.out.println(data);
        tvUsuario.setItems(data);
        tvUsuario.getColumns().addAll(idusuarioCol, nombreCol, usuarioCol, permisosCol, passwordCol);
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        usuarioSelect = null;
        limpiarCampos();
        btnCancelar.setDisable(true);
    }


}
