package rikkei.academy.model.dao;

import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.model.entity.User;

import java.util.List;

public interface UserDAO_ITF {
    List<User> findAll();
    User findById(Integer id);
    Boolean create(User user);
    Boolean delete(Integer id);
    Boolean Update(Integer id) ;
     User login(User user) ;
     User loginAdmin(User user);
}
