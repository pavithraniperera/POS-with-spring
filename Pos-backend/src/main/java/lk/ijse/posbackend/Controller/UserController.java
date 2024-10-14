package lk.ijse.posbackend.Controller;

import lk.ijse.posbackend.Dto.impl.UserDto;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }
     @PostMapping
    public ResponseEntity<String> registerUser( @RequestBody UserDto userDto) {
        try{
          userService.saveUser(userDto);

        }catch (DataPersistException e){
            e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
         logger.info("saved User successfully");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
       return   userService.getAllUsers();
    }
}
