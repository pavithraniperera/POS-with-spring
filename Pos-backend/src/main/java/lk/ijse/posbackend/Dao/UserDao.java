package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity,Long> {
}
