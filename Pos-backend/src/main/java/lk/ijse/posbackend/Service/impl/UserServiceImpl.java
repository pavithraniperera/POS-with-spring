package lk.ijse.posbackend.Service.impl;

import lk.ijse.posbackend.Dao.UserDao;
import lk.ijse.posbackend.Dto.UserDto;
import lk.ijse.posbackend.Entity.UserEntity;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Mapping.Mapping;
import lk.ijse.posbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping userMapping;
    @Override
    public void saveUser(UserDto userDto) {
        UserEntity saved = userDao.save(userMapping.toUserEntity(userDto));
        if (saved==null){
            throw new DataPersistException("User Not Registered");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }
}
