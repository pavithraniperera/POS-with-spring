package lk.ijse.posbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;
    private String img;

   public ItemDto(String name,double price,int quantity,String category,String description,String imgSrc){
        this.name = name;
        this.price =price;
        this.quantity =quantity;
        this.category =category;
        this.description =description;
        this.img =imgSrc;
    }

    public ItemDto(int id,String name,int quantity ){
       this.id=id;
        this.name = name;
        this.quantity =quantity;
    }
}

