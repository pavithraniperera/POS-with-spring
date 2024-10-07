package lk.ijse.posbackend.Mapping;

import lk.ijse.posbackend.Dto.*;
import lk.ijse.posbackend.Entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    private final ModelMapper modelMapper;

    @Autowired
    public Mapping(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings(){
        // Custom mapping to set itemName in OrderItemDto from OrderItem's Item entity
        modelMapper.typeMap(OrderItemEntity.class, OrderItemDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getItem().getId(), OrderItemDto::setItemId);
            mapper.map(src -> src.getItem().getName(), OrderItemDto::setItemName);
        });

    }

    //For Users
    public UserEntity  toUserEntity(UserDto userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }
    public UserDto toUserDto(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDto.class);
    }
    public List<UserDto> asUserDtoList(List<UserEntity> userEntities){
        return  modelMapper.map(userEntities, new TypeToken<List<UserDto>>() {}.getType());
    }

    //For customers
    public UserEntity  toCustomerEntity(CustomerDto customerDto){
        return modelMapper.map(customerDto, UserEntity.class);
    }
    public UserDto toCustomerDto(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, UserDto.class);
    }
    public List<CustomerDto> asCustomerDtoList(List<CustomerEntity> customerEntities){
        return  modelMapper.map(customerEntities, new TypeToken<List<CustomerDto>>() {}.getType());
    }

    //For Items
    public ItemEntity toItemEntity(ItemDto itemDto){
        return modelMapper.map(itemDto, ItemEntity.class);
    }

    public ItemDto toItemDto(ItemEntity item){
        return modelMapper.map(item, ItemDto.class);
    }

    public List<ItemDto> asItemDtoList(List<ItemEntity> items){
        return modelMapper.map(items, new TypeToken<List<ItemDto>>() {}.getType());
    }

    //For Orders
    public OrderEntity toOrderEntity(OrderDto orderDto){
        return modelMapper.map(orderDto, OrderEntity.class);
    }

    public OrderDto toOrderDto(OrderEntity order){
        return modelMapper.map(order, OrderDto.class);
    }

    public List<OrderDto> asOrderDtoList(List<OrderEntity> orders){
        return modelMapper.map(orders, new TypeToken<List<OrderDto>>() {}.getType());
    }

    //For orderItems
    public OrderItemEntity toOrderItemEntity(OrderItemDto orderItemDto){
        return modelMapper.map(orderItemDto, OrderItemEntity.class);
    }

    public OrderItemDto toOrderItemDto(OrderItemEntity orderItem){
        return modelMapper.map(orderItem, OrderItemDto.class);
    }

    public List<OrderItemDto> asOrderItemDtoList(List<OrderItemEntity> orderItems){
        return modelMapper.map(orderItems, new TypeToken<List<OrderItemDto>>() {}.getType());
    }




}

