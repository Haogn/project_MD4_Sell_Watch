package rikkei.academy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rikkei.academy.model.entity.Category;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespProductDTO {
    private Integer productId ;
    private String productName ;
    private Category category ;
    private String image;
    private Double price ;
    private String description ;
    private Integer quantity;
    private Boolean status ;
}
