package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.StaffResponse;
import lk.ijse.gsbackend.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    String generateNextStaffId();
    void saveStaff(StaffDTO staffDTO) throws Exception;
    void updateStaff(String id, StaffDTO staffDTO) throws Exception;
    void deleteStaff(String id) throws Exception;
    List<StaffDTO> getAllStaff() throws Exception;
    StaffResponse getStaffById(String id) throws Exception;
}
