package rikkei.academy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.dao.OrderDetailDAO_ITF;
import rikkei.academy.model.entity.OrderDetail;

import java.util.List;
@Service
public class OrderDetailService_IMPL implements OrderDetailService_ITF{
    @Autowired
    private OrderDetailDAO_ITF orderDetailDAO_itf ;
    @Override
    public List<OrderDetail> findAllByIdOrder(Integer idOder) {
        return orderDetailDAO_itf.findAllByIdOrder(idOder);
    }

    @Override
    public Boolean create(OrderDetail detail) {
        return orderDetailDAO_itf.create(detail);
    }

    @Override
    public OrderDetail findById(Integer idOd) {
        return orderDetailDAO_itf.findById(idOd);
    }
}
