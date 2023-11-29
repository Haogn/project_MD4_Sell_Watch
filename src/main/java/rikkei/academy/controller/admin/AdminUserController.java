package rikkei.academy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminUserController {
    // TODO : list User
    @RequestMapping("/list-user")
    public String showListUser(){
        return "admin/list-user";
    }
}
