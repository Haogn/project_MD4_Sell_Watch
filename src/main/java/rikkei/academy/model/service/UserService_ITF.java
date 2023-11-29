package rikkei.academy.model.service;

import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.entity.User;

import java.util.List;

public interface UserService_ITF  {
   List<RespUserDTO> findAll();
   RespUserDTO findById(Integer id) ;
   Boolean create(UserRegisterDTO user) ;
   Boolean delete(Integer id) ;
   Boolean update(Integer id );
   RespUserDTO login(UserRegisterDTO user) ;
   RespUserDTO loginAdmin(UserRegisterDTO user) ;
}
