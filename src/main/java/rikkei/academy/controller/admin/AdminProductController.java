package rikkei.academy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AdminProductController {
    // TODO : list produc
    @RequestMapping("/list-product")
    public String showListProduct(){
        return "admin/list-product";
    }
}
