package rikkei.academy.model.dao;

import rikkei.academy.model.entity.Orders;
import rikkei.academy.util.OrderStatus;

import java.util.List;

public interface OrderDAO_ITF {
    List<Orders> findAllOrder() ;
    List<Orders> findAllOrderByIdUser(Integer idUser) ;
    Boolean addOrder(Orders orders) ;
    Orders findByIdOrder(Integer idOrder) ;
    Boolean cancelOrder(Integer cancel, Integer id) ;
    Boolean editStatusOrder(Integer newStatus,  Integer id) ;
}
