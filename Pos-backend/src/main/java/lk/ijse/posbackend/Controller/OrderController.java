package lk.ijse.posbackend.Controller;

import lk.ijse.posbackend.Dto.impl.OrderDto;
import lk.ijse.posbackend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

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


}
