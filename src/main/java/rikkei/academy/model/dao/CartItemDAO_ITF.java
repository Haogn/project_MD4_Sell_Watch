package rikkei.academy.model.dao;

import rikkei.academy.model.entity.CartItem;

import java.util.List;

public interface CartItemDAO_ITF {
    List<CartItem> findAll(Integer idCart);
    CartItem findById(Integer id) ;

    Boolean create(CartItem item) ;
    Boolean update(CartItem item , Integer id) ;
    void delete ( Integer id ) ;


}
