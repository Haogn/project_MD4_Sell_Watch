package rikkei.academy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.dao.CartDAO_ITF;
import rikkei.academy.model.entity.Cart;

import javax.servlet.http.HttpSession;

@Service
public class CartService_IPML implements CartService_ITF{
    @Autowired
    private CartDAO_ITF cartDAO_itf;

    @Override
    public Boolean addCart(Cart cart) {
        return cartDAO_itf.addCart(cart);
    }

    @Override
    public Cart findByIdUser(Integer id) {
        return cartDAO_itf.findByIdUser(id);
    }
}
