package rikkei.academy.model.dao;

import rikkei.academy.model.entity.Cart;
import rikkei.academy.model.entity.CartItem;
import java.util.List;
public interface CartDAO_ITF {
    Boolean addCart(Cart cart) ;
    Cart findByIdUser( Integer id) ;
}
