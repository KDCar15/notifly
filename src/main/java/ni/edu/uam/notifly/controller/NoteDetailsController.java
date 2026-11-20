package ni.edu.uam.notifly.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ni.edu.uam.notifly.entity.Notas;
import ni.edu.uam.notifly.dao.Dao;


public class NoteDetailsController implements Initializable{

    private Notas notaActual;

    private Stage ventanaContactos;
    private Stage ventanaPlantillas;
    private final Dao dao = new Dao();

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
    private ComboBox<String> cb_tipo;
    @FXML
    private ComboBox<String> cb_area;
    @FXML
    private ComboBox<String> cb_categoria;
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
    
    @FXML
    public void marcar_completado(ActionEvent event) {
    }

    @FXML
    public void guardar(ActionEvent event) {

        if (notaActual == null) {
            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "No hay una nota cargada."
            );
            return;
        }

        try {
            notaActual.setNombresapellidos(
                tf_nombresapellidos.getText()
            );

            notaActual.setCorreo(
                tf_correo.getText()
            );

            notaActual.setCelular(
                tf_celular.getText()
            );

            notaActual.setDescripcion(
                ta_descripcion.getText()
            );

            Notas resultado = dao.update(notaActual);

            if (resultado != null) {
                mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Nota guardada",
                    "Los cambios se guardaron correctamente."
                );

                cerrarVentana();
            } else {
                mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo guardar la nota."
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "Ocurrió un problema al guardar la nota."
            );
        }
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tf_nombresapellidos
                .getScene()
                .getWindow();

        stage.close();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    
    public void setNota(Notas nota) {
        this.notaActual = nota;

        if (nota == null) {
            return;
        }

        tf_nombresapellidos.setText(
            nota.getNombresapellidos()
        );

        tf_correo.setText(
            nota.getCorreo()
        );

        tf_celular.setText(
            nota.getCelular()
        );

        tf_fechahora.setText(
            nota.getFechahora() == null
                ? ""
                : nota.getFechahora().toString()
        );

        ta_descripcion.setText(
            nota.getDescripcion()
        );

        // Categoría
        if (nota.getIdClasificacion() != null) {
            cb_categoria.setValue(
                nota.getIdClasificacion().getNombreClasificacion()
            );
        }

        // Área involucrada
        if (nota.getIdArea() != null) {
            cb_area.setValue(
                nota.getIdArea().getNombreArea()
            );
        }

        // Tipo
        if (nota.getIdTiponota() != null) {
            cb_tipo.setValue(
                nota.getIdTiponota().getNombreTipo()
            );
        }
    }
    
    private void abrirVentanaHija(
            String rutaFXML,
            String titulo,
            boolean esContactos) {

        Stage ventanaActual = esContactos
                ? ventanaContactos
                : ventanaPlantillas;

        // Si ya existe, reutilizarla.
        if (ventanaActual != null && ventanaActual.isShowing()) {
            ventanaActual.toFront();
            ventanaActual.requestFocus();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(rutaFXML)
            );

            Parent root = loader.load();

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle(titulo);
            nuevaVentana.setScene(new Scene(root));

            // La ventana hija pertenece a NoteDetails,
            // pero no bloquea su interacción.
            Stage ventanaPadre = (Stage) tf_nombresapellidos
                    .getScene()
                    .getWindow();

            nuevaVentana.initOwner(ventanaPadre);

            if (esContactos) {
                ventanaContactos = nuevaVentana;
            } else {
                ventanaPlantillas = nuevaVentana;
            }

            nuevaVentana.setOnHidden(event -> {
                if (esContactos) {
                    ventanaContactos = null;
                } else {
                    ventanaPlantillas = null;
                }
            });

            nuevaVentana.show();

        } catch (Exception e) {
            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(
                "No se pudo abrir la ventana solicitada."
            );
            alerta.showAndWait();
        }
    }

    @FXML
    public void ver_contactos(ActionEvent event) {
        abrirVentanaHija(
            "/ni/edu/uam/notifly/contactlist.fxml",
            "Contactos",
            true
        );
    }

    @FXML
    public void ver_plantillas(ActionEvent event) {
        abrirVentanaHija(
            "/ni/edu/uam/notifly/templatelist.fxml",
            "Plantillas",
            false
        );
    }
    
    public void cerrarVentanasHijas() {

        if (ventanaContactos != null) {
            ventanaContactos.close();
            ventanaContactos = null;
        }

        if (ventanaPlantillas != null) {
            ventanaPlantillas.close();
            ventanaPlantillas = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Conserva aquí la inicialización que ya tengas.

        tf_nombresapellidos.sceneProperty().addListener(
            (observable, escenaAnterior, escenaNueva) -> {

                if (escenaNueva != null) {
                    Stage stage = (Stage) escenaNueva.getWindow();

                    if (stage != null) {
                        stage.setOnHidden(event ->
                            cerrarVentanasHijas()
                        );
                    }
                }
            }
        );
    }
}