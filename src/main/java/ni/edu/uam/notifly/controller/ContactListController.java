
package ni.edu.uam.notifly.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.Contactos;

public class ContactListController {

    @FXML
    public void volver() {
        try {
            ni.edu.uam.notifly.App.setRoot("mainscreen");
        } catch (IOException exception) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo volver al menú principal.");
        }
    }

    @FXML
    private TextField tf_busqueda;

    @FXML
    private TableView<Contactos> tv_contactos;

    @FXML
    private TableColumn<Contactos, String> tc_nombreapellido;

    @FXML
    private TableColumn<Contactos, String> tc_area;

    private final Dao dao = new Dao();

    private static final String RUTA_DETALLES =
            "/ni/edu/uam/notifly/contactdetails.fxml";

    // -------------------------------------------------
    // INICIALIZACIÓN
    // -------------------------------------------------

    @FXML
    public void initialize() {

        configurarTabla();

        cargarContactos("");
    }

    // -------------------------------------------------
    // CONFIGURAR TABLA
    // -------------------------------------------------

    private void configurarTabla() {

        tc_nombreapellido.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(
                        cell.getValue().getNombresapellidos()
                )
        );

        tc_area.setCellValueFactory(cell -> {

            Contactos contacto = cell.getValue();

            String area = contacto.getIdArea() == null
                    ? ""
                    : contacto.getIdArea().getNombreArea();

            return new ReadOnlyStringWrapper(area);
        });

        tv_contactos.getSelectionModel().setSelectionMode(
                SelectionMode.SINGLE
        );
    }

    // -------------------------------------------------
    // CARGAR CONTACTOS
    // -------------------------------------------------

    private void cargarContactos(String texto) {

        List<Contactos> contactos = dao.buscarContactos(texto);

        tv_contactos.getItems().setAll(contactos);
    }

    // -------------------------------------------------
    // BUSCAR
    // -------------------------------------------------

    @FXML
    public void buscar(ActionEvent event) {

        cargarContactos(tf_busqueda.getText());
    }

    // -------------------------------------------------
    // BORRAR BÚSQUEDA
    // -------------------------------------------------

    @FXML
    public void borrar_busqueda(ActionEvent event) {

        tf_busqueda.clear();

        cargarContactos("");
    }

    // -------------------------------------------------
    // CREAR CONTACTO
    // -------------------------------------------------

    @FXML
    public void crear(ActionEvent event) {

        abrirFormulario(
                null,
                "Nuevo contacto",
                event
        );
    }

    // -------------------------------------------------
    // EDITAR CONTACTO
    // -------------------------------------------------

    @FXML
    public void editar(ActionEvent event) {

        Contactos contactoSeleccionado = tv_contactos
                .getSelectionModel()
                .getSelectedItem();

        if (contactoSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Contacto no seleccionado",
                    "Selecciona un contacto para editarlo."
            );

            return;
        }

        abrirFormulario(
                contactoSeleccionado,
                "Editar contacto",
                event
        );
    }

    // -------------------------------------------------
    // ABRIR FORMULARIO
    // -------------------------------------------------

    private void abrirFormulario(
            Contactos contacto,
            String titulo,
            ActionEvent event) {

        try {

            URL recurso = getClass().getResource(RUTA_DETALLES);

            if (recurso == null) {

                throw new IOException(
                        "No se encontró el archivo: " + RUTA_DETALLES
                );
            }

            FXMLLoader loader = new FXMLLoader(recurso);

            Parent root = loader.load();

            ContactDetailsController controller =
                    loader.getController();

            // Si es edición, cargar el contacto seleccionado.
            // Si es creación, el formulario queda vacío.
            if (contacto != null) {
                controller.setContacto(contacto);
            }

            Stage ventanaPadre = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            Stage ventana = new Stage();

            ventana.setTitle(titulo);
            ventana.setScene(new Scene(root));

            ventana.initOwner(ventanaPadre);
            ventana.initModality(Modality.WINDOW_MODAL);

            ventana.showAndWait();

            // Actualizar la tabla al cerrar el formulario.
            cargarContactos(tf_busqueda.getText());

        } catch (Exception e) {

            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir el formulario:\n" + e.getMessage()
            );
        }
    }

    // -------------------------------------------------
    // ELIMINAR CONTACTO
    // -------------------------------------------------

    @FXML
    public void eliminar(ActionEvent event) {

        Contactos contactoSeleccionado = tv_contactos
                .getSelectionModel()
                .getSelectedItem();

        if (contactoSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Contacto no seleccionado",
                    "Selecciona un contacto para eliminarlo."
            );

            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar contacto");

        confirmacion.setContentText(
                "¿Estás seguro de que deseas eliminar a "
                        + contactoSeleccionado.getNombresapellidos()
                        + "?"
        );

        ButtonType respuesta = confirmacion.showAndWait()
                .orElse(ButtonType.CANCEL);

        if (respuesta != ButtonType.OK) {
            return;
        }

        boolean eliminado = dao.eliminarContacto(
                contactoSeleccionado
        );

        if (eliminado) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Operación exitosa",
                    "El contacto se eliminó correctamente."
            );

            cargarContactos(tf_busqueda.getText());

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error al eliminar",
                    "No se pudo eliminar el contacto. "
                            + "Comprueba si existen registros relacionados "
                            + "o revisa la consola de IntelliJ."
            );
        }
    }

    // -------------------------------------------------
    // MOSTRAR ALERTAS
    // -------------------------------------------------

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}
