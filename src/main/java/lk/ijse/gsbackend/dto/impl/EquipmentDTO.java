package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.EquipmentResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lk.ijse.gsbackend.enums.Equipment;
import lk.ijse.gsbackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    private String name;
    private Equipment type;
    private Status status;
    private String assignedStaffId;
    private String assignedFieldCode;
}
