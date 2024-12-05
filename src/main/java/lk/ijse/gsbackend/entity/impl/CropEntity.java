package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "crop")

public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;
    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity field;
    @OneToMany(mappedBy = "crop" , cascade = CascadeType.ALL)
    private List<LogsEntity> logs;
}
