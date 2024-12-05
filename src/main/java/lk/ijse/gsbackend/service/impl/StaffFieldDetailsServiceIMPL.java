package lk.ijse.gsbackend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gsbackend.customObj.FieldResponse;
import lk.ijse.gsbackend.dao.FieldDAO;
import lk.ijse.gsbackend.dao.StaffDAO;
import lk.ijse.gsbackend.dao.StaffFieldDetailsDAO;
import lk.ijse.gsbackend.dto.impl.StaffFieldDetailsDTO;
import lk.ijse.gsbackend.embedded.StaffFieldDetailsPK;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lk.ijse.gsbackend.entity.impl.StaffFieldDetailsEntity;
import lk.ijse.gsbackend.service.StaffFieldDetailsService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffFieldDetailsServiceIMPL implements StaffFieldDetailsService {

    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private StaffFieldDetailsDAO staffFieldDetailsDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveStaffField(StaffFieldDetailsDTO batchDTO) throws Exception {
        if (batchDTO.getFieldCode() == null || batchDTO.getStaffIds() == null || batchDTO.getStaffIds().isEmpty()) {
            throw new IllegalArgumentException("Field Code And Staff IDs Cannot Be Null Or Empty..!!");
        }

        // Fetch the field entity
        FieldEntity field = fieldDAO.findById(batchDTO.getFieldCode())
                .orElseThrow(() -> new EntityNotFoundException("Field Not Found..!!"));

        // Loop through staff IDs and save each relationship
        for (String staffId : batchDTO.getStaffIds()) {
            StaffEntity staff = staffDAO.findById(staffId)
                    .orElseThrow(() -> new EntityNotFoundException("Staff Not Found..!!"));

            // Create StaffFieldDetailsPK
            StaffFieldDetailsPK pk = new StaffFieldDetailsPK();
            pk.setFieldCode(batchDTO.getFieldCode());
            pk.setId(staffId);

            // Create and save StaffFieldDetails
            StaffFieldDetailsEntity staffFieldDetailsEntity = new StaffFieldDetailsEntity();
            staffFieldDetailsEntity.setStaffFieldDetailPK(pk);
            staffFieldDetailsEntity.setField(field);
            staffFieldDetailsEntity.setStaff(staff);
            staffFieldDetailsEntity.setAssignedDate(batchDTO.getAssignedDate());

            // Persist
            staffFieldDetailsDAO.save(staffFieldDetailsEntity);
        }
    }

    @Override
    public void updateStaffField(StaffFieldDetailsDTO staffFieldDetailsDTO) throws Exception {

    }

    @Override
    public void deleteStaffField(String field_staff_code) throws Exception {

    }

    @Override
    public List<StaffFieldDetailsDTO> getAllStaffField() throws Exception {
        // Fetch all StaffFieldDetails entities from the database
        List<StaffFieldDetailsEntity> staffFieldDetailsList = staffFieldDetailsDAO.findAll();

        // Map each entity to a DTO
        List<StaffFieldDetailsDTO> staffFieldDetailsDTOList = staffFieldDetailsList.stream().map(staffFieldDetails -> {
            StaffFieldDetailsDTO dto = new StaffFieldDetailsDTO();
            dto.setFieldCode(staffFieldDetails.getStaffFieldDetailPK().getFieldCode());
            dto.setStaffIds(List.of(staffFieldDetails.getStaffFieldDetailPK().getId())); // Convert single staff ID to a list
            dto.setAssignedDate(staffFieldDetails.getAssignedDate());
            return dto;
        }).toList();

        // Return the DTO list
        return staffFieldDetailsDTOList;
    }


    @Override
    public FieldResponse getStaffField(String fieldId) throws Exception {
        return null;
    }

    @Override
    public List<StaffFieldDetailsDTO> getStaffByFieldCode(String fieldCode) throws Exception {
        if (fieldCode == null || fieldCode.isEmpty()) {
            throw new IllegalArgumentException("Field Code Cannot Be Null Or Empty..!!");
        }

        List<String> staffIds = staffFieldDetailsDAO.findStaffIdsByFieldCode(fieldCode);

        if (staffIds.isEmpty()) {
            throw new EntityNotFoundException("No Staff Members Found..!!");
        }

        // Map each staff ID to a StaffFieldDetailsDTO
        List<StaffFieldDetailsDTO> staffFieldDetailsDTOList = staffIds.stream().map(staffId -> {
            StaffFieldDetailsDTO dto = new StaffFieldDetailsDTO();
            dto.setFieldCode(fieldCode);
            dto.setStaffIds(List.of(staffId));
            return dto;
        }).toList();

        // Return the DTO list
        return staffFieldDetailsDTOList;
    }

    @Override
    public List<StaffFieldDetailsDTO> getFieldByStaffId(String staffId) throws Exception {
        if (staffId == null || staffId.isEmpty()) {
            throw new IllegalArgumentException("Staff ID Cannot Be Null Or Empty..!!");
        }

        // Fetch field codes associated with the given staff ID
        List<String> fieldCodes = staffFieldDetailsDAO.findFieldCodesByStaffId(staffId);

        if (fieldCodes.isEmpty()) {
            throw new EntityNotFoundException("No Fields Found..!!");
        }

        // Map each field code to a StaffFieldDetailsDTO
        List<StaffFieldDetailsDTO> staffFieldDetailsDTOList = fieldCodes.stream().map(fieldCode -> {
            StaffFieldDetailsDTO dto = new StaffFieldDetailsDTO();
            dto.setFieldCode(fieldCode);
            dto.setStaffIds(List.of(staffId)); // Add staff_id as a list (matching your DTO structure)
            return dto;
        }).toList();

        // Return the DTO list
        return staffFieldDetailsDTOList;
    }


}
