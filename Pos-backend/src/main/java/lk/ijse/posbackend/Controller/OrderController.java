package lk.ijse.posbackend.Controller;

import lk.ijse.posbackend.Dto.impl.CustomerDto;
import lk.ijse.posbackend.Dto.impl.OrderDto;
import lk.ijse.posbackend.Exceptions.OrderNotFoundException;
import lk.ijse.posbackend.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOrder(@RequestBody OrderDto orderDto) {
        System.out.println("save order");
        try {
            System.out.print(orderDto);
            orderService.save(orderDto);
            return new ResponseEntity<>("Order saved successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lastOrderId")
    public ResponseEntity<String> getLastOrderId() {
        String lastOrderId = orderService.getLastOrderId();
        if (lastOrderId != null) {
            return ResponseEntity.ok(lastOrderId);
        } else {
            return ResponseEntity.ok(null);  // If no orders, return null
        }
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") String orderId) {
        logger.info("Fetching order with ID: {}", orderId);  // Logging instead of println
        try {
            OrderDto orderDto = orderService.getOrderById(orderId);
            return ResponseEntity.ok(orderDto);  // Return 200 OK with orderDto
        } catch (OrderNotFoundException e) {
            logger.error("Order not found for ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 Not Found
        } catch (Exception e) {
            logger.error("Error fetching order for ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Return 500 Internal Server Error
        }
    }
}
