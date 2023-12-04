package rikkei.academy.model.dao;

import rikkei.academy.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO_ITF {
    List<OrderDetail> findAllByIdOrder(Integer idOder);
    Boolean create(OrderDetail detail) ;
    OrderDetail findById(Integer idOd) ;
}
