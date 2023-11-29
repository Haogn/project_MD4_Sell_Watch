package rikkei.academy.model.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.dto.request.UserRegisterDTO;
import rikkei.academy.dto.response.RespUserDTO;
import rikkei.academy.model.dao.UserDAO_IMPL;
import rikkei.academy.model.dao.UserDAO_ITF;
import rikkei.academy.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService_IMPL implements UserService_ITF{
    private ModelMapper modelMapper ;

    @Autowired
    private UserDAO_ITF userDAO_itf;

    @Override
    public List<RespUserDTO> findAll() {
        List<User> list = userDAO_itf.findAll();
         modelMapper = new ModelMapper();
        return list.stream()
                .map(item -> modelMapper.map(item,RespUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RespUserDTO findById(Integer id) {
         modelMapper = new ModelMapper() ;
         User user = userDAO_itf.findById(id);
         // trc : đối tượng cần ép kiểu ==== sau : kiểu dữ liệu cần ép
        return modelMapper.map(user, RespUserDTO.class);
    }

    @Override
    public Boolean create(UserRegisterDTO user) {
        String BCryptPass = user.getPassword();
        BCryptPass = BCrypt.hashpw(BCryptPass,BCrypt.gensalt(12));
        user.setPassword(BCryptPass);
        modelMapper = new ModelMapper();
        return userDAO_itf.create(modelMapper.map(user, User.class));
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public Boolean update(Integer id) {
        return userDAO_itf.Update(id);
    }

    @Override
    public RespUserDTO login(UserRegisterDTO user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, RespUserDTO.class);
    }

    @Override
    public RespUserDTO loginAdmin(UserRegisterDTO user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, RespUserDTO.class);
    }
}