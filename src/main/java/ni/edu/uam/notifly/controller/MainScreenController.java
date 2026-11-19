package ni.edu.uam.notifly.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreenController {

    // Método auxiliar para cargar el FXML con la ruta exacta de tus recursos
    private void abrirVentana(String archivoFxml, String titulo) {
        try {
            // Se usa la ruta de paquetes ni/edu/uam/notifly/ basándonos en tu carpeta de resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/notifly/" + archivoFxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la ventana: " + archivoFxml);
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrar_notas() {
        abrirVentana("notelist.fxml", "Lista de Notas");
    }

    @FXML
    public void mostrar_plantillas() {
        abrirVentana("templatelist.fxml", "Plantillas de Correo");
    }

    @FXML
    public void mostrar_historial() {
        abrirVentana("notehistory.fxml", "Historial de Notas");
    }

    @FXML
    public void mostrar_contactos() {
        abrirVentana("contactlist.fxml", "Directorio de Contactos");
    }

    @FXML
    public void salir() {
        System.out.println("Saliendo del programa...");
        System.exit(0);
    }

    @FXML
    public void acercade() {
        System.out.println("Notifly Versión 1.0");
    }
}