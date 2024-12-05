package lk.ijse.gsbackend.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Embeddable
public class StaffFieldDetailsPK implements Serializable {
    @Column(name = "staff_id")
    private String id;
    private String fieldCode;
}
