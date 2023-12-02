package rikkei.academy.controller.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.dao.CartItemDAO_ITF;
import rikkei.academy.model.entity.CartItem;
import rikkei.academy.model.entity.Product;
import rikkei.academy.model.service.CartItemService_IFT;
import rikkei.academy.model.service.CartService_ITF;
import rikkei.academy.model.service.ProductService_ITF;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartItemService_IFT cartItemService_ift;
    @Autowired
    private ProductService_ITF productService_itf ;
    @Autowired
    HttpSession httpSession ;
    @GetMapping("")
    public String cart(Model model){
        RespUserDTO userLogin =(RespUserDTO) httpSession.getAttribute("user") ;
        List<CartItem> cart =  cartItemService_ift.findAll(userLogin.getUserId()) ;
        model.addAttribute("cart", cart) ;
        return "client/cart";
    }

    @PostMapping("")
    public String postCart(@RequestParam("productId") Integer producId,
                           @RequestParam("quantity") Integer quantity ) {
        ModelMapper modelMapper = new ModelMapper() ;
        RespProductDTO productDTO = productService_itf.findById(producId) ;
        CartItem cartItem = new CartItem() ;
        cartItem.setProduct(modelMapper.map(productDTO, Product.class));
        cartItem.setQuantity(quantity);
//        cartItem.setCart();
        cartItemService_ift.create(cartItem) ;
        return "redirect:/cart";
    }
}
