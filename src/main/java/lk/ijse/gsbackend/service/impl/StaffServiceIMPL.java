package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.error.StaffErrorResponse;
import lk.ijse.gsbackend.customObj.StaffResponse;
import lk.ijse.gsbackend.dao.StaffDAO;
import lk.ijse.gsbackend.dto.impl.StaffDTO;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.StaffNotFound;
import lk.ijse.gsbackend.service.StaffService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceIMPL implements StaffService {
    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public String generateNextStaffId() {
        String lastStaffId = staffDAO.findLastStaffId();

        if (lastStaffId == null || lastStaffId.isEmpty()) {
            return "S001";
        }

        int newStaffId = Integer.parseInt(lastStaffId.substring(1)) + 1;
        return String.format("S%03d", newStaffId);
    }

    @Override
    public void saveStaff(StaffDTO staffDTO) throws Exception {
        if (staffDTO.getId() == null || staffDTO.getId().isEmpty()) {
            staffDTO.setId(generateNextStaffId());
        }

        StaffEntity staffEntity = mapping.convertToStaffEntity(staffDTO);
        StaffEntity saveStaff = staffDAO.save(staffEntity);

        if (saveStaff == null) {
            throw new DataPersistFailedException("Cannot Save Staff Member..!!");
        }
    }

    @Override
    public void updateStaff(String id, StaffDTO staffDTO) throws Exception {
        Optional<StaffEntity> tmpStaffEntity = staffDAO.findById(id);

        if (!tmpStaffEntity.isPresent()) {
            throw new StaffNotFound("Staff Member Not Found..!!");
        } else {
            tmpStaffEntity.get().setLastName(staffDTO.getLastName());
            tmpStaffEntity.get().setEmail(staffDTO.getEmail());
            tmpStaffEntity.get().setFirstName(staffDTO.getFirstName());
            tmpStaffEntity.get().setContactNo(staffDTO.getContactNo());
            tmpStaffEntity.get().setBuildingName(staffDTO.getBuildingName());
            tmpStaffEntity.get().setStreet(staffDTO.getStreet());
            tmpStaffEntity.get().setCity(staffDTO.getCity());
            tmpStaffEntity.get().setDistrict(staffDTO.getDistrict());
            tmpStaffEntity.get().setPostalCode(staffDTO.getPostalCode());
            tmpStaffEntity.get().setDesignation(staffDTO.getDesignation());
            tmpStaffEntity.get().setRole(staffDTO.getRole());
            tmpStaffEntity.get().setGender(staffDTO.getGender());
            tmpStaffEntity.get().setDob(staffDTO.getDob());
            tmpStaffEntity.get().setJoinedDate(staffDTO.getJoinedDate());
        }
    }

    @Override
    public void deleteStaff(String id) throws Exception {
        Optional<StaffEntity> findId = staffDAO.findById(id);

        if (!findId.isPresent()) {
            throw new StaffNotFound("Staff Member Not Found..!!");
        } else {
            staffDAO.deleteById(id);
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() throws Exception {
        List<StaffEntity> allStaff = staffDAO.findAll();
        return mapping.convertStaffToDTOList(allStaff);
    }

    @Override
    public StaffResponse getStaffById(String id) throws Exception {
        if(staffDAO.existsById(id)) {
            return mapping.convertToStaffDTO(staffDAO.getReferenceById(id));
        } else {
            return new StaffErrorResponse(0, "Staff Member Not Found..!!") {
            };
        }
    }
}
