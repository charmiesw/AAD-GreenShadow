package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.EquipmentResponse;
import lk.ijse.gsbackend.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    String generateNextEquipmentId();
    void saveEquipment(EquipmentDTO equipmentDTO) throws Exception;
    void updateEquipment(String id, EquipmentDTO equipmentDTO) throws Exception;
    void deleteEquipment(String equipmentId) throws Exception;
    List<EquipmentDTO> getAllEquipments() throws Exception;
    EquipmentResponse getEquipmentById(String equipmentId) throws Exception;
}
