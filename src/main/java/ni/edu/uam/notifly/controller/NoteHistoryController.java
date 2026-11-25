package ni.edu.uam.notifly.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NoteHistoryController implements Initializable {

    @FXML
    public void volver() {
        try {
            ni.edu.uam.notifly.App.setRoot("mainscreen");
        } catch (java.io.IOException exception) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo volver al menú principal.");
            alert.showAndWait();
        }
    }

    @FXML
    private ComboBox<String> cb_tipo;
    @FXML
    private ComboBox<String> cb_estado;
    @FXML
    private ComboBox<String> cb_area;
    @FXML
    private ComboBox<String> cb_categoria;
    @FXML
    private ComboBox<String> cb_facultad;
    @FXML
    private Button bt_fecha;

    @FXML
    private TableView<Object> tv_resultados;
    @FXML
    private TableColumn<Object, String> tc_acceso;
    @FXML
    private TableColumn<Object, Integer> tc_id;
    @FXML
    private TableColumn<Object, String> tc_tipo;
    @FXML
    private TableColumn<Object, String> tc_facultad;
    @FXML
    private TableColumn<Object, String> tc_categoria;
    @FXML
    private TableColumn<Object, String> tc_area;
    @FXML
    private TableColumn<Object, String> tc_estado;

    @FXML
    public void seleccionar_fecha_inicio(ActionEvent event) {
    }

    @FXML
    public void seleccionar_fecha_fin(ActionEvent event) {
    }

    @FXML
    public void buscar(ActionEvent event) {
    }

    @FXML
    public void limpiar(ActionEvent event) {
    }

    @FXML
    public void visualizar(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de la tabla y comboboxes
    }
}
