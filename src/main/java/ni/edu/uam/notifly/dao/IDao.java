/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ni.edu.uam.notifly.dao;

/**
 *
 * @author charly
 */
import java.util.List;

public interface IDao {
    <T> List<T> getAll(String namedQuery, Class<T> clazz);
    <T> void insert(T entity);
    <T> T update(T entity);
    <T> void remove(T entity);
    <T> T findById(Class<T> clazz, String id);
}