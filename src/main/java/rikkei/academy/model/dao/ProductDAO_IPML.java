package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.entity.Category;
import rikkei.academy.model.entity.Products;
import rikkei.academy.util.ConnectionBD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDAO_IPML implements  ProductDAO_ITF{
    @Autowired
    private CategoryDAO_ITF categoryDAO_itf;

    @Override
    public List<Products> findAll() {
        Connection connection = null ;
        List<Products> list = new ArrayList<>();

        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ALL_PRODUCT()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Products products = new Products();
                products.setProductId(rs.getInt("product_id"));
                products.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                products.setCategory(category);
                products.setImage(rs.getString("image"));
                products.setPrice(rs.getDouble("price"));
                products.setDescription(rs.getString("description"));
                products.setQuantity(rs.getInt("quantity"));
                products.setStatus(rs.getBoolean("status"));
                list.add(products);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Products findById(Integer id) {
        Connection connection = null ;
        Products products = new Products();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_By_ID_PRODUCT(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                products.setProductId(rs.getInt("product_id"));
                products.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                products.setCategory(category);
                products.setImage(rs.getString("image"));
                products.setPrice(rs.getDouble("price"));
                products.setDescription(rs.getString("description"));
                products.setQuantity(rs.getInt("quantity"));
                products.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Boolean create(Products products) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_PRODUCT(?,?,?,?,?,?)}");
            callableStatement.setString(1,products.getProductName());
            callableStatement.setInt(2,products.getCategory().getCategoryId());
            callableStatement.setString(3,products.getImage());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setString(5,products.getDescription());
            callableStatement.setInt(6, products.getQuantity());
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
            CallableStatement callableStatement = connection.prepareCall("{call PROC_DELETE_PRODUCT(?)}");
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

    @Override
    public Boolean update(Integer id, Products products) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1,products.getProductName());
            callableStatement.setInt(2,products.getCategory().getCategoryId());
            callableStatement.setString(3,products.getImage());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setString(5,products.getDescription());
            callableStatement.setInt(6, products.getQuantity());
            callableStatement.setBoolean(7,products.getStatus());
            callableStatement.setInt(8,id );
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
