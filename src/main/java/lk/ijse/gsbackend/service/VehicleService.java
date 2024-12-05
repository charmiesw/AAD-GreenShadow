package lk.ijse.gsbackend.service;

import lk.ijse.gsbackend.customObj.VehicleResponse;
import lk.ijse.gsbackend.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    String generateNextVehicleCode();
    void saveVehicle(VehicleDTO vehicleDTO) throws Exception;
    void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO) throws Exception;
    void deleteVehicle(String vehicleCode) throws Exception;
    List<VehicleDTO> getAllVehicles() throws Exception;
    VehicleResponse getVehicleByCode(String vehicleCode) throws Exception;
}
