package lk.ijse.gsbackend.dao;

import lk.ijse.gsbackend.embedded.StaffFieldDetailsPK;
import lk.ijse.gsbackend.entity.impl.StaffFieldDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffFieldDetailsDAO extends JpaRepository<StaffFieldDetailsEntity, StaffFieldDetailsPK> {
    @Query("SELECT s.staffFieldDetailPK.id FROM StaffFieldDetailsEntity s WHERE s.staffFieldDetailPK.fieldCode = : fieldCode")
    List<String> findStaffIdsByFieldCode(@Param("fieldCode") String fieldCode);

    @Query("SELECT s.staffFieldDetailPK.fieldCode FROM StaffFieldDetailsEntity s WHERE s.staffFieldDetailPK.id = : staffId")
    List<String> findFieldCodesByStaffId(@Param("staffId") String staffId);

}