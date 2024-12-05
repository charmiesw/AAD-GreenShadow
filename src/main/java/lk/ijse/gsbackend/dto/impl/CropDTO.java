package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.CropResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CropDTO implements SuperDTO, CropResponse {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    private String fieldCode;
    private List<LogsDTO> logsDTO;
}
