package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderEntity,String> {
}
