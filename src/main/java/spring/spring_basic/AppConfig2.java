package spring.spring_basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.spring_basic.discount.DiscountPolicy;
import spring.spring_basic.discount.RateDiscountPolicy;
import spring.spring_basic.member.MemberRepository;
import spring.spring_basic.member.MemberService;
import spring.spring_basic.member.MemberServiceImpl;
import spring.spring_basic.member.MemoryMemberRepository;
import spring.spring_basic.order.OrderService;
import spring.spring_basic.order.OrderServiceImpl;

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
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
