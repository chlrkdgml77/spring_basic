package spring.spring_basic.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void join() {
        Member member = new Member(1L, "memberA", Grade.VIP);

        memberService.join(member);
        Member findMember = memberService.findByMember(1L);

        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
