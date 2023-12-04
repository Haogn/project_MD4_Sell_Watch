package rikkei.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rikkei.academy.util.OrderStatus;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Orders {
    private Integer order_id ;
    private User user ;
    private String consigneePhone ;
    private String consigneeName ;
    private String address ;
    private Integer status = 1  ;

    public OrderStatus getOrderStatus() {
        switch (status) {
            case 0:
                return OrderStatus.CHỜ_XÁC_NHẬN;
            case 1:
                return OrderStatus.XÁC_NHẬN;
            case 2:
                return OrderStatus.ĐÃ_GIAO;
            case 3:
                return OrderStatus.HUỶ;
            default:
                throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }
}
