package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.dao.DivisaDao;
import main.model.Divisa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewDivisaController implements Initializable {

    @FXML
    private TextField txtIddivisa;

    @FXML
    private TextField txtPais;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSimbolo;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField txtISO;

    @FXML
    private TableView<Divisa> tvDivisa;

    @FXML
    private TableColumn<Divisa, String> nombreCol;

    @FXML
    private TableColumn<Divisa, String> isoCol;

    @FXML
    private TableColumn<Divisa, String> simboloCol;
    @FXML
    private TableColumn<Divisa, String> paisCol;
    @FXML
    private TableColumn<Divisa, Integer> iddivisaCol;
    @FXML
    private TableColumn<Divisa, String> imagenCol;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtImagen;

    private DivisaDao divisaDao;

    private ContextMenu cmOpciones;
    private Divisa divisaSelect;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.divisaDao = new DivisaDao();
        cargarDivisas();

        cmOpciones = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");

        cmOpciones.getItems().addAll(miEditar, miEliminar);
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = tvDivisa.getSelectionModel().getSelectedIndex();
                divisaSelect = tvDivisa.getItems().get(index);
                System.out.println("Divisa select" + divisaSelect);
                txtNombre.setText(divisaSelect.getNombre());
                txtISO.setText(divisaSelect.getIso());
                txtSimbolo.setText(divisaSelect.getSimbolo());
                txtPais.setText(divisaSelect.getPais());
                txtImagen.setText(divisaSelect.getImagen());

                //banderaImg.getClass().getResourceAsStream(String.valueOf("@../resources/banderas/"+txtImagen));







                btnCancelar.setDisable(false);
            }
        });

        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = tvDivisa.getSelectionModel().getSelectedIndex();
                Divisa divisaEliminar = tvDivisa.getItems().get(index);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Realmente desea eliminar la divisa: "+divisaEliminar.getNombre()+" ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK){
                    boolean rsp = divisaDao.eliminar(divisaEliminar.getIddivisa());

                    if (rsp) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Éxito");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Se eliminó correctamente la divisa");
                        alert2.initStyle(StageStyle.UTILITY);
                        alert2.showAndWait();

                        cargarDivisas();
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
        tvDivisa.setContextMenu(cmOpciones);
    }

    @FXML
    void btnGuardarOnAction(ActionEvent event) {

        if (divisaSelect == null) {
            Divisa divisa = new Divisa();
            divisa.setNombre(txtNombre.getText());
            divisa.setIso(txtISO.getText());
            divisa.setSimbolo((txtSimbolo.getText()));
            divisa.setPais((txtPais.getText()));
            divisa.setImagen((txtImagen.getText()));

            System.out.println(divisa.toString());

            boolean rsp = this.divisaDao.registrar(divisa);
            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Se registró correctamente la divisa");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarDivisas();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error, consulte con el administrador");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        } else {
            divisaSelect.setNombre(txtNombre.getText());
            divisaSelect.setIso(txtISO.getText());
            divisaSelect.setSimbolo(txtSimbolo.getText());
            divisaSelect.setPais(txtPais.getText());
            divisaSelect.setImagen(txtImagen.getText());

            boolean rsp = this.divisaDao.editar(divisaSelect);
            if (rsp) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Se guardó correctamente la divisa");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();

                limpiarCampos();
                cargarDivisas();
                divisaSelect = null;
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
        txtIddivisa.setText("");
        txtNombre.setText("");
        txtISO.setText("");
        txtSimbolo.setText("");
        txtPais.setText("");
        txtImagen.setText("");
    }

    public void cargarDivisas() {
        tvDivisa.getItems().clear();
        tvDivisa.getColumns().clear();
        List<Divisa> divisasDB = this.divisaDao.listar();
        ObservableList<Divisa> data = FXCollections.observableArrayList(divisasDB);

        iddivisaCol.setCellValueFactory(new PropertyValueFactory<>("iddivisa"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        isoCol.setCellValueFactory(new PropertyValueFactory<>("iso"));
        simboloCol.setCellValueFactory(new PropertyValueFactory<>("simbolo"));
        paisCol.setCellValueFactory(new PropertyValueFactory<>("pais"));
        imagenCol.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        tvDivisa.setItems(data);
        tvDivisa.getColumns().addAll(iddivisaCol, nombreCol, isoCol, simboloCol, paisCol, imagenCol);
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        divisaSelect = null;
        limpiarCampos();
        btnCancelar.setDisable(true);
    }

}
