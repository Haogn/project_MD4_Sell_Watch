package rikkei.academy.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.entity.User;
import rikkei.academy.model.service.UserService_IMPL;
import rikkei.academy.model.service.UserService_ITF;

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
    private UserService_IMPL userServiceImpl;
    // TODO : trang chu
    @RequestMapping("")
    public String index(){
        return "client/index";
    }

    // TODO : danh sach san pham
    @RequestMapping("/list-watch")
    public String showListWatch(){
        return "client/list-watch";
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
        RespUserDTO userLogin = userServiceImpl.login(user);

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
            return "client/index";
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
        List<RespUserDTO> list = userServiceImpl.findAll();
        boolean isCheck = !result.hasErrors() && user.getPassword().equals(user.getConfirmPassword());
        System.out.println(isCheck);
        if (isCheck) {
            for (RespUserDTO u : list) {
                if (u.getEmail().equals(user.getEmail())){
                    // email da ton tai , quay lai trang register
                    return "client/register";
                }
            }
            userServiceImpl.create(user);
            return "client/login";
        }
        return "client/register";
    }


    // TODO : giỏ hàng Cart
    @RequestMapping("/cart")
    public String cart(){
        return "client/cart";
    }

    // TODO : chi tiet don hang
    @RequestMapping("/watch-detail")
    public String showWatchDetail(){
        return "client/watch-detail";
    }
}
