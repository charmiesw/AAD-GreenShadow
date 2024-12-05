package lk.ijse.gsbackend.dto.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.dto.SuperDTO;
import lk.ijse.gsbackend.embedded.StaffFieldDetailsPK;
import lk.ijse.gsbackend.entity.SuperEntity;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StaffFieldDetailsDTO implements SuperDTO {
    private String fieldCode;
    private List<String> staffIds;
    private String assignedDate;
}
