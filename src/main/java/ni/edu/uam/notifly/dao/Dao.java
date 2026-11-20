/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.TypedQuery;
import ni.edu.uam.notifly.entity.Notas;

/**
 *
 * @author charly
 */
public class Dao implements IDao{
    @Override
    public<T> List<T> getAll(String namedQuery, Class<T> clazz){
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try{
            TypedQuery<T> query = em.createNamedQuery(namedQuery,clazz);
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            em.close();
        }
        return List.of();
    }

    @Override
    public <T> void insert(T entity) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
    }

    @Override
    public <T> T update(T entity) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try{
            em.getTransaction().begin();
            T ent = em.merge(entity);
            em.flush();
            em.getTransaction().commit();
            return ent;
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        finally{
            em.close();
        }
        return null;
    }

    @Override
    public <T> void remove(T entity) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.flush();
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.getStackTrace();
            em.getTransaction().rollback();
        }
        finally{
            em.close();
        }
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {
        EntityManager em = EntityManagerAdmin.getEntityManager();
        try{
            T entity = em.find(clazz,id);
            return entity;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return null;
    }
    
    
    public List<Notas> getNotasFiltradas(
            String tipo,
            String categoria,
            LocalDate fechaDesde,
            LocalDate fechaHasta) {

        EntityManager em = EntityManagerAdmin.getEntityManager();

        try {
            StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT n " +
                "FROM Notas n " +
                "JOIN FETCH n.idTiponota " +
                "JOIN FETCH n.idClasificacion " +
                "JOIN FETCH n.idEstadonota " +
                "LEFT JOIN FETCH n.idArea " +
                "WHERE n.completado = false ");

            if (tipo != null && !tipo.equals("Todos los tipos")) {
                jpql.append(" AND n.idTiponota.nombreTipo = :tipo");
            }

            if (categoria != null && !categoria.equals("Todas las categorías")) {
                jpql.append(" AND n.idClasificacion.nombreClasificacion = :categoria");
            }

            if (fechaDesde != null) {
                jpql.append(" AND n.fechahora >= :fechaDesde");
            }

            if (fechaHasta != null) {
                jpql.append(" AND n.fechahora < :fechaHasta");
            }

            jpql.append(" ORDER BY n.fechahora DESC");

            TypedQuery<Notas> query = em.createQuery(jpql.toString(), Notas.class);

            if (tipo != null && !tipo.equals("Todos los tipos")) {
                query.setParameter("tipo", tipo);
            }

            if (categoria != null && !categoria.equals("Todas las categorías")) {
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
                Date hastaExclusiva = Date.from(
                    fechaHasta.plusDays(1).atStartOfDay(zona).toInstant()
                );
                query.setParameter("fechaHasta", hastaExclusiva);
            }

            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();

        } finally {
            em.close();
        }
    }
}