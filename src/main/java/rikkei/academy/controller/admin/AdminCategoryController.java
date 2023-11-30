package rikkei.academy.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.CategoryDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.service.CategoryService_ITF;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminCategoryController {
    @Autowired
    private CategoryService_ITF categoryService_itf;
    @RequestMapping("/list-category")
    public String showListCategory(Model model){
        List<RespCategoryDTO> list = categoryService_itf.findAll();
        model.addAttribute("list", list);
        return "admin/list-category";
    }

    @GetMapping("/add-category")
    public String createCategory(Model model){
        CategoryDTO category = new CategoryDTO();
        model.addAttribute("category", category);
        return "admin/add-category";
    }

    @PostMapping("/add-category")
    public String postCreateCategory(@Valid @ModelAttribute("category") CategoryDTO category, BindingResult result){
        Boolean isCheck ;
        if(!result.hasErrors()) {
           isCheck = categoryService_itf.create(category);
            if(isCheck) {
                return "redirect:/list-category";
            }
        }
        return "admin/add-category";
    }
    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Integer id){
        Boolean isDelete = categoryService_itf.delete(id);
        if (isDelete) {
            return "redirect:/list-category";
        }
        return "admin/list-category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable Integer id , Model model) {
        RespCategoryDTO category = categoryService_itf.findById(id);
        model.addAttribute("category", category);
        return "admin/edit-category";
    }

    @PostMapping("/update-category/{id}")
    public String postEditCategory(@PathVariable Integer id ,
                                   @ModelAttribute("category") CategoryDTO categoryDTO){
        categoryService_itf.update(id, categoryDTO);
        return "redirect:/list-category";
    }
}
