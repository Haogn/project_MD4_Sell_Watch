package rikkei.academy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespCategoryDTO {
    private  Integer categoryId;
    private String categoryName;
    private Boolean status ;
}
