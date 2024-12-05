package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.error.FieldErrorResponse;
import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dao.FieldDAO;
import lk.ijse.gsbackend.dto.impl.FieldDTO;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.FieldNotFound;
import lk.ijse.gsbackend.service.FieldService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceIMPL implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public String generateNextFieldCode() {
        String lastFieldCode = fieldDAO.findLastFieldCode();

        if (lastFieldCode == null || lastFieldCode.isEmpty()) {
            return "F001";
        }

        int newFieldCode = Integer.parseInt(lastFieldCode.substring(1)) + 1;

        return String.format("F%03d", newFieldCode);
    }

    @Override
    public void saveField(FieldDTO fieldDTO) throws Exception {
        if (fieldDTO.getFieldCode() == null || fieldDTO.getFieldCode().isEmpty()) {
            fieldDTO.setFieldCode(generateNextFieldCode());
        }

        FieldEntity fieldEntity = mapping.convertToFieldEntity(fieldDTO);

        FieldEntity saveField = fieldDAO.save(fieldEntity);

        if (saveField == null) {
            throw new DataPersistFailedException("Cannot Save Field..!!");
        }
    }

    @Override
    public void updateField(FieldDTO fieldDTO) throws Exception {
        Optional<FieldEntity> tmpFieldEntity = fieldDAO.findById(fieldDTO.getFieldCode());

        if (!tmpFieldEntity.isPresent()) {
            throw new FieldNotFound("Field Not Found..!!");
        } else {
            tmpFieldEntity.get().setFieldName(fieldDTO.getFieldName());
            tmpFieldEntity.get().setExtentSize(fieldDTO.getExtentSize());
            tmpFieldEntity.get().setFieldImage1(fieldDTO.getFieldImage1());
            tmpFieldEntity.get().setFieldImage2(fieldDTO.getFieldImage2());
            tmpFieldEntity.get().setFieldLocation(fieldDTO.getFieldLocation());
        }
    }

    @Override
    public void deleteField(String fieldCode) throws Exception {
        Optional<FieldEntity> findCode = fieldDAO.findById(fieldCode);

        if (!findCode.isPresent()) {
            throw new FieldNotFound("Field Not Found..!!");
        } else {
            fieldDAO.deleteById(fieldCode);
        }
    }

    @Override
    public List<FieldDTO> getAllFields() throws Exception {
        List<FieldEntity> allFields = fieldDAO.findAll();
        return mapping.convertFieldToDTOList(allFields);
    }

    @Override
    public FieldResponse getFieldByCode(String fieldCode) throws Exception {
        if(fieldDAO.existsById(fieldCode)) {
            return mapping.convertToFieldDTO(fieldDAO.getReferenceById(fieldCode));
        } else {
            return new FieldErrorResponse(0, "Field Not Found..!!");
        }
    }
}
