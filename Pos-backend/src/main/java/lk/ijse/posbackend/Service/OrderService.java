package lk.ijse.posbackend.Service;

import lk.ijse.posbackend.Dto.impl.OrderDto;

import java.util.List;

public interface OrderService extends CRUDService<OrderDto>{
    String getLastOrderId();

    OrderDto getOrderById(String orderId);

    List<OrderDto> getOrderByCustomer(String customerId);
}
