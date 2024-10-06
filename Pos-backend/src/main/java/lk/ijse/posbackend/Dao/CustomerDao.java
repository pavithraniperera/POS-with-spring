package lk.ijse.posbackend.Dao;

import lk.ijse.posbackend.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<CustomerEntity,String> {

}
