package lk.ijse.gsbackend.util;

import lk.ijse.gsbackend.dto.impl.*;
import lk.ijse.gsbackend.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //Matters of Crop Entity and DTO
    public CropDTO convertToCropDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }
    public CropEntity convertToCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }

    public List<CropDTO> convertCropToDTOList(List<CropEntity> allCrops) {
        return modelMapper.map(allCrops, new TypeToken<List<CropDTO>>() {}.getType());
    }

    //Matters of Filed Entity and DTO
    public FieldDTO convertToFieldDTO(FieldEntity fieldEntity) {
        return modelMapper.map(fieldEntity, FieldDTO.class);
    }
    public FieldEntity convertToFieldEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    public List<FieldDTO> convertFieldToDTOList(List<FieldEntity> allFields) {
        return modelMapper.map(allFields, new TypeToken<List<FieldDTO>>() {}.getType());
    }

    //Matters of Vehicle Entity and DTO
    public VehicleDTO convertToVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }
    public VehicleEntity convertToVehicleEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }

    public List<VehicleDTO> convertVehicleToDTOList(List<VehicleEntity> allVehicles) {
        return modelMapper.map(allVehicles, new TypeToken<List<VehicleDTO>>() {}.getType());
    }

    //Matters of Staff Entity and DTO
    public StaffDTO convertToStaffDTO(StaffEntity staffEntity) {
        return modelMapper.map(staffEntity, StaffDTO.class);
    }
    public StaffEntity convertToStaffEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }

    public List<StaffDTO> convertStaffToDTOList(List<StaffEntity> allStaff) {
        return modelMapper.map(allStaff, new TypeToken<List<StaffDTO>>() {}.getType());
    }

    //Matters of Equipment Entity and DTO
    public EquipmentDTO convertToEquipmentDTO(EquipmentEntity equipmentEntity) {
        return modelMapper.map(equipmentEntity, EquipmentDTO.class);
    }
    public EquipmentEntity convertToEquipmentEntity(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, EquipmentEntity.class);
    }

    public List<EquipmentDTO> convertEquipmentToDTOList(List<EquipmentEntity> allEquipments) {
        return modelMapper.map(allEquipments, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }

    //Matters of Logs Entity and DTO
    public LogsDTO convertToLogsDTO(LogsEntity logsEntity) {
        return modelMapper.map(logsEntity, LogsDTO.class);
    }
    public LogsEntity convertToLogsEntity(LogsDTO logsDTO) {
        return modelMapper.map(logsDTO, LogsEntity.class);
    }

    public List<LogsDTO> convertLogsToDTOList(List<LogsEntity> allLogs) {
        return modelMapper.map(allLogs, new TypeToken<List<LogsDTO>>() {}.getType());
    }

    //Matters of User Entity and DTO
    public UserDTO convertToUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public List<UserDTO> convertUserToDTOList(List<UserEntity> allUsers) {
        return modelMapper.map(allUsers, new TypeToken<List<UserDTO>>() {}.getType());
    }
}
