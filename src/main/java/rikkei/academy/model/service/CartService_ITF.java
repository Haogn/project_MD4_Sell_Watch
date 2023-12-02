package rikkei.academy.model.service;

import rikkei.academy.model.entity.Cart;

public interface CartService_ITF {
    Boolean addCart(Cart cart) ;
    Cart findByIdUser( Integer id) ;
}
