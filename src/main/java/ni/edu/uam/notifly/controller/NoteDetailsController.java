package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteDetailsController {

    @FXML
    private TextField tf_nombresapellidos;
    @FXML
    private ComboBox<String> cb_facultad;
    @FXML
    private TextField tf_correo;
    @FXML
    private TextField tf_celular;
    @FXML
    private TextField tf_fechahora;
    @FXML
    private ComboBox<String> cb_area;
    @FXML
    private TextArea ta_descripcion;
    @FXML
    private TextArea ta_observaciones;

    @FXML
    private CheckBox checkb1;
    @FXML
    private CheckBox checkb2;
    @FXML
    private Label fecha_mandar;
    @FXML
    private Label fecha_recibir;

    @FXML
    private Button ver_contactos;
    @FXML
    private Button ver_plantillas;

    @FXML
    public void marcar_completado(ActionEvent event) {
    }

    @FXML
    public void guardar(ActionEvent event) {
    }

    @FXML
    public void cancelar(ActionEvent event) {
    }
}