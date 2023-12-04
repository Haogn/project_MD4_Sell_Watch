package rikkei.academy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.dao.OrderDAO_ITF;
import rikkei.academy.model.entity.Orders;
import rikkei.academy.util.OrderStatus;

import java.util.List;
@Service
public class OrdersService_IPML implements OrdersService_ITF {
    @Autowired
    private OrderDAO_ITF orderDAOItf ;
    @Override
    public List<Orders> findAllOrder() {
        return orderDAOItf.findAllOrder();
    }

    @Override
    public List<Orders> findAllOrderByIdUser(Integer idUser) {
        return orderDAOItf.findAllOrderByIdUser(idUser);
    }

    @Override
    public Boolean addOrder(Orders orders) {
        return orderDAOItf.addOrder(orders);
    }

    @Override
    public Orders findByIdOrder(Integer idOrder) {
        return orderDAOItf.findByIdOrder(idOrder);
    }

    @Override
    public Boolean cancelOrder(Integer cancel, Integer id) {
        return orderDAOItf.cancelOrder(cancel,id);
    }

    @Override
    public Boolean editStatusOrder(Integer newStatus, Integer id) {
        return orderDAOItf.editStatusOrder(newStatus,id);
    }
}
