package rikkei.academy.model.dao;

import java.util.List;

public interface IGenericDAO<T , E> {
    List<T> findAll();
    T findById(E e) ;
    Boolean create(T t) ;
    Boolean delete(E e) ;
    Boolean update(E e , T t) ;
}
