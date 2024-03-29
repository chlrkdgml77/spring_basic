package spring.spring_basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
public class AppConfig2 {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy(), pointPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    //추가
    @Bean
    public PointPolicy pointPolicy() {
        return new RatePointPolicy();
    }
}
