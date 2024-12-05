package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class FieldErrorResponse implements FieldResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
