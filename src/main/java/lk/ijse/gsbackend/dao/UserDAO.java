package lk.ijse.gsbackend.dao;

import lk.ijse.gsbackend.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String username);

    boolean existsByEmail(String username);
}
