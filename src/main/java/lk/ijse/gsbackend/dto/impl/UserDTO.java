package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.UserResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserDTO implements SuperDTO, UserResponse {
    private String email;
    private String password;
    private String role;
}
