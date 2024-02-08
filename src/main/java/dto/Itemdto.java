package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Itemdto {
    private String code;
    private  String name;
    private  double price;
    private int qty;

}
