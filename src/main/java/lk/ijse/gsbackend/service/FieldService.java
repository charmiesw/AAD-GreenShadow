package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    String generateNextFieldCode();
    void saveField(FieldDTO fieldDTO) throws Exception;
    void updateField(FieldDTO fieldDTO) throws Exception;
    void deleteField(String fieldCode) throws Exception;
    List<FieldDTO> getAllFields() throws Exception;
    FieldResponse getFieldByCode(String fieldCode) throws Exception;
}
