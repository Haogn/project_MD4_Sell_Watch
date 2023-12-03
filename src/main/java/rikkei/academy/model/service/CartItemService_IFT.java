package rikkei.academy.model.service;

import rikkei.academy.model.entity.CartItem;

import java.util.List;

public interface CartItemService_IFT {
    List<CartItem> findAllByIdCart();
    CartItem findById(Integer id) ;

    Boolean create(CartItem item) ;
    Boolean updateQty(Integer qty , Integer id) ;
    Boolean delete ( Integer id ) ;

}
