package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.CropResponse;
import lk.ijse.gsbackend.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    String generateNextCropId();
    void saveCrop(CropDTO cropDTO) throws Exception;
    void updateCrop(CropDTO cropDTO) throws Exception;
    void deleteCrop(String cropCode) throws Exception;
    List<CropDTO> getAllCrops() throws Exception;
    CropResponse getCropByCode(String cropCode) throws Exception;
}
