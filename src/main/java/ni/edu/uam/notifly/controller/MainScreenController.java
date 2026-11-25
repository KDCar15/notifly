package ni.edu.uam.notifly.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ni.edu.uam.notifly.App;

public class MainScreenController {
    @FXML
    public void mostrar_notas(){
        abrir("notelist");
    }
    @FXML
    public void mostrar_plantillas(){
        abrir("templatelist");
    }
    @FXML
    public void mostrar_historial(){
        abrir("notehistory");
    }
    @FXML
    public void mostrar_contactos(){
        abrir("contactlist");
    }
    @FXML
    public void mostrar_informes(){
        try {
            App.setRoot("reports");
        } catch (IOException e) { mostrarError(e); }
    }
    @FXML
    public void salir(){
        System.out.println("Saliendo del programa...");
    }
    @FXML
    public void acercade(){
        System.out.println("Mostrando informacion del programa...");
    }

    private void abrir(String pantalla) {
        try { App.setRoot(pantalla); }
        catch (IOException exception) { mostrarError(exception); }
    }

    private void mostrarError(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No se pudo abrir la pantalla");
        alert.setHeaderText(null);
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }
}
