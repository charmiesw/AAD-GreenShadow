package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vehicle")

public class VehicleEntity implements SuperEntity {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String remark;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private StaffEntity staff;
}
