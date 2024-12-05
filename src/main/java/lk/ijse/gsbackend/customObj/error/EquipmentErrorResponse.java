package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EquipmentErrorResponse implements EquipmentResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
