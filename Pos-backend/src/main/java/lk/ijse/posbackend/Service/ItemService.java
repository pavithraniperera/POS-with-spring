package lk.ijse.posbackend.Service;

import lk.ijse.posbackend.Dto.ItemStatus;
import lk.ijse.posbackend.Dto.impl.ItemDto;

public interface ItemService extends CRUDService<ItemDto>{

    ItemDto getItemByName(String itemName);

    ItemDto getById(String id);
}
