package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<OrderEntity,String> {
    // Custom query to get the last order ID
    @Query(value = "SELECT o.order_id FROM orders o ORDER BY o.order_id DESC LIMIT 1", nativeQuery = true)
    String findLastOrderId();
}
