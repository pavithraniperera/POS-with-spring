package lk.ijse.posbackend.CustomStatusCodes;


import lk.ijse.posbackend.Dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorStatus implements CustomerStatus {
    private int status;
    private String statusMessage;

}
