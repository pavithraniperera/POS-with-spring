package lk.ijse.posbackend.Dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    private String orderId;
    private String customerId;
    private List<ItemDto> itemDtoList;
    private Date date;
    private Double total;

}
