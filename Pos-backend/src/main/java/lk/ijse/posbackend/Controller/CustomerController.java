package lk.ijse.posbackend.Controller;

import lk.ijse.posbackend.CustomStatusCodes.ErrorStatus;
import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.impl.CustomerDto;
import lk.ijse.posbackend.Exceptions.CustomerNotFoundException;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Service.CustomerService;
import lk.ijse.posbackend.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCustomer(@Validated @RequestBody CustomerDto customerDto) {
        try {
            if (RegexUtil.isValidCustomerId(customerDto.getId())) {
                customerService.save(customerDto);
            }

        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getSelectedCustomer(@PathVariable("customerId") String customerId) {

        if (!RegexUtil.isValidCustomerId(customerId)) {
            return new ErrorStatus(1, "Customer Id not matched");
        }
        return customerService.getById(customerId);


    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") String customerId,
                                               @RequestBody CustomerDto updateCustomer) {

        try {
            if (!RegexUtil.isValidCustomerId(customerId) || updateCustomer == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.update(customerId, updateCustomer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId){

        try {
            if (!RegexUtil.isValidCustomerId(customerId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.delete(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (CustomerNotFoundException e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}


