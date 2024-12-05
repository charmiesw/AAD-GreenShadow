package lk.ijse.gsbackend.controller;

import lk.ijse.gsbackend.customObj.LogsResponse;
import lk.ijse.gsbackend.dto.impl.LogsDTO;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.LogsNotFound;
import lk.ijse.gsbackend.service.LogsService;
import lk.ijse.gsbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("aad/gs/log")
@RequiredArgsConstructor
@CrossOrigin

public class LogsController {
    @Autowired
    private final LogsService logsService;

    @GetMapping("/next-code")
    public ResponseEntity<String> getNextLogCode() {
        String nextCode = logsService.generateNextLogCode();
        return ResponseEntity.ok(nextCode);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropCode") String cropCode,
            @RequestPart("staffId") String staffId
    ){
        try {

            System.out.println("logDate : " + logDate);
            System.out.println("logDetails : " + logDetails);
            System.out.println("observedImage : " + (observedImage != null ? observedImage.getOriginalFilename() : "null"));
            System.out.println("fieldCode : " + fieldCode);
            System.out.println("cropCode : " + cropCode);
            System.out.println("staffId : " + staffId);


            byte[] imageByteCollection1 = observedImage.getBytes();
            String base64Image = AppUtil.toBase64image(imageByteCollection1);

            LogsDTO buildLogDTO = new LogsDTO();
            buildLogDTO.setLogDate(logDate);
            buildLogDTO.setLogDetails(logDetails);
            buildLogDTO.setObservedImage(base64Image);
            buildLogDTO.setFieldCode(fieldCode);
            buildLogDTO.setCropCode(cropCode);
            buildLogDTO.setStaffId(staffId);

            logsService.saveLogs(buildLogDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteLog(@PathVariable("code") String logCode){
        try {
            logsService.deleteLogs(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LogsNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LogsResponse getSelectedLog(@PathVariable("code") String logCode) throws Exception {
        return logsService.getLogsByCode(logCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogsDTO> getAllLogs() throws Exception {
        return logsService.getAllLogs();
    }

    @PatchMapping(value = "/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLog(@PathVariable("code") String logCode,
                                          @RequestPart("logDate") String logDate,
                                          @RequestPart("logDetails") String logDetails,
                                          @RequestPart("observedImage") MultipartFile observedImage,
                                          @RequestPart("fieldCode") String fieldCode,
                                          @RequestPart("cropCode") String cropCode,
                                          @RequestPart("staffId") String staffId
    ){

        try {
            byte[] imageByteCollection = observedImage.getBytes();
            String updateBase64ProfilePic = AppUtil.toBase64image(imageByteCollection);

            LogsDTO updateLogDTO = new LogsDTO();
            updateLogDTO.setLogCode(logCode);
            updateLogDTO.setLogDate(logDate);
            updateLogDTO.setLogDetails(logDetails);
            updateLogDTO.setObservedImage(updateBase64ProfilePic);
            updateLogDTO.setFieldCode(fieldCode);
            updateLogDTO.setCropCode(cropCode);
            updateLogDTO.setStaffId(staffId);

            logsService.updateLogs(logCode, updateLogDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (LogsNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
