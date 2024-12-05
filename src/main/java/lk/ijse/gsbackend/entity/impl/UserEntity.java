package lk.ijse.gsbackend.entity.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lk.ijse.gsbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")

public class UserEntity implements SuperEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
}
