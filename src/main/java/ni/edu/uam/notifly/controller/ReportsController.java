package ni.edu.uam.notifly.controller;

import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import ni.edu.uam.notifly.App;
import ni.edu.uam.notifly.dao.EntityManagerAdmin;
import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.service.ReportService;
import javax.persistence.EntityManager;

/** Administrator screen for creating the reports defined in the analysis document. */
public class ReportsController {

    private static final Locale SPANISH = new Locale("es", "NI");

    @FXML private ComboBox<Integer> cb_anio_mensual;
    @FXML private ComboBox<Month> cb_mes;
    @FXML private ComboBox<Areas> cb_facultad;
    @FXML private ComboBox<Integer> cb_anio_semestral;
    @FXML private ComboBox<Integer> cb_semestre;

    @FXML
    public void initialize() {
        int currentYear = java.time.Year.now().getValue();
        List<Integer> years = new ArrayList<>();
        for (int year = currentYear; year >= currentYear - 10; year--) {
            years.add(year);
        }
        cb_anio_mensual.setItems(FXCollections.observableArrayList(years));
        cb_anio_semestral.setItems(FXCollections.observableArrayList(years));
        cb_anio_mensual.getSelectionModel().select(Integer.valueOf(currentYear));
        cb_anio_semestral.getSelectionModel().select(Integer.valueOf(currentYear));

        cb_mes.setItems(FXCollections.observableArrayList(Month.values()));
        cb_mes.setConverter(new StringConverter<Month>() {
            @Override public String toString(Month month) {
                if (month == null) return "";
                String label = month.getDisplayName(TextStyle.FULL, SPANISH);
                return label.substring(0, 1).toUpperCase(SPANISH) + label.substring(1);
            }
            @Override public Month fromString(String value) { return null; }
        });
        cb_mes.getSelectionModel().select(Month.from(java.time.LocalDate.now()));

        cb_semestre.setItems(FXCollections.observableArrayList(1, 2));
        cb_semestre.setConverter(new StringConverter<Integer>() {
            @Override public String toString(Integer semester) {
                if (semester == null) return "";
                return semester == 1 ? "1 · Enero a junio" : "2 · Julio a diciembre";
            }
            @Override public Integer fromString(String value) { return null; }
        });
        int currentSemester = java.time.LocalDate.now().getMonthValue() <= 6 ? 1 : 2;
        cb_semestre.getSelectionModel().select(Integer.valueOf(currentSemester));

        cb_facultad.setConverter(new StringConverter<Areas>() {
            @Override public String toString(Areas area) {
                return area == null ? "" : area.getNombreArea();
            }
            @Override public Areas fromString(String value) { return null; }
        });
        List<Areas> faculties;
        try {
            faculties = cargarFacultades();
        } catch (RuntimeException exception) {
            faculties = new ArrayList<>();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudieron cargar las facultades",
                    mensajeError(exception));
        }
        cb_facultad.setItems(FXCollections.observableArrayList(faculties));
        if (!faculties.isEmpty()) cb_facultad.getSelectionModel().selectFirst();
    }

    private List<Areas> cargarFacultades() {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            List<Areas> faculties = new ArrayList<>();
            List<?> records = em.createNativeQuery("SELECT id_area, nombre_area FROM gestion.areas "
                    + "WHERE es_facultad IS TRUE ORDER BY nombre_area").getResultList();
            for (Object value : records) {
                Object[] row = (Object[]) value;
                Areas area = new Areas();
                area.setIdArea(((Number) row[0]).intValue());
                area.setNombreArea((String) row[1]);
                area.setEsFacultad(Boolean.TRUE);
                faculties.add(area);
            }
            return faculties;
        } finally {
            em.close();
        }
    }

    @FXML
    public void generarMensual() {
        Month month = cb_mes.getValue();
        Integer year = cb_anio_mensual.getValue();
        Areas faculty = cb_facultad.getValue();
        if (month == null || year == null || faculty == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Faltan criterios",
                    "Seleccioná el mes, año y facultad para generar el informe mensual.");
            return;
        }

        String filename = String.format("notifly_mensual_%04d_%02d_%s.pdf", year,
                month.getValue(), nombreArchivo(faculty.getNombreArea()));
        java.io.File destination = seleccionarDestino(filename);
        if (destination == null) return;
        try {
            new ReportService().exportarInformeMensual(year, month.getValue(),
                    faculty.getIdArea(), faculty.getNombreArea(), destination.getAbsolutePath());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Informe generado",
                    "El informe mensual se guardó en:\n" + destination.getAbsolutePath());
        } catch (Exception exception) {
            exception.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo generar el informe",
                    mensajeError(exception));
        }
    }

    @FXML
    public void generarSemestral() {
        Integer year = cb_anio_semestral.getValue();
        Integer semester = cb_semestre.getValue();
        if (year == null || semester == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Faltan criterios",
                    "Seleccioná el semestre y año para generar el informe semestral.");
            return;
        }

        String filename = String.format("notifly_semestral_%04d_%d.pdf", year, semester);
        java.io.File destination = seleccionarDestino(filename);
        if (destination == null) return;
        try {
            new ReportService().exportarInformeSemestral(year, semester,
                    destination.getAbsolutePath());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Informe generado",
                    "El informe semestral se guardó en:\n" + destination.getAbsolutePath());
        } catch (Exception exception) {
            exception.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo generar el informe",
                    mensajeError(exception));
        }
    }

    @FXML
    public void volver() {
        try {
            App.setRoot("mainscreen");
        } catch (IOException exception) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo volver al menú",
                    exception.getMessage());
        }
    }

    private java.io.File seleccionarDestino(String filename) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar informe PDF");
        chooser.setInitialFileName(filename);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
        Window owner = cb_facultad.getScene().getWindow();
        return chooser.showSaveDialog(owner);
    }

    private String nombreArchivo(String value) {
        return value == null ? "facultad" : value.trim().toLowerCase(SPANISH)
                .replaceAll("[^a-z0-9]+", "_").replaceAll("^_|_$", "");
    }

    private String mensajeError(Exception exception) {
        Throwable cause = exception;
        while (cause.getCause() != null) cause = cause.getCause();
        return cause.getMessage() == null ? "Ocurrió un error al consultar la base de datos." : cause.getMessage();
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
