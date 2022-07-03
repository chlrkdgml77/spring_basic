package spring.spring_basic.point;

import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

public class RatePointPolicy implements PointPolicy{

    private int rateBonusPoint = 1;

    @Override
    public int bonusPoint(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * rateBonusPoint / 100;
        }
        else {return 0;}
    }
}
