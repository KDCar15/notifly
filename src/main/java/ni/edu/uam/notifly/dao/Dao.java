
package ni.edu.uam.notifly.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.entity.Clasificaciones;
import ni.edu.uam.notifly.entity.Contactos;
import ni.edu.uam.notifly.entity.EstadosNota;
import ni.edu.uam.notifly.entity.Notas;
import ni.edu.uam.notifly.entity.TiposNota;
import ni.edu.uam.notifly.model.PlantillaCorreo;

public class Dao implements IDao {

    // =================================================
    // MÉTODOS GENERALES
    // =================================================

    @Override
    public <T> List<T> getAll(String namedQuery, Class<T> clazz) {

        EntityManager em = EntityManagerAdmin.getEntityManager();

        try {
            TypedQuery<T> query =
                    em.createNamedQuery(namedQuery, clazz);

            return query.getResultList();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();

        } finally {
            em.close();
        }
    }

    @Override
    public <T> void insert(T entity) {

        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(entity);

            em.flush();
            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    @Override
    public <T> T update(T entity) {

        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            T resultado = em.merge(entity);

            em.flush();
            transaction.commit();

            return resultado;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    public <T> void remove(T entity) {

        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            T entidad = em.merge(entity);

            em.remove(entidad);

            em.flush();
            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {

        EntityManager em = EntityManagerAdmin.getEntityManager();

        try {

            if (id == null || id.isBlank()) {
                return null;
            }

            Class<?> tipoId = em.getMetamodel()
                    .entity(clazz)
                    .getIdType()
                    .getJavaType();

            Object identificador = convertirIdentificador(id, tipoId);

            return em.find(clazz, identificador);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            em.close();
        }
    }

    private Object convertirIdentificador(String id, Class<?> tipo) {

        if (tipo == String.class) {
            return id;
        }

        if (tipo == Integer.class || tipo == int.class) {
            return Integer.valueOf(id);
        }

        if (tipo == Long.class || tipo == long.class) {
            return Long.valueOf(id);
        }

        if (tipo == Short.class || tipo == short.class) {
            return Short.valueOf(id);
        }

        if (tipo == Byte.class || tipo == byte.class) {
            return Byte.valueOf(id);
        }

        if (tipo == BigInteger.class) {
            return new BigInteger(id);
        }

        if (tipo == BigDecimal.class) {
            return new BigDecimal(id);
        }

        if (tipo == UUID.class) {
            return UUID.fromString(id);
        }

        throw new IllegalArgumentException(
                "Tipo de identificador no soportado: " + tipo.getName()
        );
    }

    // =================================================
    // MÉTODOS PARA NOTAS
    // =================================================

    public List<Notas> getNotasFiltradas(
            String tipo,
            String categoria,
            LocalDate fechaDesde,
            LocalDate fechaHasta) {

        EntityManager em = EntityManagerAdmin.getEntityManager();

        try {

            StringBuilder jpql = new StringBuilder(
                    "SELECT DISTINCT n FROM Notas n " +
                            "JOIN FETCH n.idTiponota " +
                            "JOIN FETCH n.idClasificacion " +
                            "JOIN FETCH n.idEstadonota " +
                            "LEFT JOIN FETCH n.idArea " +
                            "WHERE 1 = 1 "
            );

            if (tipo != null && !tipo.equals("Todos los tipos")) {
                jpql.append(
                        "AND n.idTiponota.nombreTipo = :tipo "
                );
            }

            if (categoria != null
                    && !categoria.equals("Todas las categorías")) {

                jpql.append(
                        "AND n.idClasificacion.nombreClasificacion = :categoria "
                );
            }

            if (fechaDesde != null) {
                jpql.append(
                        "AND n.fechahora >= :fechaDesde "
                );
            }

            if (fechaHasta != null) {
                jpql.append(
                        "AND n.fechahora < :fechaHasta "
                );
            }

            jpql.append("ORDER BY n.fechahora DESC");

            TypedQuery<Notas> query = em.createQuery(
                    jpql.toString(),
                    Notas.class
            );

            if (tipo != null && !tipo.equals("Todos los tipos")) {
                query.setParameter("tipo", tipo);
            }

            if (categoria != null
                    && !categoria.equals("Todas las categorías")) {

                query.setParameter("categoria", categoria);
            }

            ZoneId zona = ZoneId.systemDefault();

            if (fechaDesde != null) {

                Date desde = Date.from(
                        fechaDesde.atStartOfDay(zona).toInstant()
                );

                query.setParameter("fechaDesde", desde);
            }

            if (fechaHasta != null) {

                Date hasta = Date.from(
                        fechaHasta.plusDays(1)
                                .atStartOfDay(zona)
                                .toInstant()
                );

                query.setParameter("fechaHasta", hasta);
            }

            return query.getResultList();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();

        } finally {
            em.close();
        }
    }

    /** Creates a case using the existing areas, types, classifications and default state. */
    public Notas crearNota(String nombres, String correo, String celular,
            String descripcion, String tipo, String categoria, Integer areaId) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TiposNota tipoNota = em.createNamedQuery("TiposNota.findByNombreTipo", TiposNota.class)
                    .setParameter("nombreTipo", tipo).getSingleResult();
            Clasificaciones clasificacion = em.createNamedQuery(
                    "Clasificaciones.findByNombreClasificacion", Clasificaciones.class)
                    .setParameter("nombreClasificacion", categoria).getSingleResult();
            EstadosNota estado = em.createQuery(
                    "SELECT e FROM EstadosNota e WHERE LOWER(e.nombreEstado) IN ('pendiente','en revisión','en revision') ORDER BY e.idEstadonota",
                    EstadosNota.class).setMaxResults(1).getSingleResult();

            Notas nota = new Notas();
            nota.setNombresapellidos(nombres.trim());
            nota.setCorreo(correo.trim());
            nota.setCelular(celular.trim());
            nota.setDescripcion(descripcion.trim());
            nota.setFechahora(new Date());
            nota.setCompletado(false);
            nota.setIdTiponota(tipoNota);
            nota.setIdClasificacion(clasificacion);
            nota.setIdEstadonota(estado);
            nota.setIdArea(areaId == null ? null : em.find(Areas.class, areaId));
            if (areaId != null && nota.getIdArea() == null) {
                throw new IllegalArgumentException("El área seleccionada ya no existe.");
            }
            em.persist(nota);
            em.flush();
            transaction.commit();
            return nota;
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("No se pudo crear la nota:");
            exception.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /** Records the latest access, using the existing upsert semantics of the history table. */
    public boolean registrarAccesoNota(int idNota) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createNativeQuery("INSERT INTO auditoria.historial_notas (id_nota) VALUES (?) "
                    + "ON CONFLICT (id_nota) DO UPDATE SET fecha_acceso = CURRENT_TIMESTAMP")
                    .setParameter(1, idNota).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("No se pudo actualizar el historial de la nota:");
            exception.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /** Returns history rows with current case data and the most recent access timestamp. */
    public List<Object[]> buscarHistorial(String tipo, String estado, String categoria,
            Integer facultadId, Integer areaId, LocalDate desde, LocalDate hasta) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT h.fecha_acceso, n.id_nota, t.nombre_tipo, "
                    + "CASE WHEN a.es_facultad IS TRUE THEN a.nombre_area ELSE '' END, "
                    + "c.nombre_clasificacion, "
                    + "CASE WHEN COALESCE(a.es_facultad,FALSE) IS FALSE THEN COALESCE(a.nombre_area,'') ELSE '' END, "
                    + "e.nombre_estado "
                    + "FROM auditoria.historial_notas h "
                    + "JOIN gestion.notas n ON n.id_nota=h.id_nota "
                    + "JOIN gestion.tipos_nota t ON t.id_tiponota=n.id_tiponota "
                    + "JOIN gestion.clasificaciones c ON c.id_clasificacion=n.id_clasificacion "
                    + "JOIN gestion.estados_nota e ON e.id_estadonota=n.id_estadonota "
                    + "LEFT JOIN gestion.areas a ON a.id_area=n.id_area WHERE 1=1 ");
            List<Object> parameters = new ArrayList<>();
            if (tipo != null && !tipo.isBlank()) { sql.append("AND t.nombre_tipo=? "); parameters.add(tipo); }
            if (estado != null && !estado.isBlank()) { sql.append("AND e.nombre_estado=? "); parameters.add(estado); }
            if (categoria != null && !categoria.isBlank()) { sql.append("AND c.nombre_clasificacion=? "); parameters.add(categoria); }
            if (facultadId != null && areaId != null) {
                sql.append("AND n.id_area IN (?,?) "); parameters.add(facultadId); parameters.add(areaId);
            } else if (facultadId != null) { sql.append("AND n.id_area=? AND a.es_facultad IS TRUE "); parameters.add(facultadId); }
            else if (areaId != null) { sql.append("AND n.id_area=? AND COALESCE(a.es_facultad,FALSE) IS FALSE "); parameters.add(areaId); }
            if (desde != null) {
                sql.append("AND h.fecha_acceso>=? ");
                parameters.add(Timestamp.from(desde.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
            if (hasta != null) {
                sql.append("AND h.fecha_acceso<? ");
                parameters.add(Timestamp.from(hasta.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
            sql.append("ORDER BY h.fecha_acceso DESC, n.id_nota DESC");
            javax.persistence.Query query = em.createNativeQuery(sql.toString());
            for (int i = 0; i < parameters.size(); i++) query.setParameter(i + 1, parameters.get(i));
            return query.getResultList();
        } catch (Exception exception) {
            System.err.println("No se pudo consultar el historial:");
            exception.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

    public Notas buscarNotaCompleta(int idNota) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT n FROM Notas n "
                    + "JOIN FETCH n.idTiponota JOIN FETCH n.idClasificacion "
                    + "JOIN FETCH n.idEstadonota LEFT JOIN FETCH n.idArea WHERE n.idNota=:id",
                    Notas.class).setParameter("id", idNota).getSingleResult();
        } catch (Exception exception) {
            System.err.println("No se pudo cargar la nota del historial:");
            exception.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // =================================================
    // PLANTILLAS DE CORREO (respuestas.plantillas)
    // =================================================

    public List<PlantillaCorreo> buscarPlantillas(String filtro) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            String sql = "SELECT id_plantilla,nombre_plantilla,contenido FROM respuestas.plantillas ";
            boolean filtrar = filtro != null && !filtro.isBlank();
            if (filtrar) sql += "WHERE LOWER(nombre_plantilla) LIKE ? OR LOWER(contenido) LIKE ? ";
            sql += "ORDER BY LOWER(nombre_plantilla)";
            javax.persistence.Query query = em.createNativeQuery(sql);
            if (filtrar) {
                String like = "%" + filtro.trim().toLowerCase() + "%";
                query.setParameter(1, like).setParameter(2, like);
            }
            List<PlantillaCorreo> plantillas = new ArrayList<>();
            for (Object item : query.getResultList()) {
                Object[] row = (Object[]) item;
                plantillas.add(new PlantillaCorreo(((Number) row[0]).intValue(),
                        String.valueOf(row[1]), String.valueOf(row[2])));
            }
            return plantillas;
        } catch (Exception exception) {
            System.err.println("No se pudieron cargar las plantillas de respuestas:");
            exception.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

    public PlantillaCorreo guardarPlantilla(PlantillaCorreo plantilla) {
        if (plantilla == null) return null;
        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (plantilla.getId() == null) {
                em.createNativeQuery("INSERT INTO respuestas.plantillas (nombre_plantilla,contenido) VALUES (?,?)")
                        .setParameter(1, plantilla.getNombre().trim())
                        .setParameter(2, plantilla.getContenido().trim()).executeUpdate();
                Number id = (Number) em.createNativeQuery(
                        "SELECT currval(pg_get_serial_sequence('respuestas.plantillas','id_plantilla'))")
                        .getSingleResult();
                plantilla.setId(id.intValue());
            } else {
                int actualizadas = em.createNativeQuery("UPDATE respuestas.plantillas "
                        + "SET nombre_plantilla=?, contenido=? WHERE id_plantilla=?")
                        .setParameter(1, plantilla.getNombre().trim())
                        .setParameter(2, plantilla.getContenido().trim())
                        .setParameter(3, plantilla.getId()).executeUpdate();
                if (actualizadas == 0) throw new IllegalArgumentException("La plantilla ya no existe.");
            }
            transaction.commit();
            return plantilla;
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("No se pudo guardar la plantilla:");
            exception.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean eliminarPlantilla(int id) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            int eliminadas = em.createNativeQuery("DELETE FROM respuestas.plantillas WHERE id_plantilla=?")
                    .setParameter(1, id).executeUpdate();
            transaction.commit();
            return eliminadas > 0;
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("No se pudo eliminar la plantilla:");
            exception.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /** Counts cases by their current status for the administrator dashboard. */
    public Map<String, Long> contarCasosPorEstado() {
        return contarCasosPor("gestion.estados_nota", "id_estadonota", "nombre_estado");
    }

    /** Counts cases by type (complaint, suggestion, or other configured types). */
    public Map<String, Long> contarCasosPorTipo() {
        return contarCasosPor("gestion.tipos_nota", "id_tiponota", "nombre_tipo");
    }

    private Map<String, Long> contarCasosPor(String tabla, String clave, String nombre) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            List<?> rows = em.createNativeQuery("SELECT COALESCE(cat." + nombre + ",'Sin clasificar'), COUNT(*) "
                    + "FROM gestion.notas n LEFT JOIN " + tabla + " cat ON cat." + clave + "=n." + clave
                    + " GROUP BY cat." + nombre + " ORDER BY cat." + nombre).getResultList();
            Map<String, Long> counts = new LinkedHashMap<>();
            for (Object value : rows) {
                Object[] row = (Object[]) value;
                counts.put(String.valueOf(row[0]), ((Number) row[1]).longValue());
            }
            return counts;
        } catch (Exception exception) {
            System.err.println("No se pudieron resumir los casos para el dashboard:");
            exception.printStackTrace();
            return Map.of();
        } finally { em.close(); }
    }

    /** Monthly case totals for the most recent six calendar months. */
    public Map<String, Long> contarCasosPorMes() {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try {
            List<?> rows = em.createNativeQuery("SELECT TO_CHAR(DATE_TRUNC('month', fechahora), 'YYYY-MM'), COUNT(*) "
                    + "FROM gestion.notas WHERE fechahora >= DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '5 months' "
                    + "GROUP BY DATE_TRUNC('month', fechahora) ORDER BY DATE_TRUNC('month', fechahora)").getResultList();
            Map<String, Long> counts = new LinkedHashMap<>();
            for (Object value : rows) {
                Object[] row = (Object[]) value;
                counts.put(String.valueOf(row[0]), ((Number) row[1]).longValue());
            }
            return counts;
        } catch (Exception exception) {
            System.err.println("No se pudieron resumir los casos mensuales:");
            exception.printStackTrace();
            return Map.of();
        } finally { em.close(); }
    }

    public long contarTotalCasos() {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try { return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM gestion.notas").getSingleResult()).longValue(); }
        catch (Exception exception) { exception.printStackTrace(); return 0; }
        finally { em.close(); }
    }

    // =================================================
    // MÉTODOS PARA CONTACTOS
    // =================================================

    // -------------------------------------------------
    // GUARDAR O ACTUALIZAR CONTACTO
    // -------------------------------------------------

    public Contactos guardarContacto(Contactos datos) {

        if (datos == null) {
            return null;
        }

        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            boolean esNuevo = datos.getIdContacto() == null;

            Contactos contacto;

            if (esNuevo) {

                contacto = new Contactos();

            } else {

                contacto = em.find(
                        Contactos.class,
                        datos.getIdContacto()
                );

                if (contacto == null) {
                    throw new IllegalArgumentException(
                            "No existe el contacto que se intenta actualizar."
                    );
                }
            }

            contacto.setNombresapellidos(datos.getNombresapellidos());
            contacto.setOcupacion(datos.getOcupacion());
            contacto.setCelular(datos.getCelular());
            contacto.setCorreo(datos.getCorreo());

            // Recuperar el área administrada por este EntityManager.
            if (datos.getIdArea() != null) {

                Integer idArea = datos.getIdArea().getIdArea();

                if (idArea == null) {
                    throw new IllegalArgumentException(
                            "El área seleccionada no tiene identificador."
                    );
                }

                Areas area = em.find(Areas.class, idArea);

                if (area == null) {
                    throw new IllegalArgumentException(
                            "El área seleccionada no existe."
                    );
                }

                contacto.setIdArea(area);

            } else {

                contacto.setIdArea(null);
            }

            if (esNuevo) {
                em.persist(contacto);
            }

            em.flush();
            transaction.commit();

            return contacto;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error al guardar contacto:");
            e.printStackTrace();

            return null;

        } finally {
            em.close();
        }
    }

    // -------------------------------------------------
    // BUSCAR CONTACTOS
    // -------------------------------------------------

    public List<Contactos> buscarContactos(String texto) {

        EntityManager em = EntityManagerAdmin.getEntityManager();

        try {

            StringBuilder jpql = new StringBuilder(
                    "SELECT DISTINCT c FROM Contactos c " +
                            "LEFT JOIN FETCH c.idArea "
            );

            boolean filtrar = texto != null && !texto.isBlank();

            if (filtrar) {

                jpql.append(
                        "WHERE (" +
                                "LOWER(c.nombresapellidos) LIKE :texto " +
                                "OR LOWER(c.ocupacion) LIKE :texto " +
                                "OR LOWER(c.correo) LIKE :texto " +
                                "OR LOWER(c.celular) LIKE :texto " +
                                "OR EXISTS (" +
                                "SELECT a FROM Areas a " +
                                "WHERE a = c.idArea " +
                                "AND LOWER(a.nombreArea) LIKE :texto" +
                                ")" +
                                ") "
                );
            }

            jpql.append("ORDER BY c.nombresapellidos ASC");

            TypedQuery<Contactos> query = em.createQuery(
                    jpql.toString(),
                    Contactos.class
            );

            if (filtrar) {

                query.setParameter(
                        "texto",
                        "%" + texto.trim().toLowerCase() + "%"
                );
            }

            return query.getResultList();

        } catch (Exception e) {

            System.err.println("Error al buscar contactos:");
            e.printStackTrace();

            return List.of();

        } finally {
            em.close();
        }
    }

    // -------------------------------------------------
    // ELIMINAR CONTACTO
    // -------------------------------------------------

    public boolean eliminarContacto(Contactos contacto) {

        if (contacto == null || contacto.getIdContacto() == null) {
            return false;
        }

        EntityManager em = EntityManagerAdmin.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            Contactos contactoBD = em.find(
                    Contactos.class,
                    contacto.getIdContacto()
            );

            if (contactoBD == null) {

                transaction.rollback();
                return false;
            }

            em.remove(contactoBD);

            em.flush();
            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error al eliminar contacto:");
            e.printStackTrace();

            return false;

        } finally {
            em.close();
        }
    }
}
