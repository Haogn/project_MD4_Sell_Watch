package rikkei.academy.model.service;

import rikkei.academy.model.entity.CartItem;

import java.util.List;

public interface CartItemService_IFT {
    List<CartItem> findAll(Integer idUser);
    CartItem findById(Integer id) ;

    Boolean create(CartItem item) ;
    Boolean update(CartItem item , Integer id) ;
    void delete ( Integer id ) ;

}
