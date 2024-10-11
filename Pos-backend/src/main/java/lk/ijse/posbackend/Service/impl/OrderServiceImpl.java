package lk.ijse.posbackend.Service.impl;

import lk.ijse.posbackend.Dao.ItemDao;
import lk.ijse.posbackend.Dao.OrderDao;
import lk.ijse.posbackend.Dao.OrderItemDao;
import lk.ijse.posbackend.Dto.impl.ItemDto;
import lk.ijse.posbackend.Dto.impl.OrderDto;
import lk.ijse.posbackend.Entity.ItemEntity;
import lk.ijse.posbackend.Entity.OrderEntity;
import lk.ijse.posbackend.Entity.OrderItemEntity;
import lk.ijse.posbackend.Mapping.Mapping;
import lk.ijse.posbackend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private Mapping mapping;


    @Override
    public void save(OrderDto orderDto) {
        // Save the order entity first
        OrderEntity savedOrder = orderDao.save(mapping.toOrderEntity(orderDto));

        // Loop through each item in the order
        for (ItemDto itemDto : orderDto.getItemDtoList()) {
            Optional<ItemEntity> itemOpt = itemDao.findByName(itemDto.getName());

            if (itemOpt.isPresent()) {
                ItemEntity item = itemOpt.get();

                // Ensure we are using correct naming: buying quantity vs stock quantity
                int buyingQuantity = itemDto.getStockQuantity(); // renamed from getStockQuantity() to avoid confusion

                // Check stock availability
                if (item.getStockQuantity() >= buyingQuantity) {
                    // Update stock quantity
                    item.setStockQuantity(item.getStockQuantity() - buyingQuantity);
                    itemDao.save(item); // Save the updated stock quantity

                    // Create and save the order item
                    OrderItemEntity orderItem = new OrderItemEntity();
                    orderItem.setOrder(savedOrder);
                    orderItem.setItem(item);
                    orderItem.setQuantity(buyingQuantity);
                    orderItem.setUnitPrice(item.getPrice());
                    orderItem.setTotalPrice(BigDecimal.valueOf(orderDto.getTotal()));

                    // Save the order item to the database
                    orderItemDao.save(orderItem);
                } else {
                    // Throw an error if there's insufficient stock
                    throw new RuntimeException("Insufficient stock for item: " + item.getName());
                }
            } else {
                throw new RuntimeException("Item not found: " + itemDto.getName());
            }
        }
    }

    @Override
    public void update(String id,OrderDto orderDto) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<OrderDto> getAll() {
        return null;
    }


}
