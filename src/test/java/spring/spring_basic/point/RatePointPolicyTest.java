package spring.spring_basic.point;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

public class RatePointPolicyTest {
    RatePointPolicy ratePointPolicy = new RatePointPolicy();

    @Test
    @DisplayName("정률 포인트 적립 테스트")
    void vip_o() {
        Member member = new Member(1L, "memberA", Grade.VIP);

        int point = ratePointPolicy.bonusPoint(member, 10000);

        Assertions.assertThat(point).isEqualTo(100);
    }
}
