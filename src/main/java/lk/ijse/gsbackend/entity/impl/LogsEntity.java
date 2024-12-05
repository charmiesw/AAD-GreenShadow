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
@Table(name = "logs")

public class LogsEntity implements SuperEntity {
    @Id
    private String logCode;
    private String logDate;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "cropCode", nullable = false)
    private CropEntity crop;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private StaffEntity staff;
}
