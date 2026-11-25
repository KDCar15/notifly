package ni.edu.uam.notifly.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ni.edu.uam.notifly.App;

/** Main navigation for the administrator application. */
public class MainScreenController {

    @FXML public void mostrar_notas() { abrir("notelist"); }
    @FXML public void mostrar_plantillas() { abrir("templatelist"); }
    @FXML public void mostrar_historial() { abrir("notehistory"); }
    @FXML public void mostrar_contactos() { abrir("contactlist"); }
    @FXML public void mostrar_informes() { abrir("reports"); }

    @FXML
    public void salir() {
        Platform.exit();
    }

    @FXML
    public void acercade() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de Notifly");
        alert.setHeaderText("Notifly 1.0");
        alert.setContentText("Sistema administrativo de quejas y sugerencias.");
        alert.showAndWait();
    }

    private void abrir(String pantalla) {
        try {
            App.setRoot(pantalla);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se pudo abrir la pantalla");
            alert.setHeaderText(null);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
