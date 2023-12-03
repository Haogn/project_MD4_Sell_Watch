package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.entity.Cart;
import rikkei.academy.model.entity.CartItem;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.entity.Product;
import rikkei.academy.util.ConnectionBD;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private HttpSession httpSession;
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
                RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user") ;
                Cart cart = cartDAOItf.findByIdUser(userLogin.getUserId());
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
                cartItem.setQuantity(rs.getInt("quantity"));
                RespUserDTO userLogin = (RespUserDTO) httpSession.getAttribute("user") ;
                Cart cart = cartDAOItf.findByIdUser(userLogin.getUserId());
                cartItem.setCart(cart);
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
    public Boolean updateQty(Integer qty, Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_QUANTITY_CART_ITEM(?,?)}");
           callableStatement.setInt(1, qty );
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
    public Boolean delete(Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_DELETE_CART_ITEM(?)}");
            callableStatement.setInt(1,id);
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
