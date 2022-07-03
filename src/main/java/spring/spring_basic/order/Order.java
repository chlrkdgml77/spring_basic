package spring.spring_basic.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private String itemName;
    private int itemPrice;
    private int discountPrice;
    //추가
    private int bonusPoint;

    public Order(Long id, String itemName, int itemPrice, int discountPrice, int bonusPoint) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
        this.bonusPoint = bonusPoint;
    }
}
