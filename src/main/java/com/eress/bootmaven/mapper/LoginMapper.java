package com.eress.bootmaven.mapper;

import com.eress.bootmaven.model.AccessDTO;
import com.eress.bootmaven.model.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginMapper {

    UserDTO getUserInfo(UserDTO resUser) throws Exception;
    List<AccessDTO> getAccessInfo() throws Exception;
}
