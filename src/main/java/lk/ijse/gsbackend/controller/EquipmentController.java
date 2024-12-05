package lk.ijse.gsbackend.controller;

import lk.ijse.gsbackend.customObj.EquipmentResponse;
import lk.ijse.gsbackend.dto.impl.EquipmentDTO;
import lk.ijse.gsbackend.exception.DataPersistFailedException;
import lk.ijse.gsbackend.exception.EquipmentNotFound;
import lk.ijse.gsbackend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("aad/gs/equipment")
@RequiredArgsConstructor
@CrossOrigin

public class EquipmentController {
    @Autowired
    private final EquipmentService equipmentService;

    @GetMapping("/next-id")
    public ResponseEntity<String> getNextEquipmentId() {
        String nextId = equipmentService.generateNextEquipmentId();
        return ResponseEntity.ok(nextId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO){
        if(equipmentDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                equipmentService.saveEquipment(equipmentDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") String equipmentId){
        try {
            equipmentService.deleteEquipment(equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getSelectedEquipment(@PathVariable("id") String equipmentId) throws Exception {
        return equipmentService.getEquipmentById(equipmentId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments() throws Exception {
        return equipmentService.getAllEquipments();
    }

    @PatchMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable ("id") String equipmentId, @RequestBody EquipmentDTO equipmentDTO) throws Exception {
        try {
            if(equipmentDTO == null && (equipmentId == null || equipmentDTO.equals(""))) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentId, equipmentDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFound e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
