package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CropErrorResponse implements CropResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
