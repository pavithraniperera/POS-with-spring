package lk.ijse.posbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @Column(name = "item_id")
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String description;
    @Column(columnDefinition = "LONGTEXT")
    private String img;

}


