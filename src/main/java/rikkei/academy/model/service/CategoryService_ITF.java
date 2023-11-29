package rikkei.academy.model.service;

import rikkei.academy.dto.request.CategoryDTO;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.model.entity.Category;

import java.util.List;

public interface CategoryService_ITF {
    List<RespCategoryDTO> findAll();
    RespCategoryDTO findById(Integer id) ;
    Boolean create(CategoryDTO category) ;
    Boolean delete(Integer id) ;
    Boolean update( Integer id , CategoryDTO categoryDTO);
}
