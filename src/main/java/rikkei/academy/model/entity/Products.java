package rikkei.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Products {
    private Integer productId ;
    private String productName ;
    private Category category ;
    private Double price ;
    private Double newPrice ;
    private String description ;
    private Integer quantity;
    private Boolean status ;
}
