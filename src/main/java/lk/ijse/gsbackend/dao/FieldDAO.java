package lk.ijse.gsbackend.dao;

import lk.ijse.gsbackend.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity, String> {
    @Query("SELECT f.fieldCode FROM FieldEntity f ORDER BY f.fieldCode DESC LIMIT 1")
    String findLastFieldCode();
}
