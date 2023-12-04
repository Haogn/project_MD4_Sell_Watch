package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.entity.Orders;
import rikkei.academy.model.entity.OrderDetail;
import rikkei.academy.model.entity.Product;
import rikkei.academy.util.ConnectionBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderDetailDAO_IMPL implements OrderDetailDAO_ITF{
    @Autowired
    private OrderDAO_ITF orderDAOItf ;
    @Autowired
    private ProductDAO_ITF productDAO_itf;
    @Override
    public List<OrderDetail> findAllByIdOrder(Integer idOder) {
        Connection connection = null;
        List<OrderDetail> list = new ArrayList<>();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ALL_BY_ORDER_ID(?)}");
            callableStatement.setInt(1,idOder);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail() ;
                orderDetail.setOdId(rs.getInt("od_id"));
                Orders orders = orderDAOItf.findByIdOrder(rs.getInt("order_id")) ;
                orderDetail.setOrders(orders);
                Product product = productDAO_itf.findById(rs.getInt("product_id")) ;
                orderDetail.setProduct(product);
                orderDetail.setQuantity(rs.getInt(rs.getInt("quantity")));
                orderDetail.setTotal(rs.getDouble("total"));
                list.add(orderDetail) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Boolean create(OrderDetail detail) {
        Connection connection = null;
        Boolean isCheck = false ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_ORDER_DETAIL(?,?,?,?)}");
            callableStatement.setInt(1,detail.getOrders().getOrder_id());
            callableStatement.setInt(2,detail.getProduct().getProductId());
            callableStatement.setInt(3, detail.getQuantity());
            callableStatement.setDouble(4,detail.getTotal());

            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public OrderDetail findById(Integer idOd) {
        Connection connection = null;
        OrderDetail newOrderDetail = new OrderDetail();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ORDER_DETAIL_BY_ID(?)}");
            callableStatement.setInt(1,idOd);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                newOrderDetail.setOdId(rs.getInt("od_id"));
                Orders orders = orderDAOItf.findByIdOrder(rs.getInt("order_id")) ;
                newOrderDetail.setOrders(orders);
                Product product = productDAO_itf.findById(rs.getInt("product_id")) ;
                newOrderDetail.setProduct(product);
                newOrderDetail.setQuantity(rs.getInt(rs.getInt("quantity")));
                newOrderDetail.setTotal(rs.getDouble("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return newOrderDetail;
    }
}
