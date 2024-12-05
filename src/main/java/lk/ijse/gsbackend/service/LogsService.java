package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.LogsResponse;
import lk.ijse.gsbackend.dto.impl.LogsDTO;

import java.util.List;

public interface LogsService {
    String generateNextLogCode();
    void saveLogs(LogsDTO logsDTO) throws Exception;
    void updateLogs(String logCode, LogsDTO logsDTO) throws Exception;
    void deleteLogs(String logCode) throws Exception;
    List<LogsDTO> getAllLogs() throws Exception;
    LogsResponse getLogsByCode(String logCode) throws Exception;
}
