package rikkei.academy.model.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.entity.User;
import rikkei.academy.util.ConnectionBD;
import rikkei.academy.util.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDAO_IMPL implements UserDAO_ITF{
    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Connection connection = null ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_ALL_USER");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(Role.valueOf(rs.getString("role")));
                list.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return list;
    }

    @Override
    public User findById(Integer id) {
        User user = new User();
        Connection connection = null ;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_By_ID_USER(?)");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(Role.valueOf(rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionBD.closeConnection(connection);
        }
        return user;
    }

    @Override
    public Boolean create(User user) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_USER(?,?,?,?)}");
            callableStatement.setString(1,user.getUserName());
            callableStatement.setString(2,user.getEmail());
            callableStatement.setString(3,user.getPassword());
            callableStatement.setString(4, user.getRole().toString());
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
            CallableStatement callableStatement = connection.prepareCall("{call PROC_DELETE_USER(?)}");
            callableStatement.setInt(1, id);
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
    public Boolean Update(Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionBD.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_USER(?)}");
            callableStatement.setInt(1, id);
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
    public User login(User user) {
        List<User> list =findAll();
        String passBCrypt = null;
        for (User u : list) {
            if (u.getEmail().equals(user.getEmail())){
                passBCrypt = u.getPassword();
                if (BCrypt.checkpw(user.getPassword(), passBCrypt) && user.getRole() == Role.USER){
                    return u;
                }
            }
        }
        return null;
    }

    @Override
    public User loginAdmin(User user) {
        List<User> list =findAll();
        String passBCrypt = null;
        for (User u : list) {
            if (u.getEmail().equals(user.getEmail())){
                passBCrypt = u.getPassword();
                if (BCrypt.checkpw(user.getPassword(), passBCrypt) && user.getRole() == Role.ADMIN){
                    return u;
                }
            }
        }
        return null;
    }
}
