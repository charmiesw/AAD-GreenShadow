package lk.ijse.gsbackend.controller;

import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dto.impl.FieldDTO;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.FieldNotFound;
import lk.ijse.gsbackend.service.FieldService;
import lk.ijse.gsbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("aad/gs/field")
@RequiredArgsConstructor
@CrossOrigin

public class FieldController {
    @Autowired
    private final FieldService fieldService;

    @GetMapping("/next-code")
    public ResponseEntity<String> getNextFieldCode() {
        String nextCode = fieldService.generateNextFieldCode();
        return ResponseEntity.ok(nextCode);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2
    ){
        try {
            byte[] imageByteCollection1 = fieldImage1.getBytes();
            String base64Image_1 = AppUtil.toBase64image(imageByteCollection1);

            byte[] imageByteCollection2 = fieldImage2.getBytes();
            String base64Image_2 = AppUtil.toBase64image(imageByteCollection2);

            FieldDTO buildFieldDTO = new FieldDTO();
            buildFieldDTO.setFieldCode(buildFieldDTO.getFieldCode());
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setFieldLocation(fieldLocation);
            buildFieldDTO.setExtentSize(Double.parseDouble(extentSize));
            buildFieldDTO.setFieldImage1(base64Image_1);
            buildFieldDTO.setFieldImage2(base64Image_2);

            fieldService.saveField(buildFieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteField(@PathVariable("code") String fieldCode){
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable("code") String fieldCode) throws Exception {
        return fieldService.getFieldByCode(fieldCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() throws Exception {
        return fieldService.getAllFields();
    }

    @PatchMapping(value = "/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("code") String fieldCode,
                                            @RequestPart("fieldName") String fieldName,
                                            @RequestPart("fieldLocation") String location,
                                            @RequestPart("extentSize") String extentSize,
                                            @RequestPart("fieldImage1") MultipartFile image1,
                                            @RequestPart("fieldImage2") MultipartFile image2
    ){

        try {
            byte[] imageByteCollection1 = image1.getBytes();
            String updateBase64ProfilePic1 = AppUtil.toBase64image(imageByteCollection1);

            byte[] imageByteCollection2 = image2.getBytes();
            String updateBase64ProfilePic2 = AppUtil.toBase64image(imageByteCollection2);

            FieldDTO updateFieldDTO = new FieldDTO();
            updateFieldDTO.setFieldCode(fieldCode);
            updateFieldDTO.setFieldName(fieldName);
            updateFieldDTO.setFieldLocation(location);
            updateFieldDTO.setExtentSize(Double.parseDouble(extentSize));
            updateFieldDTO.setFieldImage1(updateBase64ProfilePic1);
            updateFieldDTO.setFieldImage2(updateBase64ProfilePic2);

            fieldService.updateField(updateFieldDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (FieldNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
