package lk.ijse.posbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude // Exclude customer to prevent circular reference
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "order_date", nullable = false)
    private LocalDate date;

}
