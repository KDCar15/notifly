package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NoteListController {

    @FXML
    private ComboBox<String> cb_tipo;
    @FXML
    private ComboBox<String> cb_categoria;

    @FXML
    private TableView<Object> tv_resultados;
    @FXML
    private TableColumn<Object, Integer> tc_id;
    @FXML
    private TableColumn<Object, String> tc_tipo;
    @FXML
    private TableColumn<Object, String> tc_fecha;
    @FXML
    private TableColumn<Object, String> tc_categoria;
    @FXML
    private TableColumn<Object, String> tc_resumen;

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
}