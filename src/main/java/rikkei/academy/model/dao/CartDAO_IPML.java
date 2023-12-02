package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.entity.Cart;
import rikkei.academy.model.entity.CartItem;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.entity.User;
import rikkei.academy.util.ConnectionBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class CartDAO_IPML implements CartDAO_ITF{

    @Autowired
    private UserDAO_ITF userDAOItf;
    @Override
    public Boolean addCart(Cart cart) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_CART(?)}");
            callableStatement.setInt(1, cart.getUser().getUserId());
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
    public Cart findByIdUser(Integer id) {
        Connection connection = null;
        Cart cart = new Cart() ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_BY_ID_CART(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                cart.setCart_id(rs.getInt("cart_id"));
                User user = userDAOItf.findById(rs.getInt("user_id")) ;
                cart.setUser(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return cart;
    }
}
