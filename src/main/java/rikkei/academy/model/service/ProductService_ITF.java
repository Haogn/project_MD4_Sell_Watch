package rikkei.academy.model.service;

import rikkei.academy.dto.request.ProductDTO;
import rikkei.academy.dto.response.RespProductDTO;

import java.util.List;

public interface ProductService_ITF {
    List<RespProductDTO> findAll();
    RespProductDTO findById(Integer id) ;
    Boolean create(ProductDTO productDTO) ;
    Boolean delete(Integer id) ;
    Boolean update(Integer id , ProductDTO productDTO);
    List<RespProductDTO> findAllProductByCategory(Integer id  );
    Integer countProduct();
    List<RespProductDTO> findAllProductPage(int offset , int size );
}
