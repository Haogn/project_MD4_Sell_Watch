package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryDTO {

    @NotEmpty(message = "Tên danh mục sản phầm không được để rỗng")
    private String categoryName;
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status ;
}
