package rikkei.academy.controller.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.entity.*;
import rikkei.academy.model.service.CartItemService_IFT;
import rikkei.academy.model.service.OrderDetailService_ITF;
import rikkei.academy.model.service.OrdersService_ITF;
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
    private OrdersService_ITF ordersServiceItf ;
    @Autowired
    private OrderDetailService_ITF orderDetailServiceItf ;
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
        List<CartItem> cart = cartItemService_ift.findAllByIdCart();
        boolean productExists = false;
        for (CartItem ca : cart) {
            if (ca.getProduct().getProductId().equals(productId)) {
                int newQty = ca.getQuantity() + quantity;
                int idCartItem = ca.getId();
                cartItemService_ift.updateQty(newQty, idCartItem);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(modelMapper.map(productDTO, Product.class));
            cartItem.setQuantity(quantity);
            cartItemService_ift.create(cartItem);
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

    @GetMapping("/checkout")
    public String checkout(Model model){
        List<CartItem> cart =  cartItemService_ift.findAllByIdCart() ;
        double subtotal = 0;
        for ( CartItem ca : cart) {
            subtotal += ca.getQuantity() * ca.getProduct().getPrice();
        }
        double total = subtotal + 5 ;
        Orders orders = new Orders();
        model.addAttribute("orders" , orders);
        model.addAttribute("subtotal", subtotal) ;
        model.addAttribute("total", total) ;
        model.addAttribute("cart", cart) ;
        return "client/checkout" ;
    }

    @PostMapping("/history")
    public String postCheckout(@ModelAttribute("order") Orders orders) {
        List<CartItem> cart = cartItemService_ift.findAllByIdCart();
        ModelMapper modelMapper = new ModelMapper();
        RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user");
        orders.setUser(modelMapper.map(userLogin, User.class));

        if (ordersServiceItf.addOrder(orders)) {
            // Get the newly created order
            List<Orders> list = ordersServiceItf.findAllOrderByIdUser(userLogin.getUserId());

            if (!list.isEmpty()) {
                Orders newOrders = list.get(list.size() - 1);

                for (CartItem ca : cart) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrders(newOrders);
                    orderDetail.setProduct(ca.getProduct());
                    orderDetail.setQuantity(ca.getQuantity());
                    double total = ca.getQuantity() * ca.getProduct().getPrice() ;
                    orderDetail.setTotal(total);
                    orderDetailServiceItf.create(orderDetail);
                    cartItemService_ift.delete(ca.getId());
                }
                return "redirect:/cart/order-history/" + newOrders.getOrder_id();
            } else {
                // Handle the case where order creation failed
                return "client/cart";
            }
        } else {
            // Handle the case where order creation failed
            return "client/cart";
        }
    }


    @GetMapping("/order-history/{order_id}")
    public String orderHistory(@PathVariable("order_id") Integer orderId, Model model) {
        // Find the specific order by order ID;
        RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user");
        List<Orders> list = ordersServiceItf.findAllOrderByIdUser(userLogin.getUserId());
        model.addAttribute("list", list) ;

        // Find order details for the specific order
        List<OrderDetail> orderDetail = orderDetailServiceItf.findAllByIdOrder(orderId);
        model.addAttribute("orderDetail", orderDetail);

        return "client/order-history";
    }

}
