package spring.spring_basic.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

@DisplayName("할인 서비스 테스트")
public class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("vip는 10% 할인 적용")
    void vip_o() {
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        int discount = discountPolicy.discount(member, 20000);

        Assertions.assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("vip가 아니면 할인 적용 x")
    void vip_x() {
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);

        int discount = discountPolicy.discount(member, 20000);

        Assertions.assertThat(discount).isEqualTo(0);
    }
}
