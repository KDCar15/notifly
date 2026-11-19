package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ContactListController {

    @FXML
    private TextField tf_busqueda;
    @FXML
    private TableView<Object> tv_plantillas;
    @FXML
    private TableColumn<Object, String> tc_nombreapellido;
    @FXML
    private TableColumn<Object, String> tc_area;

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
    public void eliminar(ActionEvent event) {
    }
}