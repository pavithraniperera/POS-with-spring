package lk.ijse.posbackend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.posbackend.CustomStatusCodes.ErrorStatus;
import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.ItemStatus;
import lk.ijse.posbackend.Dto.impl.ItemDto;
import lk.ijse.posbackend.Service.ItemService;
import lk.ijse.posbackend.util.AppUtil;
import lk.ijse.posbackend.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }
    @PostMapping
    public ResponseEntity<String> saveItem(@RequestParam("itemData") String itemData,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Convert itemData JSON string to ItemDto object
            ObjectMapper objectMapper = new ObjectMapper();
            ItemDto itemDto = objectMapper.readValue(itemData, ItemDto.class);

            // Convert the image file to Base64 string
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Set Base64 image and id strings in DTO
            itemDto.setImg(base64Image);
            itemDto.setId(AppUtil.generateItemId());


            itemService.save(itemDto);

            return new ResponseEntity<>("Item saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<ItemDto> getAllCustomers() {
        return itemService.getAll();
    }

    @GetMapping(value = "/{itemName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemStatus getSelectedItem(@PathVariable("itemName") String itemName) {
        ItemDto itemDto = itemService.getItemByName(itemName);

        if (!RegexUtil.isValidItemId(itemDto.getId())) {
            return new ErrorStatus(1, "Item id not matched");
        }
       return itemDto;


    }

    @PutMapping(value = "/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("itemId") String itemId,@RequestParam("itemData") String itemData,
                                             @RequestParam("imageFile") MultipartFile imageFile){
        try {
            // Convert itemData JSON string to ItemDto object
            ObjectMapper objectMapper = new ObjectMapper();
            ItemDto itemDto = objectMapper.readValue(itemData, ItemDto.class);

            // Convert the image file to Base64 string
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Set Base64 image and id strings in DTO
            itemDto.setImg(base64Image);



            itemService.update(itemId,itemDto);

            return new ResponseEntity<>("Item Updated successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to Update item", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



}
