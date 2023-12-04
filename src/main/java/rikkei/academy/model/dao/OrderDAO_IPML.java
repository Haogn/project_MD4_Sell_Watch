package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.entity.*;
import rikkei.academy.util.ConnectionBD;
import rikkei.academy.util.OrderStatus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderDAO_IPML implements OrderDAO_ITF{
    @Autowired
    private UserDAO_ITF userDAOItf ;
    @Override
    public List<Orders> findAllOrder() {
        Connection connection = null;
        List<Orders> list = new ArrayList<>();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ALL_ORDER()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Orders orders = new Orders() ;
                orders.setOrder_id(rs.getInt("order_id"));
                User user = userDAOItf.findById(rs.getInt("user_id"));
                orders.setUser(user);
                orders.setConsigneePhone(rs.getString("consignee_phone"));
                orders.setConsigneeName(rs.getString("consignee_name"));
                orders.setAddress(rs.getString("address"));
                orders.setStatus(rs.getInt("status"));
                list.add(orders) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Orders> findAllOrderByIdUser(Integer idUser) {
        Connection connection = null;
        List<Orders> list = new ArrayList<>();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ALL_ORDER_BY_ID_USER(?)}");
            callableStatement.setInt(1,idUser);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Orders orders = new Orders() ;
                orders.setOrder_id(rs.getInt("order_id"));
                User user = userDAOItf.findById(rs.getInt("user_id"));
                orders.setUser(user);
                orders.setConsigneePhone(rs.getString("consignee_phone"));
                orders.setConsigneeName(rs.getString("consignee_name"));
                orders.setAddress(rs.getString("address"));
                orders.setStatus(rs.getInt("status"));
                list.add(orders) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Boolean addOrder(Orders orders) {
        Connection connection = null;
        Boolean isCheck = false ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_ORDER(?,?,?,?,?)}");
            callableStatement.setInt(1, orders.getUser().getUserId());
            callableStatement.setString(2, orders.getConsigneePhone());
            callableStatement.setString(3, orders.getConsigneeName());
            callableStatement.setString(4, orders.getAddress());
            callableStatement.setInt(5, orders.getStatus());
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
    public Orders findByIdOrder(Integer idOrder) {
        Connection connection = null;
        Orders orders = new Orders() ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ORDER_BY_ID(?)}");
            callableStatement.setInt(1,idOrder);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                orders.setOrder_id(rs.getInt("order_id"));
                User user = userDAOItf.findById(rs.getInt("user_id"));
                orders.setUser(user);
                orders.setConsigneePhone(rs.getString("consignee_phone"));
                orders.setConsigneeName(rs.getString("consignee_name"));
                orders.setAddress(rs.getString("address"));
                orders.setStatus(rs.getInt("status"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public Boolean cancelOrder(Integer cancel,Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CANCEL_ORDER(?,?)}");
            callableStatement.setInt(1, cancel);
            callableStatement.setInt(2,id);

            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionBD.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public Boolean editStatusOrder(Integer newStatus,Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_EDIT_STATUS_ORDER(?,?)}");
            callableStatement.setInt(1, newStatus);
            callableStatement.setInt(2,id);

            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionBD.closeConnection(connection);
        }
        return isCheck;
    }
}
