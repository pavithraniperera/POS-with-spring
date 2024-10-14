package lk.ijse.posbackend.Service;

import lk.ijse.posbackend.Dto.impl.OrderDto;

public interface OrderService extends CRUDService<OrderDto>{
    String getLastOrderId();

    OrderDto getOrderById(String orderId);
}
