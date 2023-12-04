package rikkei.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetail {
    private Integer odId ;
    private Orders orders;
    private Product product ;
    private Integer quantity ;
    private Double total ;
}
