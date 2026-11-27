package ni.edu.uam.notifly.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.entity.Notas;
import ni.edu.uam.notifly.entity.TiposNota;
import ni.edu.uam.notifly.entity.EstadosNota;
import ni.edu.uam.notifly.entity.Clasificaciones;

public class NoteHistoryController implements Initializable {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final Dao dao = new Dao();
    private final List<Areas> areas = new ArrayList<>();
    @FXML private ComboBox<String> cb_tipo, cb_estado, cb_area, cb_categoria, cb_facultad;
    @FXML private DatePicker dp_fecha_inicio, dp_fecha_fin;
    @FXML private TableView<HistoryRow> tv_resultados;
    @FXML private TableColumn<HistoryRow, String> tc_acceso, tc_tipo, tc_facultad, tc_categoria, tc_area, tc_estado;
    @FXML private TableColumn<HistoryRow, Integer> tc_id;

    @Override public void initialize(java.net.URL url, java.util.ResourceBundle bundle) {
        tc_acceso.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().acceso));
        tc_id.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().id));
        tc_tipo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().tipo));
        tc_facultad.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().facultad));
        tc_categoria.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().categoria));
        tc_area.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().area));
        tc_estado.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().estado));
        tv_resultados.setPlaceholder(new javafx.scene.control.Label("No hay casos en el historial para estos filtros."));
        cargarFiltros(); buscar();
    }

    private void cargarFiltros() {
        llenar(cb_tipo, "Todos los tipos", dao.getAll("TiposNota.findAll", TiposNota.class).stream().map(TiposNota::getNombreTipo).toList());
        llenar(cb_estado, "Todos los estados", dao.getAll("EstadosNota.findAll", EstadosNota.class).stream().map(EstadosNota::getNombreEstado).toList());
        llenar(cb_categoria, "Todas las categorías", dao.getAll("Clasificaciones.findAll", Clasificaciones.class).stream().map(Clasificaciones::getNombreClasificacion).toList());
        areas.clear(); areas.addAll(dao.getAll("Areas.findAll", Areas.class));
        llenar(cb_facultad, "Todas las facultades", areas.stream().filter(a -> Boolean.TRUE.equals(a.getEsFacultad())).map(Areas::getNombreArea).toList());
        llenar(cb_area, "Todas las áreas", areas.stream().filter(a -> !Boolean.TRUE.equals(a.getEsFacultad())).map(Areas::getNombreArea).toList());
    }
    private void llenar(ComboBox<String> combo, String todos, List<String> valores) {
        combo.getItems().clear(); combo.getItems().add(todos); combo.getItems().addAll(valores); combo.getSelectionModel().selectFirst();
    }
    private String elegido(ComboBox<String> combo, String todos) {
        String v = combo.getValue(); return v == null || v.equals(todos) ? null : v;
    }
    @FXML public void buscar() {
        LocalDate desde = dp_fecha_inicio.getValue(), hasta = dp_fecha_fin.getValue();
        if (desde != null && hasta != null && desde.isAfter(hasta)) { alerta(Alert.AlertType.WARNING, "Rango de fechas inválido", "La fecha inicial no puede ser posterior a la final."); return; }
        Areas facultad = areas.stream().filter(a -> Boolean.TRUE.equals(a.getEsFacultad()) && a.getNombreArea().equals(elegido(cb_facultad, "Todas las facultades"))).findFirst().orElse(null);
        Areas area = areas.stream().filter(a -> !Boolean.TRUE.equals(a.getEsFacultad()) && a.getNombreArea().equals(elegido(cb_area, "Todas las áreas"))).findFirst().orElse(null);
        List<Object[]> rows = dao.buscarHistorial(elegido(cb_tipo, "Todos los tipos"), elegido(cb_estado, "Todos los estados"), elegido(cb_categoria, "Todas las categorías"), facultad == null ? null : facultad.getIdArea(), area == null ? null : area.getIdArea(), desde, hasta);
        List<HistoryRow> resultado = new ArrayList<>();
        for (Object[] row : rows) {
            Date fecha = (Date) row[0];
            resultado.add(new HistoryRow(fecha == null ? "" : fecha.toInstant().atZone(ZoneId.systemDefault()).format(FORMAT), ((Number) row[1]).intValue(), str(row[2]), str(row[3]), str(row[4]), str(row[5]), str(row[6])));
        }
        tv_resultados.setItems(FXCollections.observableArrayList(resultado));
    }
    private String str(Object value) { return value == null ? "" : value.toString(); }
    @FXML public void limpiar() {
        cb_tipo.getSelectionModel().selectFirst(); cb_estado.getSelectionModel().selectFirst(); cb_area.getSelectionModel().selectFirst(); cb_facultad.getSelectionModel().selectFirst(); cb_categoria.getSelectionModel().selectFirst(); dp_fecha_inicio.setValue(null); dp_fecha_fin.setValue(null); buscar();
    }
    @FXML public void visualizar() {
        HistoryRow row = tv_resultados.getSelectionModel().getSelectedItem();
        if (row == null) { alerta(Alert.AlertType.INFORMATION, "Selecciona un caso", "Selecciona una fila del historial para abrir el caso."); return; }
        Notas nota = dao.buscarNotaCompleta(row.id);
        if (nota == null) { alerta(Alert.AlertType.ERROR, "No disponible", "No se pudo cargar el caso seleccionado."); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/notifly/notedetails.fxml")); Parent root = loader.load();
            loader.<NoteDetailsController>getController().setNota(nota);
            Stage stage = new Stage(); stage.setTitle("Detalles del caso " + row.id); stage.setScene(new Scene(root));
            ni.edu.uam.notifly.App.ajustarVentanaSecundaria(stage, 1180, 820); stage.show();
        } catch (IOException e) { e.printStackTrace(); alerta(Alert.AlertType.ERROR, "Error", "No fue posible abrir los detalles del caso."); }
    }
    @FXML public void volver() { try { ni.edu.uam.notifly.App.setRoot("mainscreen"); } catch (IOException e) { alerta(Alert.AlertType.ERROR, "Error", "No se pudo volver al menú principal."); } }
    private void alerta(Alert.AlertType tipo, String titulo, String texto) { Alert a = new Alert(tipo); a.setTitle(titulo); a.setHeaderText(null); a.setContentText(texto); a.showAndWait(); }

    public static final class HistoryRow {
        final String acceso; final Integer id; final String tipo, facultad, categoria, area, estado;
        HistoryRow(String acceso, Integer id, String tipo, String facultad, String categoria, String area, String estado) { this.acceso=acceso; this.id=id; this.tipo=tipo; this.facultad=facultad; this.categoria=categoria; this.area=area; this.estado=estado; }
    }
}
