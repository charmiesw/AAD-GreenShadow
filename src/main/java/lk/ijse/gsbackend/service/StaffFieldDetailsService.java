package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dto.impl.StaffFieldDetailsDTO;
import lk.ijse.gsbackend.dto.impl.StaffFieldDetailsDTO;

import java.util.List;

public interface StaffFieldDetailsService {
    void saveStaffField(StaffFieldDetailsDTO staffFieldDetailsDTO) throws Exception;
    void updateStaffField(StaffFieldDetailsDTO staffFieldDetailsDTO) throws Exception;
    void deleteStaffField(String field_staff_code) throws Exception;
    List<StaffFieldDetailsDTO> getAllStaffField() throws Exception;
    FieldResponse getStaffField(String field_staff_code) throws Exception;
    List<StaffFieldDetailsDTO> getStaffByFieldCode(String fieldCode) throws Exception;
    List<StaffFieldDetailsDTO> getFieldByStaffId(String staff_id) throws Exception;
}
