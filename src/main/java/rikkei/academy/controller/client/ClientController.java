package rikkei.academy.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"/","/home"})

public class ClientController {
    @Autowired
//    private UserService_IMPL userServiceImpl;
    private UserService_ITF userService_itf ;
    @Autowired
    private ProductService_ITF productService_itf;

    @Autowired
    private CategoryService_ITF categoryService_itf;
    // TODO : trang chu
    @GetMapping("")
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "8") int size){
        List<RespCategoryDTO> listCategory = categoryService_itf.findAll();
        model.addAttribute("listCategory" , listCategory);
        int totalProduct = productService_itf.countProduct();
        int totalPage = (int) Math.ceil((double) totalProduct /size) ;
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        List<RespProductDTO> listProduct = productService_itf.findAllProductPage(page,size);
        model.addAttribute("listProduct",listProduct);
        return "client/index";
    }



    // TODO : danh sach san pham
    @GetMapping("/list-watch")
    public String showListWatch(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size){
        int totalProduct = productService_itf.countProduct();
        int totalPage = (int) Math.ceil((double) totalProduct /size) ;
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        List<RespProductDTO> list = productService_itf.findAllProductPage(page,size) ;
        model.addAttribute("list",list);
        return "client/list-watch";
    }

    @PostMapping("")
    public String postSortBySelect(){
        return "client/list-watch" ;
    }
    // TODO : chi tiet san pham
    @GetMapping("/watch-detail/{id}")
    public String showWatchDetail(@PathVariable Integer id , Model model){
        RespProductDTO product = productService_itf.findById(id) ;
        model.addAttribute("product", product);
        int idCategory = product.getCategory().getCategoryId();
        List<RespProductDTO> listProductByCategory = productService_itf.findAllProductByCategory(idCategory ) ;
        model.addAttribute("listProductByCategory",listProductByCategory) ;
        List<RespProductDTO> list = productService_itf.findAll();
        model.addAttribute("list",list);
        return "client/watch-detail";
    }


    @GetMapping("/wishlist")
    public String wishlistProduct(Model model){

        return "client/wishlist";
    }

    // TODO : login
    @GetMapping("/login")
    public String loginClient(Model model,
                              @CookieValue(required = false, name = "cookieEmail") String cookieEmail){
        UserRegisterDTO user =new UserRegisterDTO();
        user.setEmail(cookieEmail);
        boolean checked = false ;
        if (cookieEmail != null) {
            checked = true;
        }
        model.addAttribute("checked", checked);
        model.addAttribute("user", user);
        return "client/login";
    }

    @PostMapping("/login")
    public String postLoginClient(@ModelAttribute("user") UserRegisterDTO user,
                                  HttpSession httpSession, HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(required = false, name = "checked") Boolean isCheck){
        RespUserDTO userLogin = userService_itf.login(user);

        if (userLogin != null) {
            httpSession.setAttribute("user", userLogin);
            if (isCheck != null) { // kiem tra xem co nho tai khoan o o input khong
                // tao cookie tai may khach
                Cookie cookie = new Cookie("cookieEmail", user.getEmail());
                // set thoi gian song cua cookie
                cookie.setMaxAge(24*60*60);
                // reponst gui cookie len sever
                response.addCookie(cookie);
            } else {
                Cookie[] cookies = request.getCookies();
                for (Cookie c : cookies) {
                    if(c.getName().equals("cookieEmail")){
                        c.setMaxAge(0);
                        response.addCookie(c);
                        break;
                    }
                }
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }

    // TODO : Register
    @GetMapping("/register")
    public String registerClient(Model model){
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("user", user) ;
        return "client/register";
    }
    @PostMapping("/register")
    public String postRegisterClient(@Valid @ModelAttribute("user") UserRegisterDTO user,
                                      BindingResult result){
        List<RespUserDTO> list = userService_itf.findAll();
        boolean isCheck = !result.hasErrors() && user.getPassword().equals(user.getConfirmPassword());
        System.out.println(isCheck);
        if (isCheck) {
            for (RespUserDTO u : list) {
                if (u.getEmail().equals(user.getEmail())){
                    // email da ton tai , quay lai trang register
                    return "client/register";
                }
            }
            userService_itf.create(user);
            return "redirect:/login";
        }
        return "client/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/home";
    }

}
