package lk.ijse.gsbackend.dto.impl;

import lk.ijse.gsbackend.customObj.LogsResponse;
import lk.ijse.gsbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LogsDTO implements SuperDTO, LogsResponse {
    private String logCode;
    private String  logDate;
    private String logDetails;
    private String observedImage;
    private String fieldCode;
    private String cropCode;
    private String staffId;
}
