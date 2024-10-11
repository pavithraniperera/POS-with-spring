package lk.ijse.posbackend.Dto.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    private String orderId;
    private String customerId;
    private List<ItemDto> itemDtoList;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Double total;

}
