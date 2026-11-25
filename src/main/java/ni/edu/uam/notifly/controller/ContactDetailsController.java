
package ni.edu.uam.notifly.controller;

import java.util.List;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.entity.Contactos;

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

    private final Dao dao = new Dao();

    private Contactos contactoActual;

    private List<Areas> listaAreas;

    // -------------------------------------------------
    // INICIALIZACIÓN
    // -------------------------------------------------

    @FXML
    public void initialize() {
        cargarAreas();
    }

    // -------------------------------------------------
    // CARGAR ÁREAS
    // -------------------------------------------------

    private void cargarAreas() {

        listaAreas = dao.getAll(
                "Areas.findAll",
                Areas.class
        );

        cb_area.getItems().clear();

        for (Areas area : listaAreas) {

            if (area.getNombreArea() != null) {
                cb_area.getItems().add(
                        area.getNombreArea()
                );
            }
        }
    }

    // -------------------------------------------------
    // CARGAR CONTACTO PARA EDICIÓN
    // -------------------------------------------------

    public void setContacto(Contactos contacto) {

        this.contactoActual = contacto;

        if (contacto == null) {
            return;
        }

        tf_nombresapellidos.setText(
                contacto.getNombresapellidos()
        );

        tf_telefono.setText(
                contacto.getCelular() == null
                        ? ""
                        : contacto.getCelular()
        );

        tf_correo.setText(
                contacto.getCorreo() == null
                        ? ""
                        : contacto.getCorreo()
        );

        ta_extrainfo.setText(
                contacto.getOcupacion() == null
                        ? ""
                        : contacto.getOcupacion()
        );

        if (contacto.getIdArea() != null) {

            cb_area.setValue(
                    contacto.getIdArea().getNombreArea()
            );
        }
    }

    // -------------------------------------------------
    // VALIDAR Y GUARDAR CONTACTO
    // -------------------------------------------------

    private boolean guardarContacto() {

        String nombres = tf_nombresapellidos.getText().trim();
        String ocupacion = ta_extrainfo.getText().trim();

        String telefono = tf_telefono.getText().trim();
        String correo = tf_correo.getText().trim();

        String nombreArea = cb_area.getValue();

        if (nombres.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campo obligatorio",
                    "Introduce los nombres y apellidos del contacto."
            );

            tf_nombresapellidos.requestFocus();
            return false;
        }

        if (ocupacion.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campo obligatorio",
                    "Introduce la ocupación del contacto."
            );

            ta_extrainfo.requestFocus();
            return false;
        }

        if (!correo.isEmpty()
                && !Pattern.matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$",
                correo
        )) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Correo incorrecto",
                    "Introduce una dirección de correo válida."
            );

            tf_correo.requestFocus();
            return false;
        }

        // Crear una entidad nueva solamente si corresponde.
        if (contactoActual == null) {
            contactoActual = new Contactos();
        }

        contactoActual.setNombresapellidos(nombres);
        contactoActual.setOcupacion(ocupacion);

        contactoActual.setCelular(
                telefono.isEmpty() ? null : telefono
        );

        contactoActual.setCorreo(
                correo.isEmpty() ? null : correo
        );

        // Buscar el área seleccionada.
        Areas areaSeleccionada = null;

        if (nombreArea != null) {

            for (Areas area : listaAreas) {

                if (nombreArea.equals(area.getNombreArea())) {
                    areaSeleccionada = area;
                    break;
                }
            }

            if (areaSeleccionada == null) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Área incorrecta",
                        "Selecciona un área válida."
                );

                return false;
            }
        }

        contactoActual.setIdArea(areaSeleccionada);

        // Insertar o actualizar en PostgreSQL.
        Contactos resultado = dao.guardarContacto(contactoActual);

        if (resultado == null) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error al guardar",
                    "No fue posible guardar el contacto. Revisa la consola."
            );

            return false;
        }

        contactoActual = resultado;

        return true;
    }

    // -------------------------------------------------
    // ACEPTAR
    // -------------------------------------------------

    @FXML
    public void aceptar(ActionEvent event) {

        if (guardarContacto()) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Operación exitosa",
                    "El contacto se guardó correctamente."
            );

            cerrarVentana(event);
        }
    }

    // -------------------------------------------------
    // APLICAR
    // -------------------------------------------------

    @FXML
    public void aplicar(ActionEvent event) {

        if (guardarContacto()) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Operación exitosa",
                    "Los cambios se guardaron correctamente."
            );
        }
    }

    // -------------------------------------------------
    // CANCELAR
    // -------------------------------------------------

    @FXML
    public void cancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    // -------------------------------------------------
    // CERRAR VENTANA
    // -------------------------------------------------

    private void cerrarVentana(ActionEvent event) {

        Stage ventana = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        ventana.close();
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
