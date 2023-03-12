package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dao.VistaTipoDeCambioDao;
import main.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
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
    private TableColumn banderaColumn1;

    @FXML
    private TableColumn isoColum1;

    @FXML
    private TableColumn isoColum2;

    @FXML
    private TableView<VistaTipoDeCambio> tvDe;

    @FXML
    private TableColumn nombreColum2;

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
    private TableView<?> tvA;

    @FXML
    private ImageView imgBandera1;

    @FXML
    private Tab tabConversorTemperatura;

    private VistaTipoDeCambioDao vistaTipoDeCambioDao;

    private ObservableList<VistaTipoDeCambio> cambioOrigenList;
    private ObservableList<VistaTipoDeCambio> cambioDestinoList;


    public void initialize(URL url, ResourceBundle rb) {
        this.vistaTipoDeCambioDao = new VistaTipoDeCambioDao();
        cargarTipoDeCambio();
        tvDe.getSelectionModel().select(0);
    }

    @FXML
    void btnAdminUsuariosOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        URI uri = Paths.get("src/main/java/main/view/ViewUsuario.fxml").toAbsolutePath().toUri();
        System.out.println("URI: "+uri.toString());
        Parent root = null;
        try {
            root = FXMLLoader.load(uri.toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setTitle("Administrar usuarios");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void recibeParametros(Usuario usuarioRecibido) {
        //textPrueba.setText(Integer.toString(id));
        //stage1_controller_enStage2=stage1;
        txtMonto.setText(usuarioRecibido.getNombre());
        System.out.println(usuarioRecibido.getPermisos());
        String permisos = usuarioRecibido.getPermisos();
        if(permisos.equals("Admin")){
            tabAdmin.setDisable(false);
        } else {
            tabAdmin.setDisable(true);
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public void cargarTipoDeCambio() {
        tvDe.getItems().clear();
        tvDe.getColumns().clear();

        List<VistaTipoDeCambio> tiposDeCambioDB = this.vistaTipoDeCambioDao.listar();
        List<VistaTipoDeCambio> cambioOrigenFiltro = tiposDeCambioDB.stream()
                .filter( distinctByKey(p -> p.getIsoo()) )
                .collect( Collectors.toList() );

        System.out.println(cambioOrigenFiltro);
        ObservableList<VistaTipoDeCambio> datatc = FXCollections.observableArrayList(cambioOrigenFiltro);
        //Divisas Origen

        
        isoColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("isoo"));
        nombreColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("nombreo"));
        cambioColum1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("cambio1"));
        banderaColumn1.setCellValueFactory(new PropertyValueFactory<VistaTipoDeCambio, String>("bandera1"));

        tvDe.setItems(datatc);
        tvDe.getColumns().addAll(isoColum1, nombreColum1,  cambioColum1, banderaColumn1);


        //idtipocambio,   isoo, nombreo, isod, nombred, fechaactualizacion, cambio
        //idCambioVistaCol, isoOrigenVistaCol,  divisaOrigenVistaCol, isoDestinoVistaCol, divisaDestinoVistaCol, cambioVistaCol, fechaVistaCol
    }
    /*
    private static List<Persona> getH(List<Persona> list){
        return list.Stream.filter(x -> x.isSexo()).collect(Collectors.toList));
    }

     */

}
