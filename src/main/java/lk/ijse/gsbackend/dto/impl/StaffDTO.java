package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.StaffResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lk.ijse.gsbackend.enums.Gender;
import lk.ijse.gsbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String buildingName;
    private String street;
    private String city;
    private String district;
    private String postalCode;
    private String designation;
    private Role role;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joinedDate;
    private List<VehicleDTO> vehicleDTOS;
    private List<FieldDTO> fieldDTOS;
    private List<LogsDTO> logsDTOS;
    private List<EquipmentDTO> equipmentDTOS;
}
