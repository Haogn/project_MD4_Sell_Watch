package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import rikkei.academy.model.entity.Category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotEmpty(message = "Tên sản phẩm không được để rỗng")
    private String productName ;
    @NotNull(message = "Danh mục sản phẩm không được để rỗng")
    private Category category ;
    private String image;

    private Double price ;

    private String description ;

    private Integer stock;
    private Boolean status ;
}
