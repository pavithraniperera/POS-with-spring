package lk.ijse.posbackend.Service;

import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.impl.CustomerDto;

public interface CustomerService extends CRUDService<CustomerDto>{
    CustomerStatus getById(String id);
}
