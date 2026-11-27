package ni.edu.uam.notifly.controller;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ni.edu.uam.notifly.App;
import ni.edu.uam.notifly.dao.Dao;

public class DashboardController {
    @FXML private Label lbl_total, lbl_quejas, lbl_sugerencias, lbl_pendientes, lbl_completados, lbl_estado_conexion;
    @FXML private PieChart chart_estados;
    @FXML private BarChart<String, Number> chart_meses;
    private final Dao dao = new Dao();

    @FXML public void initialize() { cargarDatos(); }

    @FXML public void actualizar() { cargarDatos(); }

    private void cargarDatos() {
        long total;
        Map<String, Long> tipos;
        Map<String, Long> estados;
        Map<String, Long> mesesDb;
        try {
            total = dao.contarTotalCasos();
            tipos = dao.contarCasosPorTipo();
            estados = dao.contarCasosPorEstado();
            mesesDb = dao.contarCasosPorMes();
            lbl_estado_conexion.setText("Datos actualizados desde la base de datos.");
            lbl_estado_conexion.setStyle("-fx-text-fill: #247A62; -fx-font-size: 12px;");
        } catch (RuntimeException exception) {
            lbl_estado_conexion.setText("No se pudo conectar a la base de datos. Inicia PostgreSQL y pulsa Actualizar datos.");
            lbl_estado_conexion.setStyle("-fx-text-fill: #A63E3E; -fx-font-size: 12px;");
            lbl_total.setText("—"); lbl_quejas.setText("—"); lbl_sugerencias.setText("—");
            lbl_pendientes.setText("—"); lbl_completados.setText("—");
            chart_estados.getData().clear(); chart_meses.getData().clear();
            return;
        }
        lbl_total.setText(Long.toString(total));
        lbl_quejas.setText(Long.toString(contar(tipos, "queja")));
        lbl_sugerencias.setText(Long.toString(contar(tipos, "sugerencia")));
        lbl_pendientes.setText(Long.toString(contar( estados, "pendiente")));
        lbl_completados.setText(Long.toString(contar(estados, "completado")));

        chart_estados.setData(FXCollections.observableArrayList());
        estados.forEach((estado, cantidad) -> chart_estados.getData().add(new PieChart.Data(estado + " · " + cantidad, cantidad)));

        YearMonth actual = YearMonth.now();
        DateTimeFormatter keyFormat = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter labelFormat = DateTimeFormatter.ofPattern("MMM yy", new Locale("es", "NI"));
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Casos registrados");
        for (int offset = -5; offset <= 0; offset++) {
            YearMonth mes = actual.plusMonths(offset);
            serie.getData().add(new XYChart.Data<>(mes.format(labelFormat), mesesDb.getOrDefault(mes.format(keyFormat), 0L)));
        }
        chart_meses.getData().setAll(serie);
    }

    private long contar(Map<String, Long> datos, String fragmento) {
        return datos.entrySet().stream().filter(e -> e.getKey().toLowerCase(Locale.ROOT).contains(fragmento))
                .mapToLong(Map.Entry::getValue).sum();
    }

    @FXML public void volver() {
        try { App.setRoot("mainscreen"); }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo volver al menú principal.");
            alert.setHeaderText(null); alert.showAndWait();
        }
    }
}
