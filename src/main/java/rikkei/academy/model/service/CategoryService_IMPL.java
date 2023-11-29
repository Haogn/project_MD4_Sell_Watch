package rikkei.academy.model.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.request.CategoryDTO;
import rikkei.academy.dto.response.RespCategoryDTO;
import rikkei.academy.model.dao.CategoryDAO_ITF;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService_IMPL implements CategoryService_ITF{
    @Autowired
    private CategoryDAO_ITF categoryDAO_itf;
    private ModelMapper modelMapper ;

    @Override
    public List<RespCategoryDTO> findAll() {
        modelMapper = new ModelMapper();
        List<Category> list = categoryDAO_itf.findAll();

        return list.stream()
                .map(item -> modelMapper.map(item,RespCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RespCategoryDTO findById(Integer id) {
        modelMapper = new ModelMapper();
        Category category = categoryDAO_itf.findById(id);
        return modelMapper.map(category, RespCategoryDTO.class);
    }

    @Override
    public Boolean create(CategoryDTO category) {
        modelMapper = new ModelMapper();
        return categoryDAO_itf.create(modelMapper.map(category, Category.class));
    }

    @Override
    public Boolean delete(Integer id) {
        return categoryDAO_itf.delete(id);
    }

    @Override
    public Boolean update( Integer id , CategoryDTO categoryDTO) {
        modelMapper = new ModelMapper();
        return categoryDAO_itf.update(id,modelMapper.map(categoryDTO, Category.class));
    }
}
