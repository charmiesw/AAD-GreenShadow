package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.error.CropErrorResponse;
import lk.ijse.gsbackend.customObj.CropResponse;
import lk.ijse.gsbackend.dao.CropDAO;
import lk.ijse.gsbackend.dao.FieldDAO;
import lk.ijse.gsbackend.dto.impl.CropDTO;
import lk.ijse.gsbackend.entity.impl.CropEntity;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.FieldNotFound;
import lk.ijse.gsbackend.service.CropService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceIMPL implements CropService {
    @Autowired
    private CropDAO cropDAO;
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public String generateNextCropId() {
        String lastCropId = cropDAO.findLastCropId();

        if (lastCropId == null || lastCropId.isEmpty()) {
            return "C001";
        }

        int newId = Integer.parseInt(lastCropId.substring(1)) + 1;

        return String.format("C%03d", newId);
    }

    @Override
    public void saveCrop(CropDTO cropDTO) throws Exception {
        if (cropDTO.getCropCode() == null || cropDTO.getCropCode().isEmpty()) {
            cropDTO.setCropCode(generateNextCropId());
        }

        CropEntity cropEntity = mapping.convertToCropEntity(cropDTO);
        CropEntity save = cropDAO.save(cropEntity);

        if (save == null) {
            throw new DataPersistFailedException("Cannot Save Crop..!!");
        }

    }

    @Override
    public void updateCrop(CropDTO cropDTO) throws Exception {
        Optional<CropEntity> tmpCrop = cropDAO.findById(cropDTO.getCropCode());

        if (!tmpCrop.isPresent()) {
            throw new FieldNotFound("Crop Not Found..!!");
        } else {
            tmpCrop.get().setCropCommonName(cropDTO.getCropCommonName());
            tmpCrop.get().setCropScientificName(cropDTO.getCropScientificName());
            tmpCrop.get().setCropImage(cropDTO.getCropImage());
            tmpCrop.get().setCategory(cropDTO.getCategory());
            tmpCrop.get().setCropSeason(cropDTO.getCropSeason());

            FieldEntity field = fieldDAO.getReferenceById(cropDTO.getFieldCode());

            if (field == null) {
                throw new FieldNotFound("Field Not Found..!!");
            } else {
                tmpCrop.get().setField(field);
            }
        }
    }

    @Override
    public void deleteCrop(String cropCode) throws Exception {
        Optional<CropEntity> findCode = cropDAO.findById(cropCode);

        if (!findCode.isPresent()) {
            throw new FieldNotFound("Crop Not Found..!!");
        } else {
            cropDAO.deleteById(cropCode);
        }
    }

    @Override
    public List<CropDTO> getAllCrops() throws Exception {
        List<CropEntity> allCrops = cropDAO.findAll();
        return mapping.convertCropToDTOList(allCrops);
    }

    @Override
    public CropResponse getCropByCode(String cropCode) throws Exception {
        if(cropDAO.existsById(cropCode)) {
            return mapping.convertToCropDTO(cropDAO.getReferenceById(cropCode));
        } else {
            return new CropErrorResponse(0, "Crop Not Found..!!");
        }
    }
}
