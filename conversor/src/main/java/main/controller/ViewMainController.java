package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.dao.VistaTipoDeCambioDao;
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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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
    private Label lblS1;
    @FXML
    private Label lblS2;

    @FXML
    private TableView<VistaTipoDeCambio> tvDe;
    @FXML
    private TableView<VistaTipoDeCambio> tvA;
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
    private ImageView imgBandera2;
    @FXML
    private ImageView imgBandera1;
    @FXML
    private TextField txtMonto2;
    @FXML
    private TextField txtMonto1;
    @FXML
    private Label lblDe1;
    @FXML
    private Label lblA1;
    @FXML
    private Label lblFecha;

    private VistaTipoDeCambioDao vistaTipoDeCambioDao;
    private VistaTipoDeCambio cambioSeleccionado;
    private double indiceDe;
    private double indiceA;
    private double valorTexto1;
    private double valorTexto2;
    private String Iso1;
    private String Iso2;
    private double monedaOrigen;
    private double monedaDestino;

    private List<VistaTipoDeCambio> tiposDeCambioDB;

    // Crear un formato de número para la localización actual
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);



    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void initialize(URL url, ResourceBundle rb) {

// Configurar el formato para usar separadores de miles y decimales
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);

        txtMonto1.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    valorTexto1 = Double.parseDouble(txtMonto1.getText());
                    Calcular("CalcularDestino", valorTexto1, indiceA);
                }
            }
        });

        txtMonto2.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    valorTexto2 = Double.parseDouble(txtMonto2.getText());
                    Calcular("CalcularOrigen", valorTexto2, indiceA);
                }
            }
        });

        this.vistaTipoDeCambioDao = new VistaTipoDeCambioDao();
        cargarTipoDeCambio();

        Iso1 = tvDe.getItems().get(0).getIsoo();
        Iso2 = tvA.getItems().get(0).getIsod();

        PoblarCampos(Iso1, Iso2);
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
        //txtMonto.setText(usuarioRecibido.getNombre());
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

        ObservableList<VistaTipoDeCambio> datatc1 = FXCollections.observableArrayList(cambioOrigenFiltro1);

        isoColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isoo"));
        nombreColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombreo"));

        tvDe.setItems(datatc1);
        tvDe.getColumns().addAll(isoColum1, nombreColum1);

        //Divisas Destino
        List<VistaTipoDeCambio> cambioOrigenFiltro2 = tiposDeCambioDB.stream()
                .filter(distinctByKey(p -> p.getIsod()))
                .collect(Collectors.toList());

        ObservableList<VistaTipoDeCambio> datatc2 = FXCollections.observableArrayList(cambioOrigenFiltro2);

        isoColum2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isod"));
        nombreColum2.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombred"));

        tvA.setItems(datatc2);
        tvA.setFixedCellSize(0);
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
        lblS1.setText(cambioSeleccionado.getSimbolo1());
        indiceDe = cambioSeleccionado.getCambio1();

        String bandera2 = cambioSeleccionado.getBandera2();
        MostrarBandera(bandera2, imgBandera2);
        lblA.setText(String.valueOf(cambioSeleccionado.getCambio2()));
        lblA1.setText(cambioSeleccionado.getNombred());
        txtMonto2.setText(String.valueOf(cambioSeleccionado.getCambio2()));
        lblS2.setText(cambioSeleccionado.getSimbolo2());
        indiceA = cambioSeleccionado.getCambio2();

        lblFecha.setText("Fecha de actualización: "+cambioSeleccionado.getFechaactualizacion());
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
            int index = tvDe.getSelectionModel().getSelectedIndex();
            Iso1 = tvDe.getItems().get(index).getIsoo();
            PoblarCampos(Iso1, Iso2);
        }
    }

    @FXML
    void onMouseClickedDivisaDestino(MouseEvent mouseEvent) {
        if (tvA.getSelectionModel().getSelectedItem() != null) {
            //PoblarCamposDe();
            int index = tvA.getSelectionModel().getSelectedIndex();
            Iso2 = tvA.getItems().get(index).getIsod();
            PoblarCampos(Iso1, Iso2);
        }
    }

    void Calcular(String tipo, double indiceDe, double indiceA) {
        //indiceDe indiceA
        String formattedValue = "";
        if (indiceDe == 0 || indiceDe == 0) {

        } else {
            if (tipo.equals("CalcularDestino")) {
                monedaDestino = indiceDe * indiceA;
                formattedValue = numberFormat.format(monedaDestino);
                txtMonto2.setText(formattedValue);

                monedaOrigen = Double.parseDouble(txtMonto1.getText());
                formattedValue =  numberFormat.format(monedaOrigen);
                txtMonto1.setText(formattedValue);
            } else {
                monedaOrigen = indiceDe / indiceA;
                formattedValue = numberFormat.format(monedaOrigen);
                txtMonto1.setText(formattedValue);

                monedaDestino = Double.parseDouble(txtMonto2.getText());
                formattedValue =  numberFormat.format(monedaDestino);
                txtMonto2.setText(formattedValue);
            }
        }
    }
}
