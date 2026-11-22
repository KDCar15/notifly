
package ni.edu.uam.notifly.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ni.edu.uam.notifly.entity.Areas;
import ni.edu.uam.notifly.entity.Contactos;
import ni.edu.uam.notifly.entity.Notas;

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
