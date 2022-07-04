package spring.spring_basic.point;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

@Component
@Qualifier("fixPointPolicy")
public class FixPointPolicy implements PointPolicy{

    private int fixPointAmount = 5;

    @Override
    public int bonusPoint(Member member, int price) {
        if (member.getGrade() == Grade.BASIC) {
            return fixPointAmount;
        }
        else {
            return 0;
        }
    }
}
