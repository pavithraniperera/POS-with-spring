package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemDao extends JpaRepository<ItemEntity,String> {
    // Custom JPQL query to find an item by name
    @Query("SELECT i FROM ItemEntity i WHERE i.name = :name")
    Optional<ItemEntity> findByName(@Param("name") String name);
}
