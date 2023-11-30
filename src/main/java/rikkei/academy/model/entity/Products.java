package rikkei.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Products {
    private Integer productId ;
    private String productName ;
    private Category category ;
    private String image;
    private Double price ;
    private String description ;
    private Integer quantity;
    private Boolean status ;
}
