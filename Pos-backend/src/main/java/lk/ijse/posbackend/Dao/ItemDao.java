package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<ItemEntity,Integer> {
}
