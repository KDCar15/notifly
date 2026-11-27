package ni.edu.uam.notifly.controller;

import java.net.URL;
import java.util.List;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ni.edu.uam.notifly.entity.Notas;
import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.entity.TiposNota;
import ni.edu.uam.notifly.entity.Clasificaciones;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class NoteDetailsController implements Initializable{

    private Notas notaActual;
    private boolean modoCreacion;

    private Stage ventanaContactos;
    private Stage ventanaPlantillas;
    private final Dao dao = new Dao();

    @FXML
    private TextField tf_nombresapellidos;
    @FXML
    private ComboBox<String> cb_facultad;
    @FXML
    private Label lbl_facultad;
    @FXML
    private TextField tf_correo;
    @FXML
    private TextField tf_celular;
    @FXML
    private TextField tf_fechahora;
    @FXML
    private ComboBox<String> cb_tipo;
    @FXML
    private ComboBox<Areas> cb_area;
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
    @FXML
    private Button btn_marcar_completado;

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

    private void guardar_nota(){
        if (modoCreacion) {
            String nombres = texto(tf_nombresapellidos.getText());
            String correo = texto(tf_correo.getText());
            String celular = texto(tf_celular.getText());
            String descripcion = texto(ta_descripcion.getText());
            String tipo = cb_tipo.getValue();
            String categoria = cb_categoria.getValue();
            if (nombres.isEmpty() || correo.isEmpty() || celular.isEmpty() || descripcion.isEmpty()
                    || tipo == null || categoria == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos",
                        "Completa nombre, correo, celular, tipo, categoría y descripción.");
                return;
            }
            Integer areaId = cb_area.getValue() == null ? null : cb_area.getValue().getIdArea();
            Notas creada = dao.crearNota(nombres, correo, celular, descripcion, tipo, categoria, areaId);
            if (creada == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo registrar",
                        "Revisa que la base de datos tenga tipos, categorías y un estado Pendiente configurados.");
                return;
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Caso registrado", "El caso " + creada.getIdNota() + " se registró correctamente.");
            cerrarVentana();
            return;
        }
        if (notaActual == null) {
            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "No hay una nota cargada."
            );
            return;
        }

        try {
            // Área seleccionada
            notaActual.setIdArea(
                    cb_area.getValue()
            );

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

            // Observaciones adicionales
            notaActual.setNotasAdicionales(
                ta_observaciones.getText()
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

    @FXML
    public void guardar(ActionEvent event) {
        guardar_nota();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tf_nombresapellidos
                .getScene()
                .getWindow();

        stage.close();
    }

    @FXML
    public void marcar_completado(ActionEvent event) {
        if (modoCreacion) return;
        if (notaActual == null) {
            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "No hay una nota cargada."
            );
            return;
        }
        notaActual.setCompletado(true);
        this.guardar_nota();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cargarAreas() {
        try {
            List<Areas> areas = dao.getAll(
                "Areas.findAll",
                Areas.class
            );

            cb_area.getItems().setAll(areas);

            // Mostrar el nombre del área en el ComboBox
            cb_area.setCellFactory(param -> new ListCell<Areas>() {
                @Override
                protected void updateItem(Areas area, boolean empty) {
                    super.updateItem(area, empty);

                    if (empty || area == null) {
                        setText(null);
                    } else {
                        setText(area.getNombreArea());
                    }
                }
            });

            cb_area.setButtonCell(new ListCell<Areas>() {
                @Override
                protected void updateItem(Areas area, boolean empty) {
                    super.updateItem(area, empty);

                    if (empty || area == null) {
                        setText(null);
                    } else {
                        setText(area.getNombreArea());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "No se pudieron cargar las áreas."
            );
        }
    }

    public void setNota(Notas nota) {
        this.notaActual = nota;
        this.modoCreacion = false;
        lbl_facultad.setVisible(true); lbl_facultad.setManaged(true);
        cb_facultad.setVisible(true); cb_facultad.setManaged(true);

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
        // Seleccionar el área correspondiente
        if (nota.getIdArea() != null) {
            cb_area.getItems().stream()
                .filter(area -> area.getIdArea().equals(
                    nota.getIdArea().getIdArea()
                ))
                .findFirst()
                .ifPresent(area -> cb_area.setValue(area));
        } else {
            cb_area.setValue(null);
        }

        // Cargar observaciones adicionales
        ta_observaciones.setText(
            nota.getNotasAdicionales() != null
                ? nota.getNotasAdicionales()
                : ""
        );

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
            ni.edu.uam.notifly.App.ajustarVentanaSecundaria(nuevaVentana,
                    esContactos ? 960 : 1100, esContactos ? 760 : 800);

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
        cargarCatalogos();

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

        cargarAreas();
    }

    private String texto(String valor) { return valor == null ? "" : valor.trim(); }

    private void cargarCatalogos() {
        cb_tipo.getItems().setAll(dao.getAll("TiposNota.findAll", TiposNota.class).stream()
                .map(TiposNota::getNombreTipo).toList());
        cb_categoria.getItems().setAll(dao.getAll("Clasificaciones.findAll", Clasificaciones.class).stream()
                .map(Clasificaciones::getNombreClasificacion).toList());
    }

    /** Prepares the existing case form to register a new case manually. */
    public void prepararNuevaNota() {
        modoCreacion = true;
        notaActual = null;
        tf_nombresapellidos.clear(); tf_correo.clear(); tf_celular.clear(); ta_descripcion.clear(); ta_observaciones.clear();
        tf_fechahora.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault()).format(java.time.Instant.now()));
        tf_nombresapellidos.setEditable(true); tf_correo.setEditable(true); tf_celular.setEditable(true); ta_descripcion.setEditable(true);
        tf_nombresapellidos.setFocusTraversable(true); tf_correo.setFocusTraversable(true); tf_celular.setFocusTraversable(true); ta_descripcion.setFocusTraversable(true);
        cb_tipo.setDisable(false); cb_categoria.setDisable(false); cb_area.setDisable(false);
        cb_tipo.setPromptText("Selecciona el tipo"); cb_categoria.setPromptText("Selecciona la categoría"); cb_area.setPromptText("Área o facultad (opcional)");
        cb_tipo.setValue(null); cb_categoria.setValue(null); cb_area.setValue(null);
        lbl_facultad.setVisible(false); lbl_facultad.setManaged(false);
        cb_facultad.setVisible(false); cb_facultad.setManaged(false);
        checkb1.setDisable(true); checkb2.setDisable(true);
        if (btn_marcar_completado != null) btn_marcar_completado.setVisible(false);
    }
}
