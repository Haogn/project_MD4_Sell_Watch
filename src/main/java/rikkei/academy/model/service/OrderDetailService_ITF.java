package rikkei.academy.model.service;

import rikkei.academy.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService_ITF {
    List<OrderDetail> findAllByIdOrder(Integer idOder);
    Boolean create(OrderDetail detail) ;
    OrderDetail findById(Integer idOd) ;
}
