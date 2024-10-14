package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemDao extends JpaRepository<OrderItemEntity, UUID> {
    @Query("SELECT oi FROM OrderItemEntity oi WHERE oi.order.orderId = :orderId")
    List<OrderItemEntity> findByOrderId(@Param("orderId")String orderId);
}
