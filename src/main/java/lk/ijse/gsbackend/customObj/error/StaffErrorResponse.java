package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StaffErrorResponse implements StaffResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
