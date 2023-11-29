package rikkei.academy.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.entity.User;
import rikkei.academy.model.service.UserService_IMPL;
import rikkei.academy.util.Role;

import javax.servlet.http.HttpSession;
@Controller
public class AuthController {
    @Autowired
    private UserService_IMPL userServiceImpl ;
    @GetMapping("/loginAdmin")
    public String login(Model model){
        UserRegisterDTO admin = new UserRegisterDTO();
        model.addAttribute("admin",admin);
        return "admin/loginAdmin";
    }
    @PostMapping("/loginAdmin")
    public String postLogin(@ModelAttribute("admin") UserRegisterDTO admin, HttpSession httpSession){
        admin.setRole(Role.ADMIN);
        RespUserDTO userLogin = userServiceImpl.loginAdmin(admin) ;
        if ( userLogin != null) {
            httpSession.setAttribute("admin", userLogin);
            return "admin/index";
        }
        return "redirect:/loginAdmin";
    }
}
