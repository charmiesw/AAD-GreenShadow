package lk.ijse.gsbackend.customObj.error;

import lk.ijse.gsbackend.customObj.LogsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class LogsErrorResponse implements LogsResponse, Serializable {
    private int errorCode;
    private String errorMsg;
}
