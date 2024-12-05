package lk.ijse.gsbackend.controller;

import lk.ijse.gsbackend.customObj.VehicleResponse;
import lk.ijse.gsbackend.dto.impl.VehicleDTO;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.VehicleNotFound;
import lk.ijse.gsbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("aad/gs/vehicle")
@RequiredArgsConstructor
@CrossOrigin

public class VehicleController {
    @Autowired
    private final VehicleService vehicleService;

    @GetMapping("/next-code")
    public ResponseEntity<String> getNextVehicleCode() {
        String nextCode = vehicleService.generateNextVehicleCode();
        return ResponseEntity.ok(nextCode);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicleDTO){

        if(vehicleDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                vehicleService.saveVehicle(vehicleDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("code") String vehicleCode){
        try {
            vehicleService.deleteVehicle(vehicleCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (VehicleNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleResponse getSelectedVehicle(@PathVariable("code") String vehicleCode) throws Exception {
        return vehicleService.getVehicleByCode(vehicleCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicle() throws Exception {
        return vehicleService.getAllVehicles();
    }

    @PatchMapping(value = "/{code}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable ("code") String vehicleCode, @RequestBody VehicleDTO vehicleDTO) throws Exception {
        try {
            if(vehicleDTO == null && (vehicleCode == null || vehicleDTO.equals(""))) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.updateVehicle(vehicleCode, vehicleDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (VehicleNotFound e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
