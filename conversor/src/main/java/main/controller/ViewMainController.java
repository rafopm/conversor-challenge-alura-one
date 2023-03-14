package main.controller;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.model.VistaTipoDeCambio;


public class ViewMainController implements Initializable {
    //ViewLoginController stage1_controller_enStage2;

    PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(Duration.ofSeconds(1).toMillis()));
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
    private VistaTipoDeCambio cambioSeleccionado;
    private double indiceDe;
    private double indiceA;
    private double valorTexto1;
    private double valorTexto2;
    private String Iso1;
    private String Iso2;
    private double monedaOrigen;
    private double monedaDestino;
    private String banderaDe;
    private String banderaA;
    private List<VistaTipoDeCambio> tiposDeCambioDB;

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void initialize(URL url, ResourceBundle rb) {


        System.out.println("Es null? " + tiposDeCambioDB);
        this.vistaTipoDeCambioDao = new VistaTipoDeCambioDao();
        cargarTipoDeCambio();
        //
        //tvDe.getSelectionModel().select(0);
        //tvA.getSelectionModel().select(0);

        //Iso1 = tvDe.getItems().get(index1);
        Iso1 = tvDe.getItems().get(0).getIsoo();
        Iso2 = tvA.getItems().get(0).getIsod();

        //PoblarCamposDe();
        //PoblarCamposA();
        PoblarCampos(Iso1, Iso2);

        //Listener caja de texto 1
        txtMonto1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                System.out.println("Tendria que salir esto al seleccionar alguna opcion de campo1");
                //indiceA = 0;
            }
            //indiceA = cambioDeSeleccionado.getCambio2();
        });

        //Listener caja de texto 2
        txtMonto2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                System.out.println("Tendria que salir esto al seleccionar alguna opcion de campo1");
                //indiceDe = 0;
            }
        });
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


    public void PoblarCampos(String iso1, String iso2) {

        String isoBuscar = iso1 + iso2;

        if (tiposDeCambioDB == null) {
            tiposDeCambioDB = this.vistaTipoDeCambioDao.listar();
        }
        List<VistaTipoDeCambio> cambioOrigenFiltro = tiposDeCambioDB
                .stream().filter(e -> e.getIdtipocambio().equals(isoBuscar))
                .collect(Collectors.toList());

        cambioSeleccionado = cambioOrigenFiltro.get(0);

        String bandera1 = cambioSeleccionado.getBandera1();
        MostrarBandera(bandera1, imgBandera1);
        lblDe.setText(String.valueOf(cambioSeleccionado.getCambio1()));
        lblDe1.setText(cambioSeleccionado.getNombreo());
        txtMonto1.setText(String.valueOf(cambioSeleccionado.getCambio1()));
        indiceDe = cambioSeleccionado.getCambio1();

        String bandera2 = cambioSeleccionado.getBandera2();
        MostrarBandera(bandera2, imgBandera2);
        lblA.setText(String.valueOf(cambioSeleccionado.getCambio2()));
        lblA1.setText(cambioSeleccionado.getNombred());
        txtMonto2.setText(String.valueOf(cambioSeleccionado.getCambio2()));
        indiceA = cambioSeleccionado.getCambio2();

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

    private void LanzadorDeVentanas(String nombreVentana, String titulo) {
        Stage stage = new Stage();
        URI uri = Paths.get("src/main/java/main/view/" + nombreVentana).toAbsolutePath().toUri();
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
            //PoblarCamposDe();
            int index = tvDe.getSelectionModel().getSelectedIndex();
            Iso1 = tvDe.getItems().get(index).getIsoo();
            System.out.println("indice 1" + Iso1);
            PoblarCampos(Iso1, Iso2);
        }

    }

    @FXML
    void onMouseClickedDivisaDestino(MouseEvent mouseEvent) {
        if (tvA.getSelectionModel().getSelectedItem() != null) {
            //PoblarCamposDe();
            int index = tvA.getSelectionModel().getSelectedIndex();
            Iso2 = tvA.getItems().get(index).getIsod();
            System.out.println("indice 2" + Iso2);
            PoblarCampos(Iso1, Iso2);
        }
    }

    @FXML
    void txtMonto1InputTextChanged(KeyEvent keyEvent) {
        txtMonto1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    System.out.println("HOla desde enter");
                    valorTexto1 = Double.parseDouble(newValue);
                    Calcular("CalcularDestino", valorTexto1, indiceA);
                }
            } else {
                txtMonto2.setText(String.valueOf(0));
            }
        });

    }


    @FXML
    void txtMonto2InputTextChanged(KeyEvent keyEvent) {
        txtMonto2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                valorTexto2 = Double.parseDouble(newValue);
                Calcular("CalcularOrigen", valorTexto2, indiceA);

            } else {
                txtMonto1.setText(String.valueOf(0));
            }
        });
    }


    void Calcular(String tipo, double indiceDe, double indiceA) {
        //indiceDe indiceA
        if (indiceDe == 0 || indiceDe == 0) {

        } else {
            if (tipo.equals("CalcularDestino")) {
                monedaDestino = indiceDe * indiceA;
                txtMonto2.setText(String.valueOf(monedaDestino));
            } else {
                monedaOrigen = indiceDe / indiceA;
                txtMonto1.setText(String.valueOf(monedaOrigen));
            }
        }


    }


}
