package lk.ijse.posbackend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.posbackend.CustomStatusCodes.ErrorStatus;
import lk.ijse.posbackend.Dto.CustomerStatus;
import lk.ijse.posbackend.Dto.ItemStatus;
import lk.ijse.posbackend.Dto.impl.ItemDto;
import lk.ijse.posbackend.Exceptions.CustomerNotFoundException;
import lk.ijse.posbackend.Service.ItemService;
import lk.ijse.posbackend.util.AppUtil;
import lk.ijse.posbackend.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }
    @PostMapping
    public ResponseEntity<String> saveItem(@RequestParam("itemData") String itemData,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
        logger.info("Received request to save item with data: {}", itemData);
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
            logger.info("Item {} saved successfully", itemDto.getId());
            return new ResponseEntity<>("Item saved successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error saving item", e);
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
            logger.warn("Invalid item ID: {}", itemDto.getId());
            return new ErrorStatus(1, "Item id not matched");
        }
        logger.info("Item {} retrieved successfully", itemDto.getId());
       return itemDto;


    }

    @PutMapping(value = "/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("itemId") String itemId,@RequestParam("itemData") String itemData,
                                             @RequestParam("imageFile") MultipartFile imageFile){
        logger.info("Received request to update item with ID: {}", itemId);
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
            logger.info("Item {} updated successfully", itemId);

            return new ResponseEntity<>("Item Updated successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error updating item with ID: {}", itemId, e);
            return new ResponseEntity<>("Failed to Update item", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @DeleteMapping(value = "/{itemId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("itemId") String itemId){
        logger.info("Received request to delete item with ID: {}", itemId);

        try {
            if (!RegexUtil.isValidItemId(itemId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            itemService.delete(itemId);
            logger.info("Item {} deleted successfully", itemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (CustomerNotFoundException e){
            e.printStackTrace();
            logger.error("Item not found with ID: {}", itemId, e);
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
