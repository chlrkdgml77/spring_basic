package spring.spring_basic;

import spring.spring_basic.discount.DiscountPolicy;
import spring.spring_basic.discount.FixDiscountPolicy;
import spring.spring_basic.discount.RateDiscountPolicy;
import spring.spring_basic.member.MemberRepository;
import spring.spring_basic.member.MemberService;
import spring.spring_basic.member.MemberServiceImpl;
import spring.spring_basic.member.MemoryMemberRepository;
import spring.spring_basic.order.OrderService;
import spring.spring_basic.order.OrderServiceImpl;
import spring.spring_basic.point.FixPointPolicy;
import spring.spring_basic.point.PointPolicy;
import spring.spring_basic.point.RatePointPolicy;

public class AppConfig {
//    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository());
//    }
//
//    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//    }
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy(), pointPolicy()) ;
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    //추가
    public PointPolicy pointPolicy() {
        return new RatePointPolicy();
    }
}
