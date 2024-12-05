package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.UserResponse;
import lk.ijse.gsbackend.dto.impl.UserDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO) throws Exception;
    void updateUser(String email, UserDTO userDTO) throws Exception;
    void deleteUser(String email) throws Exception;
    List<UserDTO> getAllUsers() throws Exception;
    UserResponse getUserByEmail(String email) throws Exception;
}
