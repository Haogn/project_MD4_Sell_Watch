package rikkei.academy.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.dto.request.ProductDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.service.CategoryService_ITF;
import rikkei.academy.model.service.ProductService_ITF;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
public class AdminProductController {
    @Autowired
    private ProductService_ITF productService_itf;

    @Autowired
    private CategoryService_ITF categoryService_itf;

    @Value("${path}")
    private String pathUpload ;

    // TODO : list produc
    @GetMapping("/list-product")
    public String showListProduct(Model model){
       List<RespProductDTO> list = productService_itf.findAll();
       model.addAttribute("list",list);
        return "admin/list-product";
    }

    @GetMapping("/add-product")
    public String createProduct(Model model ){
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("product", productDTO);
        List<RespCategoryDTO> list = categoryService_itf.findAll();
        model.addAttribute("category", list) ;
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String postCreateProduct(@Valid @ModelAttribute("product") ProductDTO productDTO,
                                    BindingResult result,
                                    @RequestParam("fileImage")MultipartFile multipartFile,
                                    Model model){

        if (result.hasErrors()) {
            List<RespCategoryDTO> categories = categoryService_itf.findAll();
            model.addAttribute("category", categories);
            return "admin/add-product";
        }

        if (multipartFile.isEmpty()) {
            model.addAttribute("fileError", "Please select a file to upload.");
            List<RespCategoryDTO> categories = categoryService_itf.findAll();
            model.addAttribute("category", categories);
            return "admin/add-product";
        }
        try {
            // Upload the file
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(pathUpload + fileName);
            multipartFile.transferTo(file);

            productDTO.setImage(fileName);

            productService_itf.create(productDTO);

            return "redirect:/list-product";
        } catch (IOException e) {
            // Handle IO exception
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Integer id ){
        Boolean isDelete = productService_itf.delete(id) ;
        if ( isDelete) {
            return "redirect:/list-product";
        }
        return "admin/list-product";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable Integer id , Model model) {
        RespProductDTO respProductDTO = productService_itf.findById(id) ;
        model.addAttribute("product", respProductDTO);
        List<RespCategoryDTO> list = categoryService_itf.findAll();
        model.addAttribute("category", list);
        return "admin/edit-product";
    }

    @PostMapping("/update-product/{id}")
    public String postEditProduct(@Valid @PathVariable Integer id ,
                                  @ModelAttribute("product") ProductDTO productDTO,
                                  BindingResult result, Model model ,
                                  @RequestParam("image-Update")MultipartFile multipartFile){
        if ( result.hasErrors()) {
            List<RespCategoryDTO> categories = categoryService_itf.findAll();
            model.addAttribute("category", categories);
            return "admin/edit-product";
        }

        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(pathUpload + fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            productDTO.setImage(fileName);
        } else {
            RespProductDTO respProductDTO = productService_itf.findById(id);
            productDTO.setImage(respProductDTO.getImage());
        }
        productService_itf.update(id, productDTO);
        return "redirect:/list-product";
    }
}
