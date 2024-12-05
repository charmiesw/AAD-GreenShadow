package lk.ijse.gsbackend.service.impl;


import lk.ijse.gsbackend.customObj.error.EquipmentErrorResponse;
import lk.ijse.gsbackend.customObj.EquipmentResponse;
import lk.ijse.gsbackend.dao.EquipmentDAO;
import lk.ijse.gsbackend.dao.FieldDAO;
import lk.ijse.gsbackend.dao.StaffDAO;
import lk.ijse.gsbackend.dto.impl.EquipmentDTO;
import lk.ijse.gsbackend.entity.impl.EquipmentEntity;
import lk.ijse.gsbackend.entity.impl.FieldEntity;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.EquipmentNotFound;
import lk.ijse.gsbackend.exception.FieldNotFound;
import lk.ijse.gsbackend.exception.StaffNotFound;
import lk.ijse.gsbackend.service.EquipmentService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceIMPL implements EquipmentService {
    @Autowired
    private EquipmentDAO equipmentDAO;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private FieldDAO fieldDAO;

    public String generateNextEquipmentId() {
        String lastEquipmentId = equipmentDAO.findLastEquipmentId();

        if (lastEquipmentId == null || lastEquipmentId.isEmpty()) {
            return "E001";
        }

        int newId = Integer.parseInt(lastEquipmentId.substring(1)) + 1;

        return String.format("E%03d", newId);
    }

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) throws Exception {
        if (equipmentDTO.getEquipmentId() == null || equipmentDTO.getEquipmentId().isEmpty()) {
            equipmentDTO.setEquipmentId(generateNextEquipmentId());
        }

        StaffEntity staffEntity = staffDAO.findById(equipmentDTO.getAssignedStaffId())
                .orElseThrow(() -> new StaffNotFound("Staff Member Not Found..!!"));

        FieldEntity fieldEntity = fieldDAO.findById(equipmentDTO.getAssignedFieldCode())
                .orElseThrow(() -> new FieldNotFound("Field Not Found..!!"));

        EquipmentEntity equipmentEntity = mapping.convertToEquipmentEntity(equipmentDTO);

        equipmentEntity.setStaff(staffEntity);
        equipmentEntity.setField(fieldEntity);

        EquipmentEntity savedEquipment = equipmentDAO.save(equipmentEntity);
        if (savedEquipment == null) {
            throw new DataPersistFailedException("Cannot Save Equipment..!!");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) throws Exception {
        Optional<EquipmentEntity> tmpEquipmentEntity = equipmentDAO.findById(equipmentId);

        if (!tmpEquipmentEntity.isPresent()) {
            throw new EquipmentNotFound("Equipment Not Found..!!");
        } else {
            tmpEquipmentEntity.get().setName(equipmentDTO.getName());
            tmpEquipmentEntity.get().setType(equipmentDTO.getType());
            tmpEquipmentEntity.get().setStatus(equipmentDTO.getStatus());

            StaffEntity staff = staffDAO.getReferenceById(equipmentDTO.getAssignedStaffId());

            if (staff == null) {
                throw new StaffNotFound("Staff Not Found..!!");
            } else {
                tmpEquipmentEntity.get().setStaff(staff);
            }

            FieldEntity field = fieldDAO.getReferenceById(equipmentDTO.getAssignedFieldCode());

            if (field == null) {
                throw new FieldNotFound("Field Not Found..!!");
            } else {
                tmpEquipmentEntity.get().setField(field);
            }
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) throws Exception {
        Optional<EquipmentEntity> findId = equipmentDAO.findById(equipmentId);

        if (!findId.isPresent()) {
            throw new EquipmentNotFound("Equipment Not Found..!!");
        } else {
            equipmentDAO.deleteById(equipmentId);
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() throws Exception {
        List<EquipmentEntity> allEquipments = equipmentDAO.findAll();
        List<EquipmentDTO> equipmentDTOList = new ArrayList<>();

        for (EquipmentEntity equipment : allEquipments) {
            EquipmentDTO dto = mapping.convertToEquipmentDTO(equipment);
            dto.setAssignedStaffId(equipment.getStaff().getId());
            dto.setAssignedFieldCode(equipment.getField().getFieldCode());
            equipmentDTOList.add(dto);
        }

        return equipmentDTOList;
    }

    @Override
    public EquipmentResponse getEquipmentById(String equipmentId) throws Exception {
        Optional<EquipmentEntity> optionalEquipments = equipmentDAO.findById(equipmentId);

        if (optionalEquipments.isPresent()) {
            EquipmentEntity equipment = optionalEquipments.get();
            EquipmentDTO dto = mapping.convertToEquipmentDTO(equipment);
            dto.setAssignedStaffId(equipment.getStaff().getId());
            dto.setAssignedFieldCode(equipment.getField().getFieldCode());
            return dto;
        } else {
            return new EquipmentErrorResponse(0, "Equipment Not Found..!!");
        }
    }
}
