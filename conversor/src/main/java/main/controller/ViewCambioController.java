package main.controller;

import main.dao.DivisaDao;
import main.dao.TipoCambioDao;
import main.dao.VistaTipoDeCambioDao;
import main.model.Divisa;
import main.model.TipoCambio;
import main.model.VistaTipoDeCambio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCambioController implements Initializable {
    @FXML
    private TableColumn<VistaTipoDeCambio, String> isoOrigenVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, String> divisaDestinoVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, Double> cambioVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, String> fechaVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, String> idCambioVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, String> divisaOrigenVistaCol;

    @FXML
    private TableColumn<VistaTipoDeCambio, String> isoDestinoVistaCol;
    @FXML
    private Label lblDivisaDestino;
    @FXML
    private TextField txtMonto;

    @FXML
    private TableColumn<Divisa, String> fechaCol;

    @FXML
    private TableColumn<Divisa, Double> cambioCol;

    @FXML
    private TableColumn<Divisa, String> isoDestinoCol;

    @FXML
    private TableView<Divisa> tvDivisaOrigen;

    @FXML
    private TableColumn<Divisa, Integer> idDivisaOrigenCol;

    @FXML
    private TableView<VistaTipoDeCambio> tvTipoCambio;

    @FXML
    private TableColumn<Divisa, String> nombreDestinoCol;

    @FXML
    private TableColumn<Divisa, String> nombreOrigenCol;


    @FXML
    private TableColumn<Divisa, Integer> idDestinoCol;

    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<Divisa> tvDivisaDestino;

    @FXML
    private TableColumn<Divisa, String> isoOrigenCol;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableColumn idCambioCol;

    @FXML
    private TableColumn idDivisaDestinoCol;

    @FXML
    private Label lblDivisaOrigen;
    @FXML
    private TableColumn<Divisa, Integer> idOrigenCol;
    private DivisaDao divisaDao;
    private TipoCambioDao tipoCambioDao;
    private VistaTipoDeCambioDao vistaTipoDeCambioDao;

    private ContextMenu cmOpciones;

    private int idDivisaOrigen;
    private int idDivisaDestino;

    private String isoOrigen = "";
    private String isoDestino = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtMonto.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumeros(event));


        this.divisaDao = new DivisaDao();
        this.tipoCambioDao = new TipoCambioDao();
        this.vistaTipoDeCambioDao = new VistaTipoDeCambioDao();
        cargarDivisas();
        cargarTipoDeCambio();

        cmOpciones = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");

        cmOpciones.getItems().addAll(miEditar, miEliminar);
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /*
                int index = tvDivisa.getSelectionModel().getSelectedIndex();
                divisaSelect = tvDivisa.getItems().get(index);
                System.out.println("Divisa select" + divisaSelect);
                txtNombre.setText(divisaSelect.getNombre());
                txtISO.setText(divisaSelect.getIso());
                txtSimbolo.setText(divisaSelect.getSimbolo());
                txtPais.setText(divisaSelect.getPais());
                txtImagen.setText(divisaSelect.getImagen());

                btnCancelar.setDisable(false);
*/
            }
        });

        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /*
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
                */
            }
        });
        tvTipoCambio.setContextMenu(cmOpciones);
    }

    @FXML
    void btnGuardarOnAction(ActionEvent event) {
/*
        if (divisaSelect == null) {
 */     String idTipoDeCambio = isoOrigen+isoDestino;
        System.out.println(idTipoDeCambio);
        TipoCambio tipoCambio = new TipoCambio();
        tipoCambio.setCambio(Double.parseDouble(txtMonto.getText()));
        tipoCambio.setIddivisaorigen(idDivisaOrigen);
        tipoCambio.setIddivisadestino(idDivisaDestino);
        tipoCambio.setIdtipocambio(idTipoDeCambio);

        System.out.println(tipoCambio.toString());

        boolean rsp = this.tipoCambioDao.registrar(tipoCambio);
        if (rsp) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Se registró correctamente el tipo de cambio");
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

        cargarTipoDeCambio();
            /*
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
*/
    }

    private void limpiarCampos() {
        /*
        txtIddivisa.setText("");
        txtNombre.setText("");
        txtISO.setText("");
        txtSimbolo.setText("");
        txtPais.setText("");
        txtImagen.setText("");

         */
    }

    public void cargarDivisas() {
        tvDivisaOrigen.getItems().clear();
        tvDivisaOrigen.getColumns().clear();
        tvDivisaDestino.getItems().clear();
        tvDivisaDestino.getColumns().clear();

        List<Divisa> divisasDB1 = this.divisaDao.listar();
        ObservableList<Divisa> data1 = FXCollections.observableArrayList(divisasDB1);

        List<Divisa> divisasDB2 = this.divisaDao.listar();
        ObservableList<Divisa> data2 = FXCollections.observableArrayList(divisasDB2);

        System.out.println("marca" + data1.toString());
        //Divisas Origen
        idOrigenCol.setCellValueFactory(new PropertyValueFactory<Divisa, Integer>("iddivisa"));
        isoOrigenCol.setCellValueFactory(new PropertyValueFactory<Divisa, String>("iso"));
        nombreOrigenCol.setCellValueFactory(new PropertyValueFactory<Divisa, String>("nombre"));
        tvDivisaOrigen.setItems(data1);
        tvDivisaOrigen.getColumns().addAll(idOrigenCol, isoOrigenCol, nombreOrigenCol);

        //Divisas Destino
        idDestinoCol.setCellValueFactory(new PropertyValueFactory<Divisa, Integer>("iddivisa"));
        isoDestinoCol.setCellValueFactory(new PropertyValueFactory<Divisa, String>("iso"));
        nombreDestinoCol.setCellValueFactory(new PropertyValueFactory<Divisa, String>("nombre"));
        tvDivisaDestino.setItems(data2);
        tvDivisaDestino.getColumns().addAll(idDestinoCol, isoDestinoCol, nombreDestinoCol);

    }

    public void cargarTipoDeCambio() {
        tvTipoCambio.getItems().clear();
        tvTipoCambio.getColumns().clear();

        List<VistaTipoDeCambio> tiposDeCambioDB = this.vistaTipoDeCambioDao.listar();
        ObservableList<VistaTipoDeCambio> datatc = FXCollections.observableArrayList(tiposDeCambioDB);
        //Divisas Origen
        idCambioVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("idtipocambio"));
        isoOrigenVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isoo"));
        divisaOrigenVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombreo"));
        isoDestinoVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isod"));
        divisaDestinoVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombred"));
        cambioVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, Double>("cambio2"));
        fechaVistaCol.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("fechaactualizacion"));
        tvTipoCambio.setItems(datatc);
        tvTipoCambio.getColumns().addAll(idCambioVistaCol, isoOrigenVistaCol, divisaOrigenVistaCol, isoDestinoVistaCol, divisaDestinoVistaCol, cambioVistaCol, fechaVistaCol);
        //idtipocambio,   isoo, nombreo, isod, nombred, fechaactualizacion, cambio
        //idCambioVistaCol, isoOrigenVistaCol,  divisaOrigenVistaCol, isoDestinoVistaCol, divisaDestinoVistaCol, cambioVistaCol, fechaVistaCol
    }

    //MouseEvent // ActionEvent
    @FXML
    void onMouseClickedDivisaOrigen(MouseEvent mouseEvent) {
        if (tvDivisaOrigen.getSelectionModel().getSelectedItem() != null) {
            Divisa seleccion = tvDivisaOrigen.getSelectionModel().getSelectedItem();

            lblDivisaOrigen.setText(seleccion.getNombre());
            idDivisaOrigen = seleccion.getIddivisa();
            isoOrigen = seleccion.getIso();
        }
    }

    @FXML
    void onMouseClickedDivisaDestino(MouseEvent mouseEvent) {
        if (tvDivisaDestino.getSelectionModel().getSelectedItem() != null) {
            Divisa seleccion = tvDivisaDestino.getSelectionModel().getSelectedItem();
            lblDivisaDestino.setText(seleccion.getNombre());
            idDivisaDestino = seleccion.getIddivisa();
            isoDestino = seleccion.getIso();
            txtMonto.setDisable(false);
        }
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        //tipoCambio = null;
        limpiarCampos();
        btnCancelar.setDisable(true);
    }

    @FXML
    void SoloNumeros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key) && key != '.')
                keyEvent.consume();
            if (key == '.' && txtMonto.getText().contains(".")) {
                keyEvent.consume();
            }

        } catch (Exception ex) {
        }
    }

}
