package spring.spring_basic.point;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.spring_basic.member.Grade;
import spring.spring_basic.member.Member;

public class FixPointPolicyTest {
    FixPointPolicy fixPointPolicy = new FixPointPolicy();

    @Test
    @DisplayName("정적 포인트 적립 테스트")
    void vip_o() {
        Member member = new Member(1L, "memberA", Grade.BASIC);

        int point = fixPointPolicy.bonusPoint(member, 10000);

        Assertions.assertThat(point).isEqualTo(5);
    }
}
