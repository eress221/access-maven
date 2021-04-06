package com.eress.bootmaven.service;

import com.eress.bootmaven.model.AccessDTO;
import com.eress.bootmaven.model.UserDTO;

import java.util.List;

public interface LoginService {

    UserDTO getUserInfo(UserDTO reqUser);
    String loginCheck(UserDTO reqUser, UserDTO resUser);
    List<AccessDTO> getAccessInfo();
}
