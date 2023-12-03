package rikkei.academy.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.CategoryDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.service.UserService_ITF;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminUserController {
    @Autowired
    private UserService_ITF userService_itf ;
    // TODO : list User
    @GetMapping("/list-user")
    public String showListUser(Model model){
        List<RespUserDTO> list = userService_itf.findAll();
        model.addAttribute("list", list) ;
        return "admin/list-user";
    }

    @GetMapping("/add-user")
    public String createUser(Model model){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        return "admin/add-user";
    }

    @PostMapping("/add-user")
    public String postCreateUser(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, BindingResult result){
        Boolean isCheck ;
        if(!result.hasErrors()) {
            isCheck = userService_itf.create(userRegisterDTO);
            if(isCheck) {
                return "redirect:/list-category";
            }
        }
        return "admin/add-user";
    }

    @GetMapping("/lockAccount/{id}")
    public String lockAccount(@PathVariable Integer id ) {
        Boolean isCheck = userService_itf.update(id);
        if (isCheck) {
            return "redirect:/list-user";
        }
        return "admin/list-user" ;
    }

}
