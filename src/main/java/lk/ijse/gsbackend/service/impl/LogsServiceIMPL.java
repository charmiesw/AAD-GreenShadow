package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.error.LogsErrorResponse;
import lk.ijse.gsbackend.customObj.LogsResponse;
import lk.ijse.gsbackend.dao.CropDAO;
import lk.ijse.gsbackend.dao.FieldDAO;
import lk.ijse.gsbackend.dao.LogsDAO;
import lk.ijse.gsbackend.dao.StaffDAO;
import lk.ijse.gsbackend.dto.impl.LogsDTO;
import lk.ijse.gsbackend.entity.impl.CropEntity;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.entity.impl.LogsEntity;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lk.ijse.gsbackend.exception.*;
import lk.ijse.gsbackend.service.LogsService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogsServiceIMPL implements LogsService {
    @Autowired
    private LogsDAO logsDAO;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private CropDAO cropDAO;

    @Override
    public String generateNextLogCode() {
        String lastLogCode = logsDAO.findLastLogCode();

        if (lastLogCode == null || lastLogCode.isEmpty()) {
            return "L001";
        }

        int newLogCode = Integer.parseInt(lastLogCode.substring(1)) + 1;

        return String.format("L%03d", newLogCode);
    }

    @Override
    public void saveLogs(LogsDTO logsDTO) throws Exception {
        if (logsDTO.getLogCode() == null || logsDTO.getLogCode().isEmpty()) {
            logsDTO.setLogCode(generateNextLogCode());
        }

        System.out.println(logsDTO);

        StaffEntity staffEntity = staffDAO.findById(logsDTO.getStaffId())
                .orElseThrow(() -> new StaffNotFound("Staff Not Found..!!"));
        CropEntity cropEntity = cropDAO.findById(logsDTO.getCropCode())
                .orElseThrow(() -> new CropNotFound("Crop Not Found..!!"));
        FieldEntity fieldEntity = fieldDAO.findById(logsDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFound("Field Not Found..!!"));

        LogsEntity logsEntity = mapping.convertToLogsEntity(logsDTO);
        logsEntity.setStaff(staffEntity);
        logsEntity.setCrop(cropEntity);
        logsEntity.setField(fieldEntity);

        LogsEntity saveLog = logsDAO.save(logsEntity);

        if (saveLog == null) {
            throw new DataPersistFailedException("Cannot Save Log..!!");
        }
    }

    @Override
    public void updateLogs(String logCode, LogsDTO logsDTO) throws Exception {
        Optional<LogsEntity> tmpLogEntity = logsDAO.findById(logCode);

        if (!tmpLogEntity.isPresent()) {
            throw new LogsNotFound("Log Not Found..!!");
        } else {
            tmpLogEntity.get().setLogDate(logsDTO.getLogDate());
            tmpLogEntity.get().setLogDetails(logsDTO.getLogDetails());
            tmpLogEntity.get().setObservedImage(logsDTO.getObservedImage());

            StaffEntity staff = staffDAO.getReferenceById(logsDTO.getStaffId());

            if (staff == null) {
                throw new StaffNotFound("Staff Not Found..!!");
            } else {
                tmpLogEntity.get().setStaff(staff);
            }

            FieldEntity field = fieldDAO.getReferenceById(logsDTO.getFieldCode());
            if (field == null) {
                throw new FieldNotFound("Field Not Found..!!");
            } else {
                tmpLogEntity.get().setField(field);
            }

            CropEntity crop = cropDAO.getReferenceById(logsDTO.getCropCode());

            if (crop == null) {
                throw new FieldNotFound("Crop Not Found..!!");
            } else {
                tmpLogEntity.get().setCrop(crop);
            }
        }
    }

    @Override
    public void deleteLogs(String logCode) throws Exception {
        Optional<LogsEntity> findCode = logsDAO.findById(logCode);

        if (!findCode.isPresent()) {
            throw new LogsNotFound("Log Not Found..!!");
        } else {
            logsDAO.deleteById(logCode);
        }
    }

    @Override
    public List<LogsDTO> getAllLogs() throws Exception {
        List<LogsEntity> allLogs = logsDAO.findAll();
        List<LogsDTO> logsDTOList = new ArrayList<>();

        for (LogsEntity logs : allLogs) {
            LogsDTO dto = mapping.convertToLogsDTO(logs);
            dto.setStaffId(logs.getStaff().getId());
            logsDTOList.add(dto);
        }

        return logsDTOList;
    }

    @Override
    public LogsResponse getLogsByCode(String logCode) throws Exception {
        Optional<LogsEntity> optionalLogs = logsDAO.findById(logCode);

        if (optionalLogs.isPresent()) {
            LogsEntity logsEntity = optionalLogs.get();
            LogsDTO dto = mapping.convertToLogsDTO(logsEntity);
            dto.setStaffId(logsEntity.getStaff().getId());
            return dto;
        } else {
            return new LogsErrorResponse(0, "Log Not Found..!!");
        }
    }
}
