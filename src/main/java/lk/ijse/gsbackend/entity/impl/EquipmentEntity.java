package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.entity.SuperEntity;
import lk.ijse.gsbackend.enums.Equipment;
import lk.ijse.gsbackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "equipment")

public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Equipment type;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private StaffEntity staff;
    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity field;
}
