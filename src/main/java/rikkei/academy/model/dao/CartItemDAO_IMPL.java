package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.entity.Cart;
import rikkei.academy.model.entity.CartItem;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.entity.Product;
import rikkei.academy.util.ConnectionBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartItemDAO_IMPL implements CartItemDAO_ITF{
    @Autowired
    private ProductDAO_ITF productDAO_itf ;
    @Autowired
    private CartDAO_ITF cartDAOItf;
    @Override
    public List<CartItem> findAll(Integer idCart) {
        Connection connection = null;
        List<CartItem> list = new ArrayList<>();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CART_ITEM_BY_CART(?)}");
            callableStatement.setInt(1, idCart);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                Product product = productDAO_itf.findById(rs.getInt("product_id"));
                cartItem.setProduct(product) ;
                cartItem.setQuantity(rs.getInt("quantity"));
                Cart cart = cartDAOItf.findByIdUser(rs.getInt("user_id"));
                cartItem.setCart(cart);
                list.add(cartItem) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list ;
    }

    @Override
    public CartItem findById(Integer id) {
        Connection connection = null;
        CartItem cartItem = new CartItem() ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_BY_ID_CART_ITEM(?)}");
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                cartItem.setId(rs.getInt("id"));
                Product product = productDAO_itf.findById(rs.getInt("product_id"));
                cartItem.setProduct(product) ;
                Cart cart = cartDAOItf.findByIdUser(rs.getInt("user_id"));
                cartItem.setCart(cart);
                cartItem.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return cartItem ;
    }

    @Override
    public Boolean create(CartItem item) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_CART_ITEM(?,?,?)}");
            callableStatement.setInt(1, item.getProduct().getProductId());
            callableStatement.setInt(2, item.getQuantity());
            callableStatement.setInt(3,item.getCart().getCart_id());
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
    public Boolean update(CartItem item, Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_CART_ITEM(?,?,?)}");
            callableStatement.setInt(1,item.getProduct().getProductId());
            callableStatement.setInt(2, item.getQuantity());
            callableStatement.setInt(3,item.getCart().getCart_id());
            callableStatement.setInt(4,id);
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
    public void delete(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_DELETE_CART_ITEM(?)}");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionBD.closeConnection(connection);
        }
    }
}
