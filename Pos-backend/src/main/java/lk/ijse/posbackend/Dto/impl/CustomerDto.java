package lk.ijse.posbackend.Dto.impl;

import lk.ijse.posbackend.Dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto implements CustomerStatus {
    private String id;
    private String name;
    private String contact;
    private String address;
    private  String note;
}
