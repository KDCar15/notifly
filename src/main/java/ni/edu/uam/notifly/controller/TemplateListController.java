package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TemplateListController {

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
    private TextField tf_busqueda;
    @FXML
    private TableView<Object> tv_plantillas;
    @FXML
    private TableColumn<Object, String> tc_plantilla;

    @FXML
    public void buscar(ActionEvent event) {
    }

    @FXML
    public void borrar_busqueda(ActionEvent event) {
    }

    @FXML
    public void crear(ActionEvent event) {
    }

    @FXML
    public void editar(ActionEvent event) {
    }

    @FXML
    public void borrar(ActionEvent event) {
    }
}
