package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FieldDTO implements SuperDTO, FieldResponse {
    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private double extentSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<CropDTO> cropDTO = new ArrayList<>();
    private List<StaffDTO> staffDTO = new ArrayList<>();
    private List<LogsDTO> logsDTO = new ArrayList<>();
    private List<EquipmentDTO> equipmentDTO = new ArrayList<>();
}
