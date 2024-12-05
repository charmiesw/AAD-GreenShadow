package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gsbackend.embedded.StaffFieldDetailsPK;
import lk.ijse.gsbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "staff_field_details")

public class StaffFieldDetailsEntity implements SuperEntity {
    @EmbeddedId
    private StaffFieldDetailsPK staffFieldDetailPK;
    @ManyToOne
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode", insertable = false, updatable = false)
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private StaffEntity staff;
    private String assignedDate;
}
