package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.error.VehicleErrorResponse;
import lk.ijse.gsbackend.customObj.VehicleResponse;
import lk.ijse.gsbackend.dao.StaffDAO;
import lk.ijse.gsbackend.dao.VehicleDAO;
import lk.ijse.gsbackend.dto.impl.VehicleDTO;
import lk.ijse.gsbackend.entity.impl.StaffEntity;
import lk.ijse.gsbackend.entity.impl.VehicleEntity;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.StaffNotFound;
import lk.ijse.gsbackend.exception.VehicleNotFound;
import lk.ijse.gsbackend.service.VehicleService;
import lk.ijse.gsbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceIMPL implements VehicleService {
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public String generateNextVehicleCode() {
        String lastVehicleCode = vehicleDAO.findLastVehicleCode();

        if (lastVehicleCode == null || lastVehicleCode.isEmpty()) {
            return "V001";
        }

        int newVehicleCode = Integer.parseInt(lastVehicleCode.substring(1)) + 1;
        return String.format("V%03d", newVehicleCode);
    }

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) throws Exception {
        if (vehicleDTO.getVehicleCode() == null || vehicleDTO.getVehicleCode().isEmpty()) {
            vehicleDTO.setVehicleCode(generateNextVehicleCode());
        }

        StaffEntity staffEntity = staffDAO.findById(vehicleDTO.getAllocatedStaffMemberId())
                .orElseThrow(() -> new StaffNotFound("Staff Member Not Found..!!"));

        VehicleEntity vehicleEntity = mapping.convertToVehicleEntity(vehicleDTO);
        vehicleEntity.setStaff(staffEntity);
        VehicleEntity saveVehicle = vehicleDAO.save(vehicleEntity);

        if (saveVehicle == null) {
            throw new DataPersistFailedException("Cannot Save Vehicle..!!");
        }
    }

    @Override
    public void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO) throws Exception {
        Optional<VehicleEntity> tmpVehicleEntity = vehicleDAO.findById(vehicleCode);

        if (!tmpVehicleEntity.isPresent()) {
            throw new VehicleNotFound("Vehicle Not Found..!!");
        } else {
            tmpVehicleEntity.get().setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
            tmpVehicleEntity.get().setVehicleCategory(vehicleDTO.getVehicleCategory());
            tmpVehicleEntity.get().setFuelType(vehicleDTO.getFuelType());
            tmpVehicleEntity.get().setStatus(vehicleDTO.getStatus());
            tmpVehicleEntity.get().setRemark(vehicleDTO.getRemark());

            StaffEntity staff = staffDAO.getReferenceById(vehicleDTO.getAllocatedStaffMemberId());

            if (staff == null) {
                throw new StaffNotFound("Staff Member Not Found..!!");
            } else {
                tmpVehicleEntity.get().setStaff(staff);
            }
        }
    }

    @Override
    public void deleteVehicle(String vehicleCode) throws Exception {
        Optional<VehicleEntity> findCode = vehicleDAO.findById(vehicleCode);

        if (!findCode.isPresent()) {
            throw new VehicleNotFound("Vehicle Not Found..!!");
        } else {
            vehicleDAO.deleteById(vehicleCode);
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() throws Exception {
        List<VehicleEntity> allVehicles = vehicleDAO.findAll();
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();

        for (VehicleEntity vehicle : allVehicles) {
            VehicleDTO dto = mapping.convertToVehicleDTO(vehicle);
            dto.setAllocatedStaffMemberId(vehicle.getStaff().getId());
            vehicleDTOS.add(dto);
        }

        return vehicleDTOS;
    }

    @Override
    public VehicleResponse getVehicleByCode(String vehicleCode) throws Exception {
        Optional<VehicleEntity> tmpVehicleEntity = vehicleDAO.findById(vehicleCode);

        if (tmpVehicleEntity.isPresent()) {
            VehicleEntity vehicle = tmpVehicleEntity.get();
            VehicleDTO dto = mapping.convertToVehicleDTO(vehicle);
            dto.setAllocatedStaffMemberId(vehicle.getStaff().getId());
            return dto;
        } else {
            return new VehicleErrorResponse(0, "Vehicle Not Found..!!");
        }
    }
}
