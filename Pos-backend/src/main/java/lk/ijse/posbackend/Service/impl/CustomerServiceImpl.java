package lk.ijse.posbackend.Service.impl;

import lk.ijse.posbackend.CustomStatusCodes.ErrorStatus;
import lk.ijse.posbackend.Dao.CustomerDao;
import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.impl.CustomerDto;
import lk.ijse.posbackend.Entity.CustomerEntity;
import lk.ijse.posbackend.Exceptions.CustomerNotFoundException;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Mapping.Mapping;
import lk.ijse.posbackend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Mapping customerMapping;
    @Override
    public void save(CustomerDto customerDto) {
        CustomerEntity saved = customerDao.save(customerMapping.toCustomerEntity(customerDto));
        if (saved==null){
            throw new DataPersistException("Customer Not Saved");
        }

    }

    @Override
    public void update(String id,CustomerDto customerDto) {
        Optional<CustomerEntity> findCustomer = customerDao.findById(id);
        if (!findCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer not found");
        }else {
           findCustomer.get().setName(customerDto.getName());
           findCustomer.get().setNote(customerDto.getNote());
           findCustomer.get().setAddress(customerDto.getAddress());
           findCustomer.get().setContact(customerDto.getContact());
        }

    }

    @Override
    public void delete(String id) {
        Optional<CustomerEntity> existById = customerDao.findById(id);
        if (!existById.isPresent()){
            throw  new CustomerNotFoundException("Customer with id "+id+" not found");
        }else {
            customerDao.deleteById(id);
        }

    }

    @Override
    public List<CustomerDto> getAll() {
        return customerMapping.asCustomerDtoList(customerDao.findAll());
    }

    @Override
    public CustomerStatus getById(String id) {
        if (customerDao.existsById(id)){
            CustomerEntity selectedCustomer =  customerDao.getReferenceById(id);
            return customerMapping.toCustomerDto(selectedCustomer);
        }else {
            return new ErrorStatus(2,"Selected Customer not found");
        }
    }
}
