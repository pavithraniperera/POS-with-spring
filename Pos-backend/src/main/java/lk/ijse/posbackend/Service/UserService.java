package lk.ijse.posbackend.Service;

import lk.ijse.posbackend.Dto.impl.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    List<UserDto> getAllUsers();
}
