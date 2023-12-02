package rikkei.academy.model.dao;

import rikkei.academy.model.entity.Product;

import java.util.List;

public interface ProductDAO_ITF extends IGenericDAO<Product, Integer>{
     List<Product> findAllProductByCategory(Integer id );
    Integer countProduct();
    List<Product> findAllProductPage(int offset , int size );

}
