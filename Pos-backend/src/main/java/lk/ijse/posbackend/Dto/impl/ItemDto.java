package lk.ijse.posbackend.Dto.impl;

import lk.ijse.posbackend.Dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto implements ItemStatus {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;
    private String category;
    private String description;
    private String img;

   public ItemDto(String name,double price,int quantity,String category,String description,String imgSrc){
        this.name = name;
        this.price =price;
        this.stockQuantity =quantity;
        this.category =category;
        this.description =description;
        this.img =imgSrc;
    }

    public ItemDto(String id,String name,int quantity ){
       this.id=id;
        this.name = name;
        this.stockQuantity =quantity;
    }
}

