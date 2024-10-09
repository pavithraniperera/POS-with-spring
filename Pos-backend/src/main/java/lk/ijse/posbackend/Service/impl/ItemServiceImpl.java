package lk.ijse.posbackend.Service.impl;


import lk.ijse.posbackend.Dao.ItemDao;
import lk.ijse.posbackend.Dto.impl.ItemDto;
import lk.ijse.posbackend.Entity.ItemEntity;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Mapping.Mapping;
import lk.ijse.posbackend.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private Mapping itemMapping;
    @Override
    public void save(ItemDto itemDto) {
        ItemEntity saved = itemDao.save(itemMapping.toItemEntity(itemDto));
        if (saved==null){
            throw new DataPersistException("Item Not Saved");
        }

    }

    @Override
    public void update(String id,ItemDto itemDto) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ItemDto> getAll() {
        return null;
    }



}
