package rikkei.academy.model.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.request.ProductDTO;
import rikkei.academy.dto.response.RespProductDTO;
import rikkei.academy.model.dao.ProductDAO_IPML;
import rikkei.academy.model.dao.ProductDAO_ITF;
import rikkei.academy.model.entity.Products;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService_IMPL implements ProductService_ITF{
    @Autowired
    private ProductDAO_ITF productDAO_itf;

    @Autowired
    private ProductDAO_IPML productDAO_ipml ;

    private ModelMapper modelMapper;
    @Override
    public List<RespProductDTO> findAll() {
        modelMapper = new ModelMapper();
        List<Products> list = productDAO_itf.findAll();
        return list.stream()
                .map(item -> modelMapper.map(item,RespProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RespProductDTO findById(Integer id) {
        modelMapper = new ModelMapper();
        Products products = productDAO_itf.findById(id);
        return modelMapper.map(products, RespProductDTO.class);
    }

    @Override
    public Boolean create(ProductDTO productDTO) {
        modelMapper = new ModelMapper();
        return productDAO_itf.create(modelMapper.map(productDTO,Products.class));
    }

    @Override
    public Boolean delete(Integer id) {
        return productDAO_itf.delete(id);
    }

    @Override
    public Boolean update(Integer id, ProductDTO productDTO) {
        modelMapper = new ModelMapper();
        return productDAO_itf.update(id, modelMapper.map(productDTO, Products.class));
    }

    public List<RespProductDTO> findAllProductByCategory(Integer id ) {
        modelMapper = new ModelMapper();
        List<Products> list = productDAO_ipml.findAllProductByCategory(id) ;

        return list.stream()
                .map(item -> modelMapper.map(item, RespProductDTO.class))
                .collect(Collectors.toList());
    }
    public Integer countProduct() {
        return productDAO_ipml.countProduct();
    }

    public List<RespProductDTO> findAllProductPage(int offset , int size ) {
        modelMapper = new ModelMapper() ;
        List<Products> list = productDAO_ipml.findAllProductPage(offset,size) ;
        return list.stream()
                .map(item -> modelMapper.map(item, RespProductDTO.class))
                .collect(Collectors.toList());
    }
}
