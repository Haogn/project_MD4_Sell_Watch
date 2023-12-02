package rikkei.academy.model.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.request.ProductDTO;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.model.dao.ProductDAO_ITF;
import rikkei.academy.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService_IMPL implements ProductService_ITF{
    @Autowired
    private ProductDAO_ITF productDAO_itf;

    private ModelMapper modelMapper;
    @Override
    public List<RespProductDTO> findAll() {
        modelMapper = new ModelMapper();
        List<Product> list = productDAO_itf.findAll();
        return list.stream()
                .map(item -> modelMapper.map(item,RespProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RespProductDTO findById(Integer id) {
        modelMapper = new ModelMapper();
        Product product = productDAO_itf.findById(id);
        return modelMapper.map(product, RespProductDTO.class);
    }

    @Override
    public Boolean create(ProductDTO productDTO) {
        modelMapper = new ModelMapper();
        return productDAO_itf.create(modelMapper.map(productDTO, Product.class));
    }

    @Override
    public Boolean delete(Integer id) {
        return productDAO_itf.delete(id);
    }

    @Override
    public Boolean update(Integer id, ProductDTO productDTO) {
        modelMapper = new ModelMapper();
        return productDAO_itf.update(id, modelMapper.map(productDTO, Product.class));
    }

    @Override
    public List<RespProductDTO> findAllProductByCategory(Integer id   ) {
        modelMapper = new ModelMapper();
        List<Product> list = productDAO_itf.findAllProductByCategory(id) ;

        return list.stream()
                .map(item -> modelMapper.map(item, RespProductDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public Integer countProduct() {
        return productDAO_itf.countProduct();
    }

    @Override
    public List<RespProductDTO> findAllProductPage(int offset , int size ) {
        modelMapper = new ModelMapper() ;
        List<Product> list = productDAO_itf.findAllProductPage(offset,size) ;
        return list.stream()
                .map(item -> modelMapper.map(item, RespProductDTO.class))
                .collect(Collectors.toList());
    }
}
