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

    // create
    @GetMapping("")
    public String cart(Model model){
        List<CartItem> cart =  cartItemService_ift.findAllByIdCart() ;
        double total = 0;
        for ( CartItem ca : cart) {
            total += ca.getQuantity() * ca.getProduct().getPrice();
        }
        model.addAttribute("total", total) ;
        model.addAttribute("cart", cart) ;
        return "client/cart";
    }

    @PostMapping("")
    public String postCart(@RequestParam("productId") Integer productId,
                           @RequestParam("quantity") Integer quantity ) {
        ModelMapper modelMapper = new ModelMapper() ;
        RespProductDTO productDTO = productService_itf.findById(productId) ;
//        // kiểm tra xem sản phẩm có trong giỏ hanhg chưa
        List<CartItem> cart =  cartItemService_ift.findAllByIdCart() ;
        CartItem existingCartItem = cartItemService_ift.findById(productId) ;
        for (CartItem ca : cart) {
            if (ca.equals(existingCartItem)) {
                int newQty = existingCartItem.getQuantity() + quantity ;
                int idCartItem = existingCartItem.getId();
                cartItemService_ift.updateQty(newQty,idCartItem );
                break;
            } else {
                CartItem cartItem = new CartItem() ;
                cartItem.setProduct(modelMapper.map(productDTO, Product.class));
                cartItem.setQuantity(quantity);
                cartItemService_ift.create(cartItem) ;
                break;
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/delete-cart/{id}")
    public String deleteCart(@PathVariable Integer id ) {
        Boolean isDelete = cartItemService_ift.delete(id);
        if ( isDelete) {
            return "redirect:/cart";
        }
        return "client/cart";
    }
}
