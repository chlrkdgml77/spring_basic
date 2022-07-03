package spring.spring_basic.point;

import spring.spring_basic.member.Member;

public interface PointPolicy {
    int bonusPoint(Member member, int price);
}
