package spring.spring_basic.discount;

import org.springframework.stereotype.Component;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPresent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPresent / 100;
        }
        else {return 0;}
    }
}
