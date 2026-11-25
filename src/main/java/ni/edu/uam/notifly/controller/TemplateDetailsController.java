package ni.edu.uam.notifly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.model.PlantillaCorreo;

public class TemplateDetailsController {

    @FXML
    private TextField tf_templatename;
    @FXML
    private TextArea ta_templatecontents;
    private final Dao dao = new Dao();
    private PlantillaCorreo plantilla;

    public void setPlantilla(PlantillaCorreo plantilla) {
        this.plantilla = plantilla;
        if (plantilla != null) {
            tf_templatename.setText(plantilla.getNombre());
            ta_templatecontents.setText(plantilla.getContenido());
        }
    }

    private boolean guardar() {
        String nombre = tf_templatename.getText() == null ? "" : tf_templatename.getText().trim();
        String contenido = ta_templatecontents.getText() == null ? "" : ta_templatecontents.getText().trim();
        if (nombre.isEmpty() || contenido.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Completa el nombre y el contenido de la plantilla.");
            a.setHeaderText("Datos incompletos"); a.showAndWait(); return false;
        }
        if (nombre.length() > 90) {
            Alert a = new Alert(Alert.AlertType.WARNING, "El nombre no debe superar 90 caracteres.");
            a.setHeaderText("Nombre demasiado largo"); a.showAndWait(); return false;
        }
        if (plantilla == null) plantilla = new PlantillaCorreo(null, nombre, contenido);
        else { plantilla.setNombre(nombre); plantilla.setContenido(contenido); }
        if (dao.guardarPlantilla(plantilla) == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "No se pudo guardar. Verifica la conexión y la tabla respuestas.plantillas.");
            a.setHeaderText("Error al guardar"); a.showAndWait(); return false;
        }
        return true;
    }
    private void cerrar() { ((Stage) tf_templatename.getScene().getWindow()).close(); }

    @FXML
    public void aceptar(ActionEvent event) {
        if (guardar()) cerrar();
    }

    @FXML
    public void aplicar(ActionEvent event) {
        guardar();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        cerrar();
    }
}
