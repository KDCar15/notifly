
package ni.edu.uam.notifly.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreenController {

    /**
     * Abre una ventana secundaria y oculta la pantalla principal.
     *
     * Cuando la ventana secundaria se oculta o se cierra,
     * vuelve a mostrar la pantalla principal.
     */
    private void abrirVentana(
            String archivoFxml,
            String titulo,
            Stage mainStage) {

        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/ni/edu/uam/notifly/" + archivoFxml
                )
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

            // Recuperar la pantalla principal al cerrar esta ventana.
            stage.setOnHidden(event -> mainStage.show());

            // Mostrar primero la ventana seleccionada.
            stage.show();

            // Ocultar la pantalla principal.
            mainStage.hide();

        } catch (IOException e) {
            System.out.println(
                "Error al cargar la ventana: " + archivoFxml
            );
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el Stage de la pantalla principal
     * a partir del botón que ejecutó la acción.
     */
    private Stage obtenerVentanaPrincipal(ActionEvent event) {

        Node source = (Node) event.getSource();

        return (Stage) source.getScene().getWindow();
    }

    @FXML
    public void mostrar_notas(ActionEvent event) {

        Stage mainStage = obtenerVentanaPrincipal(event);

        abrirVentana(
            "notelist.fxml",
            "Lista de Notas",
            mainStage
        );
    }

    @FXML
    public void mostrar_plantillas(ActionEvent event) {

        Stage mainStage = obtenerVentanaPrincipal(event);

        abrirVentana(
            "templatelist.fxml",
            "Plantillas de Correo",
            mainStage
        );
    }

    @FXML
    public void mostrar_historial(ActionEvent event) {

        Stage mainStage = obtenerVentanaPrincipal(event);

        abrirVentana(
            "notehistory.fxml",
            "Historial de Notas",
            mainStage
        );
    }

    @FXML
    public void mostrar_contactos(ActionEvent event) {

        Stage mainStage = obtenerVentanaPrincipal(event);

        abrirVentana(
            "contactlist.fxml",
            "Directorio de Contactos",
            mainStage
        );
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
