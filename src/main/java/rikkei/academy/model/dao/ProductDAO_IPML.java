package rikkei.academy.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
public class ProductDAO_IPML implements  ProductDAO_ITF{
    @Autowired
    private CategoryDAO_ITF categoryDAO_itf;

    @Override
    public List<Product> findAll() {
        Connection connection = null ;
        List<Product> list = new ArrayList<>();

        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ALL_PRODUCT()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Connection connection = null ;
        Product product = new Product();
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_By_ID_PRODUCT(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return product;
    }

    @Override
    public Boolean create(Product product) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_PRODUCT(?,?,?,?,?,?)}");
            callableStatement.setString(1, product.getProductName());
            callableStatement.setInt(2, product.getCategory().getCategoryId());
            callableStatement.setString(3, product.getImage());
            callableStatement.setDouble(4, product.getPrice());
            callableStatement.setString(5, product.getDescription());
            callableStatement.setInt(6, product.getStock());
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
    public Boolean update(Integer id, Product product) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, product.getProductName());
            callableStatement.setInt(2, product.getCategory().getCategoryId());
            callableStatement.setString(3, product.getImage());
            callableStatement.setDouble(4, product.getPrice());
            callableStatement.setString(5, product.getDescription());
            callableStatement.setInt(6, product.getStock());
            callableStatement.setBoolean(7, product.getStatus());
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

    @Override
//    todo : product theo category
    public List<Product> findAllProductByCategory(Integer id  ) {
        List<Product> list = new ArrayList<>();
        Connection connection = null ;

        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_PRODUCT_BY_CATEGORY(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }

        return list ;
    }

    @Override
    public Integer countProduct() {
        Connection connection = null;
        int count = 0 ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_COUNT_PRODUCT()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                count= resultSet.getInt("total_product");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }

        return count  ;
    }
    @Override
    public List<Product> findAllProductPage(int offset , int size ) {
        List<Product> list = new ArrayList<>();
        Connection connection = null  ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_PRODUCT_PAGE(?,?)}");
            callableStatement.setInt(1, offset);
            callableStatement.setInt(2, size);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                Category category = categoryDAO_itf.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list ;
    }

}
