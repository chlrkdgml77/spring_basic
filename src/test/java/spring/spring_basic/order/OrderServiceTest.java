package spring.spring_basic.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.spring_basic.AppConfig;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;
import spring.spring_basic.member.MemberService;
import spring.spring_basic.member.MemberServiceImpl;

public class OrderServiceTest {
//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("OrderService 기능 테스트")
    void creatOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.creatOrder(memberId, "itemA", 20000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(2000);
    }
}
