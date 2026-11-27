
package ni.edu.uam.notifly.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.Notas;
import ni.edu.uam.notifly.entity.TiposNota;
import ni.edu.uam.notifly.entity.Clasificaciones;

public class NoteListController {

    @FXML
    public void volver() {
        try {
            ni.edu.uam.notifly.App.setRoot("mainscreen");
        } catch (java.io.IOException exception) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo volver al menú principal.");
        }
    }

    @FXML
    private ComboBox<String> cb_tipo;

    @FXML
    private ComboBox<String> cb_categoria;

    @FXML
    private DatePicker dp_fecha_inicio;

    @FXML
    private DatePicker dp_fecha_fin;

    @FXML
    private TableView<Notas> tv_resultados;

    @FXML
    private TableColumn<Notas, Integer> tc_id;

    @FXML
    private TableColumn<Notas, String> tc_tipo;

    @FXML
    private TableColumn<Notas, String> tc_fecha;

    @FXML
    private TableColumn<Notas, String> tc_categoria;

    @FXML
    private TableColumn<Notas, String> tc_resumen;

    private final Dao dao = new Dao();

    private static final String TODOS_TIPOS = "Todos los tipos";
    private static final String TODAS_CATEGORIAS = "Todas las categorías";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        configurarTabla();
        cargarFiltros();
        cargarNotas();
    }

    // ---------------------------------------------------------
    // CONFIGURACIÓN DE LA TABLA
    // ---------------------------------------------------------

    private void configurarTabla() {

        tc_id.setCellValueFactory(cell ->
            new ReadOnlyObjectWrapper<>(
                cell.getValue().getIdNota()
            )
        );

        tc_tipo.setCellValueFactory(cell -> {
            Notas nota = cell.getValue();

            String tipo = nota.getIdTiponota() == null
                    ? ""
                    : nota.getIdTiponota().getNombreTipo();

            return new ReadOnlyStringWrapper(tipo);
        });

        tc_categoria.setCellValueFactory(cell -> {
            Notas nota = cell.getValue();

            String categoria = nota.getIdClasificacion() == null
                    ? ""
                    : nota.getIdClasificacion()
                        .getNombreClasificacion();

            return new ReadOnlyStringWrapper(categoria);
        });

        tc_fecha.setCellValueFactory(cell -> {
            Date fecha = cell.getValue().getFechahora();

            String valor = fecha == null
                    ? ""
                    : fecha.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .format(FORMATO_FECHA);

            return new ReadOnlyStringWrapper(valor);
        });

        tc_resumen.setCellValueFactory(cell -> {
            String descripcion = cell.getValue().getDescripcion();

            if (descripcion == null) {
                descripcion = "";
            }

            if (descripcion.length() > 30) {
                descripcion = descripcion.substring(0, 27) + "...";
            }

            return new ReadOnlyStringWrapper(descripcion);
        });

        // Permite seleccionar una fila completa.
        tv_resultados.getSelectionModel()
                .setSelectionMode(
                    javafx.scene.control.SelectionMode.SINGLE
                );
    }

    // ---------------------------------------------------------
    // CARGA DE FILTROS
    // ---------------------------------------------------------

    private void cargarFiltros() {

        cb_tipo.getItems().clear();
        cb_categoria.getItems().clear();

        cb_tipo.getItems().add(TODOS_TIPOS);

        List<TiposNota> tipos = dao.getAll(
            "TiposNota.findAll",
            TiposNota.class
        );

        for (TiposNota tipo : tipos) {
            cb_tipo.getItems().add(tipo.getNombreTipo());
        }

        cb_categoria.getItems().add(TODAS_CATEGORIAS);

        List<Clasificaciones> categorias = dao.getAll(
            "Clasificaciones.findAll",
            Clasificaciones.class
        );

        for (Clasificaciones categoria : categorias) {
            cb_categoria.getItems().add(
                categoria.getNombreClasificacion()
            );
        }

        cb_tipo.getSelectionModel().selectFirst();
        cb_categoria.getSelectionModel().selectFirst();
    }

    // ---------------------------------------------------------
    // CARGA DE NOTAS
    // ---------------------------------------------------------

    private void cargarNotas() {

        List<Notas> notas = dao.getNotasFiltradas(
            TODOS_TIPOS,
            TODAS_CATEGORIAS,
            null,
            null
        );

        tv_resultados.getItems().setAll(notas);
    }

    // ---------------------------------------------------------
    // APLICAR FILTROS
    // ---------------------------------------------------------

    @FXML
    public void buscar(ActionEvent event) {

        String tipo = cb_tipo.getValue();
        String categoria = cb_categoria.getValue();

        LocalDate fechaDesde = dp_fecha_inicio.getValue();
        LocalDate fechaHasta = dp_fecha_fin.getValue();

        if (fechaDesde != null
                && fechaHasta != null
                && fechaDesde.isAfter(fechaHasta)) {

            mostrarAlerta(
                Alert.AlertType.WARNING,
                "Fechas incorrectas",
                "La fecha inicial no puede ser posterior a la fecha final."
            );

            return;
        }

        List<Notas> notas = dao.getNotasFiltradas(
            tipo,
            categoria,
            fechaDesde,
            fechaHasta
        );

        tv_resultados.getItems().setAll(notas);
    }

    // ---------------------------------------------------------
    // LIMPIAR FILTROS
    // ---------------------------------------------------------

    @FXML
    public void limpiar(ActionEvent event) {

        cb_tipo.getSelectionModel().selectFirst();
        cb_categoria.getSelectionModel().selectFirst();

        dp_fecha_inicio.setValue(null);
        dp_fecha_fin.setValue(null);

        cargarNotas();
    }

    // ---------------------------------------------------------
    // VER DETALLES DE UNA NOTA
    // ---------------------------------------------------------

    @FXML
    public void ver_nota(ActionEvent event) {

        Notas notaSeleccionada = tv_resultados
                .getSelectionModel()
                .getSelectedItem();

        if (notaSeleccionada == null) {

            mostrarAlerta(
                Alert.AlertType.WARNING,
                "Nota no seleccionada",
                "Selecciona una nota de la tabla para consultar sus detalles."
            );

            return;
        }

        try {
            dao.registrarAccesoNota(notaSeleccionada.getIdNota());

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/ni/edu/uam/notifly/notedetails.fxml"
                )
            );

            Parent root = loader.load();

            NoteDetailsController controller =
                    loader.getController();

            controller.setNota(notaSeleccionada);

            Stage stage = new Stage();
            stage.setTitle("Detalles de la nota");
            stage.setScene(new Scene(root));
            ni.edu.uam.notifly.App.ajustarVentanaSecundaria(stage, 1180, 820);

            // Al cerrar NoteDetails también se cierran
            // las ventanas auxiliares que haya abierto.
            stage.setOnHidden(evento ->
                controller.cerrarVentanasHijas()
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error",
                "No fue posible abrir los detalles de la nota."
            );
        }
    }

    @FXML
    public void crear_nota(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/notifly/notedetails.fxml"));
            Parent root = loader.load();
            NoteDetailsController controller = loader.getController();
            controller.prepararNuevaNota();
            Stage stage = new Stage();
            stage.setTitle("Registrar nuevo caso");
            stage.setScene(new Scene(root));
            ni.edu.uam.notifly.App.ajustarVentanaSecundaria(stage, 1180, 820);
            stage.setOnHidden(e -> {
                controller.cerrarVentanasHijas();
                cargarNotas();
            });
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el formulario para registrar el caso.");
        }
    }

    // ---------------------------------------------------------
    // ALERTAS
    // ---------------------------------------------------------

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
