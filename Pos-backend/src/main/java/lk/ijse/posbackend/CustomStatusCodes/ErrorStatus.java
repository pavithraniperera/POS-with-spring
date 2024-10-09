package lk.ijse.posbackend.CustomStatusCodes;


import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorStatus implements CustomerStatus, ItemStatus {
    private int status;
    private String statusMessage;

}
