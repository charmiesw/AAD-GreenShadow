package lk.ijse.gsbackend.entity.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lk.ijse.gsbackend.entity.SuperEntity;
import lk.ijse.gsbackend.enums.Gender;
import lk.ijse.gsbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "staff")

public class StaffEntity implements SuperEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String contactNo;
    private String buildingName;
    private String street;
    private String city;
    private String district;
    private String postalCode;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate dob;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate joinedDate;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<VehicleEntity> vehicles;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<StaffFieldDetailsEntity> staffField;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<LogsEntity> logs;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<EquipmentEntity> equipment;
}
