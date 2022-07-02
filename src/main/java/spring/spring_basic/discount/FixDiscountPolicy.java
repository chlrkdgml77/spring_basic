package spring.spring_basic.discount;

import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private int fixDiscountAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return fixDiscountAmount;
        }
        else {return 0;}
    }
}
