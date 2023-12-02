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
    public List<CartItem> findAll(Integer IdUser) {
        return cartItemDAO_itf.findAll(IdUser);
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
    public Boolean update(CartItem item, Integer id) {
        return cartItemDAO_itf.update(item, id);
    }

    @Override
    public void delete(Integer id) {
        cartItemDAO_itf.delete(id);
    }
}
