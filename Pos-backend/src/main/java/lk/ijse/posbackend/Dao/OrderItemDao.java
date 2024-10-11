package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemDao extends JpaRepository<OrderItemEntity, UUID> {
}
