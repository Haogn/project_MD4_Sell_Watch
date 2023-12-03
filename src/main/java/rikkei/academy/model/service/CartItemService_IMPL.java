package rikkei.academy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.dao.CartItemDAO_ITF;
import rikkei.academy.model.entity.Cart;
import rikkei.academy.model.entity.CartItem;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
public class CartItemService_IMPL implements CartItemService_IFT{
    @Autowired
    private CartItemDAO_ITF cartItemDAO_itf ;
    @Autowired
    private HttpSession httpSession ;
    @Autowired
    private CartService_ITF cartService_itf ;
    @Override
    public List<CartItem> findAllByIdCart() {
        RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user") ;
        Cart cart = cartService_itf.findByIdUser(userLogin.getUserId()) ;
        return cartItemDAO_itf.findAll(cart.getCart_id());
    }

    @Override
    public CartItem findById(Integer id) {
        return cartItemDAO_itf.findById(id);
    }

    @Override
    public Boolean create(CartItem item) {
        RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user") ;
        Cart cart = cartService_itf.findByIdUser(userLogin.getUserId()) ;
        item.setCart(cart);
        return cartItemDAO_itf.create(item);
    }

    @Override
    public Boolean updateQty(Integer qty, Integer id) {
        return cartItemDAO_itf.updateQty(qty, id);
    }

    @Override
    public Boolean delete(Integer id) {
        return cartItemDAO_itf.delete(id);
    }
}
