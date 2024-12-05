package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserErrorResponse implements UserResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
