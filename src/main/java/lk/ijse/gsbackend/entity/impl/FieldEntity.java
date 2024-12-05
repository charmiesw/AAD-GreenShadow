package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "field")

public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private double extentSize;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<CropEntity> crops;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<StaffFieldDetailsEntity> staffField;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<LogsEntity> logs;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<EquipmentEntity> equipment;
}
