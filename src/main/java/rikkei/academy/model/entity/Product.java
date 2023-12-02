package rikkei.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    private Integer productId ;
    private String productName ;
    private Category category ;
    private String image;
    private Double price ;
    private String description ;
    private Integer stock;
    private Boolean status ;
}
