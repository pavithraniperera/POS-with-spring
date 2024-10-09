package lk.ijse.posbackend.Service.impl;


import lk.ijse.posbackend.Dao.ItemDao;
import lk.ijse.posbackend.Dto.ItemStatus;
import lk.ijse.posbackend.Dto.impl.ItemDto;
import lk.ijse.posbackend.Entity.CustomerEntity;
import lk.ijse.posbackend.Entity.ItemEntity;
import lk.ijse.posbackend.Exceptions.CustomerNotFoundException;
import lk.ijse.posbackend.Exceptions.DataPersistException;
import lk.ijse.posbackend.Exceptions.ItemNotFoundException;
import lk.ijse.posbackend.Mapping.Mapping;
import lk.ijse.posbackend.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        Optional<ItemEntity> findItem = itemDao.findById(id);
        if (!findItem.isPresent()){
            throw new CustomerNotFoundException("Item not found");
        }else {
           findItem.get().setName(itemDto.getName());
           findItem.get().setImg(itemDto.getImg());
           findItem.get().setCategory(itemDto.getCategory());
           findItem.get().setDescription(itemDto.getDescription());
           findItem.get().setPrice(BigDecimal.valueOf(itemDto.getPrice()));
           findItem.get().setStockQuantity(itemDto.getStockQuantity());
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ItemDto> getAll() {
        return itemMapping.asItemDtoList(itemDao.findAll());
    }


    @Override
    public ItemDto getItemByName(String itemName) {
        Optional<ItemEntity> itemOptional = itemDao.findByName(itemName);
        if (itemOptional.isPresent()) {
            ItemEntity item = itemOptional.get();

            return itemMapping.toItemDto(item);
        } else {
            throw new ItemNotFoundException("Item with name " + itemName + " not found");
        }
    }

    @Override
    public ItemDto getById(String id) {
        return null;
    }
}
