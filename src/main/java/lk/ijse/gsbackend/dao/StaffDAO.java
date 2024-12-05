package lk.ijse.gsbackend.dao;

import lk.ijse.gsbackend.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDAO extends JpaRepository<StaffEntity, String> {
    @Query("SELECT s.id FROM StaffEntity s ORDER BY s.id DESC LIMIT 1")
    String findLastStaffId();
}
