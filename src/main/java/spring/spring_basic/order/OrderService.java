package spring.spring_basic.order;

public interface OrderService {
    Order creatOrder(Long memberId, String itemName, int itemPrice);
}
