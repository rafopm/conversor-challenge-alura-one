package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.dao.VistaTipoDeCambioDao;
import main.model.Divisa;
import main.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.model.VistaTipoDeCambio;

public class ViewMainController implements Initializable {
    //ViewLoginController stage1_controller_enStage2;

    @FXML
    private TextField txtMonto;

    @FXML
    private Button btnCrossover;

    @FXML
    private Button btnCalcular;

    @FXML
    private Tab tabConversorDivisas;



    @FXML
    private TableView<VistaTipoDeCambio> tvDe;

    @FXML
    private TableView<VistaTipoDeCambio> tvA;

    @FXML
    private TableColumn cambioColum2;
    @FXML
    private TableColumn banderaColumn1;

    @FXML
    private TableColumn isoColum1;

    @FXML
    private TableColumn isoColum2;
    @FXML
    private TableColumn nombreColum2;
    @FXML
    private TableColumn banderaColumn2;

    @FXML
    private TableColumn nombreColum1;

    @FXML
    private TableColumn cambioColum1;

    @FXML
    private Label lblDe;

    @FXML
    private Tab tabAdmin;

    @FXML
    private Label lblA;

    @FXML
    private Button btnAdminUsuarios;

    @FXML
    private ImageView imgBandera2;



    @FXML
    private ImageView imgBandera1;

    @FXML
    private Tab tabConversorTemperatura;
    @FXML
    private Button btnAdminTCambio;

    @FXML
    private Button btnAdminDivisa;
    @FXML
    private TextField txtMonto2;

    @FXML
    private TextField txtMonto1;
    @FXML
    private Label lblDe1;
    @FXML
    private Label lblA1;



    private VistaTipoDeCambioDao vistaTipoDeCambioDao;

    private ObservableList<VistaTipoDeCambio> cambioOrigenList;
    private ObservableList<VistaTipoDeCambio> cambioDestinoList;

    private VistaTipoDeCambio cambioDeSeleccionado;
    private VistaTipoDeCambio cambioASeleccionado;
    private double indiceDe;
    private double indiceA;

    private double monedaOrigen;
    private double monedaDestino;

    private String banderaDe;
    private String banderaA;

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void initialize(URL url, ResourceBundle rb) {

        txtMonto1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")){
                System.out.println("Tendria que salir esto al seleccionar alguna opcion de campo1");
                indiceA = 0;
            }
            indiceA = cambioDeSeleccionado.getCambio2();
        });

        txtMonto2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")){
                System.out.println("Tendria que salir esto al seleccionar alguna opcion de campo1");
                indiceDe = 0;
            } else{
                indiceDe = cambioDeSeleccionado.getCambio2();
                System.out.println(indiceDe);
            }

        });


        this.vistaTipoDeCambioDao = new VistaTipoDeCambioDao();
        cargarTipoDeCambio();
        //
        tvDe.getSelectionModel().select(0);
        tvA.getSelectionModel().select(0);

        PoblarCamposDe();
        PoblarCamposA();
    }

    @FXML
    void btnAdminUsuariosOnAction(ActionEvent event) throws IOException {
        LanzadorDeVentanas("ViewUsuario.fxml", "Administrar usuarios");
    }

    @FXML
    void btnAdminTCambioOnAction(ActionEvent event) {
        LanzadorDeVentanas("ViewTipoCambio.fxml", "Administrar Tipos de cambio");
    }

    @FXML
    void btnAdminDivisaOnAction(ActionEvent event) {
        LanzadorDeVentanas("ViewDivisa.fxml", "Administrar Divisas");
    }


    @FXML
    public void recibeParametros(Usuario usuarioRecibido) {
        //textPrueba.setText(Integer.toString(id));
        //stage1_controller_enStage2=stage1;
        txtMonto.setText(usuarioRecibido.getNombre());
        System.out.println(usuarioRecibido.getPermisos());
        String permisos = usuarioRecibido.getPermisos();
        if (permisos.equals("Admin")) {
            tabAdmin.setDisable(false);
        } else {
            tabAdmin.setDisable(true);
        }
    }

    public void cargarTipoDeCambio() {
        tvDe.getItems().clear();
        tvDe.getColumns().clear();

        List<VistaTipoDeCambio> tiposDeCambioDB = this.vistaTipoDeCambioDao.listar();

        //Divisas Origen
        List<VistaTipoDeCambio> cambioOrigenFiltro1 = tiposDeCambioDB.stream()
                .filter(distinctByKey(p -> p.getIsoo()))
                .collect(Collectors.toList());

        //System.out.println(cambioOrigenFiltro1);
        ObservableList<VistaTipoDeCambio> datatc1 = FXCollections.observableArrayList(cambioOrigenFiltro1);


        isoColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isoo"));
        nombreColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombreo"));
        cambioColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("cambio1"));
        banderaColumn1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("bandera1"));

        tvDe.setItems(datatc1);
        tvDe.getColumns().addAll(isoColum1, nombreColum1, cambioColum1, banderaColumn1);

        //tvDe.setFixedCellSize(0);

        //Divisas Destino
        List<VistaTipoDeCambio> cambioOrigenFiltro2 = tiposDeCambioDB.stream()
                .filter(distinctByKey(p -> p.getIsod()))
                .collect(Collectors.toList());

        //System.out.println(cambioOrigenFiltro2);
        ObservableList<VistaTipoDeCambio> datatc2 = FXCollections.observableArrayList(cambioOrigenFiltro2);

        isoColum2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isod"));
        nombreColum2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombred"));
        cambioColum2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("cambio2"));
        banderaColumn2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("bandera2"));

        tvA.setItems(datatc2);
        tvA.setFixedCellSize(0);
        //tvA.getColumns().addAll(isoColum2, nombreColum2, cambioColum2, banderaColumn2);

    }


    public void PoblarCamposDe() {
        int index = tvDe.getSelectionModel().getSelectedIndex();
        cambioDeSeleccionado = tvDe.getItems().get(index);
        String bandera = cambioDeSeleccionado.getBandera1();
        MostrarBandera(bandera, imgBandera1);
        lblDe.setText(String.valueOf(cambioDeSeleccionado.getCambio1()));
        lblDe1.setText(cambioDeSeleccionado.getNombreo());
        txtMonto1.setText(String.valueOf(cambioDeSeleccionado.getCambio1()));
        indiceDe = cambioDeSeleccionado.getCambio1();
    }

    public void PoblarCamposA() {
        int index = tvA.getSelectionModel().getSelectedIndex();
        cambioASeleccionado = tvA.getItems().get(index);
        String bandera = cambioASeleccionado.getBandera2();
        MostrarBandera(bandera, imgBandera2);
        lblA.setText(String.valueOf(cambioASeleccionado.getCambio2()));
        lblA1.setText(cambioASeleccionado.getNombred());
        txtMonto2.setText(String.valueOf(cambioASeleccionado.getCambio2()));
        indiceA = cambioASeleccionado.getCambio2();
    }

    private void MostrarBandera(String bandera, ImageView imageView) {
        //String bandera = "pe.png";
        URI uri = Paths.get("src/main/java/main/resources/banderas/" + bandera).toAbsolutePath().toUri();
        try {
            Image img = new Image(uri.toURL().openStream());
            imageView.setImage(img);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void LanzadorDeVentanas(String nombreVentana, String titulo){
        Stage stage = new Stage();
        URI uri = Paths.get("src/main/java/main/view/"+nombreVentana).toAbsolutePath().toUri();
        System.out.println("URI: " + uri.toString());
        Parent root = null;
        try {
            root = FXMLLoader.load(uri.toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onMouseClickedDivisaOrigen(MouseEvent mouseEvent) {
        if (tvDe.getSelectionModel().getSelectedItem() != null) {
            PoblarCamposDe();
        }
    }
    @FXML
    void onMouseClickedDivisaDestino(ActionEvent event) {

    }

    @FXML
    void txtMonto1InputTextChanged(KeyEvent keyEvent) {
        txtMonto1.textProperty().addListener((observable, oldValue, newValue) -> {
            indiceDe = Double.parseDouble(newValue);
            Calcular(indiceDe, indiceA);
            txtMonto2.setText(String.valueOf(monedaDestino));
            // Aquí puedes agregar el código que quieras ejecutar cuando el texto cambie
        });
    }



    @FXML
    void txtMonto2InputTextChanged(ActionEvent event) {

    }

    void Calcular(double indiceDe, double indiceA){
        //indiceDe indiceA
        monedaDestino = indiceDe * indiceA;

    }

}
