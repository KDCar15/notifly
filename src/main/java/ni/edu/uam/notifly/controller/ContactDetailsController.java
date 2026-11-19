package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactDetailsController {

    @FXML
    private TextField tf_nombresapellidos;
    @FXML
    private ComboBox<String> cb_area;
    @FXML
    private TextField tf_telefono;
    @FXML
    private TextField tf_correo;
    @FXML
    private TextArea ta_extrainfo;

    @FXML
    public void aceptar(ActionEvent event) {
    }

    @FXML
    public void aplicar(ActionEvent event) {
    }

    @FXML
    public void cancelar(ActionEvent event) {
    }
}