package lk.ijse.gsbackend.controller;

import lk.ijse.gsbackend.customObj.CropResponse;
import lk.ijse.gsbackend.dto.impl.CropDTO;
import lk.ijse.gsbackend.dto.impl.FieldDTO;
import lk.ijse.gsbackend.exception.CropNotFound;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.FieldNotFound;
import lk.ijse.gsbackend.service.CropService;
import lk.ijse.gsbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("aad/gs/crop")
@RequiredArgsConstructor
@CrossOrigin

public class CropController {
    @Autowired
    private final CropService cropService;

    @GetMapping("/next-code")
    public ResponseEntity<String> getNextCropCode() {
        String nextCropId = cropService.generateNextCropId();
        return ResponseEntity.ok(nextCropId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> saveCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart("category") String category,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("fieldCode") String fieldCode
    ){
//        try {
//            byte[] imageByteCollection1 = cropImage.getBytes();
//            String base64Image = AppUtil.toBase64image(imageByteCollection1);
//
//            CropDTO buildCropDTO = new CropDTO();
//            buildCropDTO.setCropCommonName(commonName);
//            buildCropDTO.setCropScientificName(scientificName);
//            buildCropDTO.setCropImage(base64Image);
//            buildCropDTO.setCategory(category);
//            buildCropDTO.setCropSeason(cropSeason);
//            buildCropDTO.setFieldCode(fieldCode);
//
//            cropService.saveCrop(buildCropDTO);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (DataPersistFailedException e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        try {
            byte[] imageByteCollection1 = cropImage.getBytes();
            String base64Image = AppUtil.toBase64image(imageByteCollection1);

            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCropCode(buildCropDTO.getCropCode());
            buildCropDTO.setCropCommonName(commonName);
            buildCropDTO.setCropScientificName(scientificName);
            buildCropDTO.setCropImage(base64Image);
            buildCropDTO.setCategory(category);
            buildCropDTO.setCropSeason(cropSeason);
            buildCropDTO.setFieldCode(fieldCode);

            cropService.saveCrop(buildCropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("code") String cropCode){
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable("code") String cropCode) throws Exception {
        return cropService.getCropByCode(cropCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops() throws Exception {
        return cropService.getAllCrops();
    }

    @PatchMapping(value = "/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("code") String cropCode,
                                           @RequestPart("commonName") String commonName,
                                           @RequestPart("scientificName") String scientificName,
                                           @RequestPart("cropImage") MultipartFile cropImage,
                                           @RequestPart("category") String category,
                                           @RequestPart("cropSeason") String cropSeason,
                                           @RequestPart("fieldCode") String fieldCode
    ){

        try {
            byte[] imageByteCollection = cropImage.getBytes();
            String updateBase64ProfilePic = AppUtil.toBase64image(imageByteCollection);

            CropDTO updateCropDTO = new CropDTO();
            updateCropDTO.setCropCode(cropCode);
            updateCropDTO.setCropCommonName(commonName);
            updateCropDTO.setCropScientificName(scientificName);
            updateCropDTO.setCategory(category);
            updateCropDTO.setCropImage(updateBase64ProfilePic);
            updateCropDTO.setCropSeason(cropSeason);
            updateCropDTO.setFieldCode(fieldCode);

            cropService.updateCrop(updateCropDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (FieldNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
