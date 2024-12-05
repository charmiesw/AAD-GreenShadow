package lk.ijse.gsbackend.dao;

import lk.ijse.gsbackend.entity.impl.LogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsDAO extends JpaRepository<LogsEntity, String> {
    @Query("SELECT l.logCode FROM LogsEntity l ORDER BY l.logCode DESC LIMIT 1")
    String findLastLogCode();
}
