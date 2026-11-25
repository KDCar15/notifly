package ni.edu.uam.notifly.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ni.edu.uam.notifly.dao.EntityManagerAdmin;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/** Generates the administrator's monthly and semester PDF reports. */
public class ReportService {

    private static final String MONTHLY_TEMPLATE = "/reports/reporte_mensual_facultad.jrxml";
    private static final String SEMESTER_TEMPLATE = "/reports/reporte_semestral_general.jrxml";
    private static final Locale SPANISH = new Locale("es", "NI");

    public void exportarInformeMensual(int year, int month, int facultyId,
            String facultyName, String destination) throws Exception {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = startDate.plusMonths(1).atStartOfDay();

        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            String sql = "SELECT t.nombre_tipo, c.nombre_clasificacion, e.nombre_estado, COUNT(n.id_nota) "
                    + "FROM gestion.notas n "
                    + "JOIN gestion.tipos_nota t ON t.id_tiponota = n.id_tiponota "
                    + "JOIN gestion.clasificaciones c ON c.id_clasificacion = n.id_clasificacion "
                    + "JOIN gestion.estados_nota e ON e.id_estadonota = n.id_estadonota "
                    + "JOIN gestion.areas a ON a.id_area = n.id_area "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? "
                    + "AND a.id_area = ? AND a.es_facultad = TRUE "
                    + "GROUP BY t.nombre_tipo, c.nombre_clasificacion, e.nombre_estado "
                    + "ORDER BY t.nombre_tipo, c.nombre_clasificacion, e.nombre_estado";
            Query query = em.createNativeQuery(sql)
                    .setParameter(1, Timestamp.valueOf(start))
                    .setParameter(2, Timestamp.valueOf(end))
                    .setParameter(3, facultyId);

            List<Map<String, ?>> rows = new ArrayList<>();
            long total = 0;
            long complaints = 0;
            long suggestions = 0;
            for (Object value : query.getResultList()) {
                Object[] result = (Object[]) value;
                long count = asLong(result[3]);
                Map<String, Object> row = new HashMap<>();
                row.put("tipo", result[0] == null ? "Sin tipo" : result[0].toString());
                row.put("clasificacion", result[1] == null ? "Sin clasificación" : result[1].toString());
                row.put("estado", result[2] == null ? "Sin estado" : result[2].toString());
                row.put("cantidad", count);
                rows.add(row);
                total += count;
                if ("Queja".equalsIgnoreCase(String.valueOf(result[0]))) {
                    complaints += count;
                } else if ("Sugerencia".equalsIgnoreCase(String.valueOf(result[0]))) {
                    suggestions += count;
                }
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("PERIODO", startDate.getMonth().getDisplayName(TextStyle.FULL, SPANISH)
                    + " " + year);
            parameters.put("FACULTAD", facultyName);
            parameters.put("TOTAL_CASOS", total);
            parameters.put("TOTAL_QUEJAS", complaints);
            parameters.put("TOTAL_SUGERENCIAS", suggestions);
            parameters.put("LOGO_UAM", ReportService.class.getResource("/reports/uam-logo.png"));
            JasperPrint report = JasperFillManager.fillReport(
                    compileTemplate(MONTHLY_TEMPLATE), parameters,
                    new JRMapCollectionDataSource(rows));
            JasperExportManager.exportReportToPdfFile(report, destination);
        } finally {
            em.close();
        }
    }

    public void exportarInformeSemestral(int year, int semester, String destination) throws Exception {
        LocalDate startDate = LocalDate.of(year, semester == 1 ? 1 : 7, 1);
        LocalDate endDate = startDate.plusMonths(6);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atStartOfDay();

        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            Timestamp startTimestamp = Timestamp.valueOf(start);
            Timestamp endTimestamp = Timestamp.valueOf(end);
            long total = scalarCount(em, "SELECT COUNT(*) FROM gestion.notas n "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ?", startTimestamp, endTimestamp);
            long complaints = scalarCount(em, "SELECT COUNT(*) FROM gestion.notas n "
                    + "JOIN gestion.tipos_nota t ON t.id_tiponota = n.id_tiponota "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? AND LOWER(t.nombre_tipo) = 'queja'",
                    startTimestamp, endTimestamp);
            long suggestions = scalarCount(em, "SELECT COUNT(*) FROM gestion.notas n "
                    + "JOIN gestion.tipos_nota t ON t.id_tiponota = n.id_tiponota "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? AND LOWER(t.nombre_tipo) = 'sugerencia'",
                    startTimestamp, endTimestamp);

            String sql = "SELECT 'Casos por mes y tipo' AS seccion, "
                    + "TO_CHAR(DATE_TRUNC('month', n.fechahora), 'YYYY-MM') AS grupo, "
                    + "t.nombre_tipo AS detalle, COUNT(n.id_nota) AS cantidad "
                    + "FROM gestion.notas n JOIN gestion.tipos_nota t ON t.id_tiponota = n.id_tiponota "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? "
                    + "GROUP BY DATE_TRUNC('month', n.fechahora), t.nombre_tipo "
                    + "UNION ALL "
                    + "SELECT 'Quejas por clasificación', c.nombre_clasificacion, '', COUNT(n.id_nota) "
                    + "FROM gestion.notas n "
                    + "JOIN gestion.tipos_nota t ON t.id_tiponota = n.id_tiponota "
                    + "JOIN gestion.clasificaciones c ON c.id_clasificacion = n.id_clasificacion "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? AND LOWER(t.nombre_tipo) = 'queja' "
                    + "GROUP BY c.nombre_clasificacion "
                    + "UNION ALL "
                    + "SELECT 'Facultad por estado', COALESCE(a.nombre_area, 'Sin facultad'), e.nombre_estado, COUNT(n.id_nota) "
                    + "FROM gestion.notas n "
                    + "JOIN gestion.estados_nota e ON e.id_estadonota = n.id_estadonota "
                    + "LEFT JOIN gestion.areas a ON a.id_area = n.id_area AND a.es_facultad = TRUE "
                    + "WHERE n.fechahora >= ? AND n.fechahora < ? "
                    + "GROUP BY COALESCE(a.nombre_area, 'Sin facultad'), e.nombre_estado "
                    + "ORDER BY seccion, grupo, detalle";
            Query query = em.createNativeQuery(sql)
                    .setParameter(1, startTimestamp).setParameter(2, endTimestamp)
                    .setParameter(3, startTimestamp).setParameter(4, endTimestamp)
                    .setParameter(5, startTimestamp).setParameter(6, endTimestamp);
            List<Map<String, ?>> rows = new ArrayList<>();
            for (Object value : query.getResultList()) {
                Object[] result = (Object[]) value;
                Map<String, Object> row = new HashMap<>();
                row.put("seccion", result[0] == null ? "" : result[0].toString());
                row.put("grupo", result[1] == null ? "" : result[1].toString());
                row.put("detalle", result[2] == null ? "" : result[2].toString());
                row.put("cantidad", asLong(result[3]));
                rows.add(row);
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("PERIODO", "Semestre " + semester + " de " + year
                    + " (" + startDate.getMonth().getDisplayName(TextStyle.FULL, SPANISH)
                    + " - " + endDate.minusDays(1).getMonth().getDisplayName(TextStyle.FULL, SPANISH) + ")");
            parameters.put("TOTAL_CASOS", total);
            parameters.put("TOTAL_QUEJAS", complaints);
            parameters.put("TOTAL_SUGERENCIAS", suggestions);
            parameters.put("LOGO_UAM", ReportService.class.getResource("/reports/uam-logo.png"));
            JasperPrint report = JasperFillManager.fillReport(
                    compileTemplate(SEMESTER_TEMPLATE), parameters,
                    new JRMapCollectionDataSource(rows));
            JasperExportManager.exportReportToPdfFile(report, destination);
        } finally {
            em.close();
        }
    }

    private long scalarCount(EntityManager em, String sql, Timestamp start, Timestamp end) {
        Object value = em.createNativeQuery(sql)
                .setParameter(1, start)
                .setParameter(2, end)
                .getSingleResult();
        return asLong(value);
    }

    private long asLong(Object value) {
        return value instanceof Number ? ((Number) value).longValue() : 0L;
    }

    private JasperReport compileTemplate(String resource) throws JRException {
        InputStream input = ReportService.class.getResourceAsStream(resource);
        if (input == null) {
            throw new JRException("No se encontró la plantilla Jasper: " + resource);
        }
        try (InputStream reportInput = input) {
            return JasperCompileManager.compileReport(reportInput);
        } catch (java.io.IOException exception) {
            throw new JRException("No se pudo cerrar la plantilla Jasper: " + resource, exception);
        }
    }
}
