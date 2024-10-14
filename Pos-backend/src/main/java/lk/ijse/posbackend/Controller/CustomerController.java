package lk.ijse.posbackend.Controller;

import lk.ijse.posbackend.CustomStatusCodes.ErrorStatus;
import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.impl.CustomerDto;
import lk.ijse.posbackend.Exceptions.CustomerNotFoundException;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Service.CustomerService;
import lk.ijse.posbackend.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCustomer(@Validated @RequestBody CustomerDto customerDto) {
        logger.info("Received request to save customer: {}", customerDto.getId());
        try {
            if (RegexUtil.isValidCustomerId(customerDto.getId())) {
                customerService.save(customerDto);
                logger.info("Customer {} saved successfully", customerDto.getId());
            }
        } catch (DataPersistException e) {
            logger.error("Error while saving customer: {}", customerDto.getId(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        logger.info("Received request to get all customers");
        return customerService.getAll();
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getSelectedCustomer(@PathVariable("customerId") String customerId) {
        logger.info("Received request to get customer by ID: {}", customerId);

        if (!RegexUtil.isValidCustomerId(customerId)) {
            logger.warn("Invalid customer ID: {}", customerId);
            return new ErrorStatus(1, "Customer Id not matched");
        }

        CustomerStatus customerStatus = customerService.getById(customerId);
        logger.info("Customer with ID {} retrieved", customerId);
        return customerStatus;
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") String customerId,
                                               @RequestBody CustomerDto updateCustomer) {
        logger.info("Received request to update customer: {}", customerId);
        try {
            if (!RegexUtil.isValidCustomerId(customerId) || updateCustomer == null) {
                logger.warn("Invalid customer ID or update data");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.update(customerId, updateCustomer);
            logger.info("Customer {} updated successfully", customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error while updating customer: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId) {
        logger.info("Received request to delete customer: {}", customerId);
        try {
            if (!RegexUtil.isValidCustomerId(customerId)) {
                logger.warn("Invalid customer ID: {}", customerId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.delete(customerId);
            logger.info("Customer {} deleted successfully", customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while deleting customer: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
