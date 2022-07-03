package spring.spring_basic.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.spring_basic.AutoAppConfig;
import spring.spring_basic.member.MemberService;
import spring.spring_basic.point.PointPolicy;

public class AutoAppConfigTest {
    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        PointPolicy pointPolicy = ac.getBean(PointPolicy.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        Assertions.assertThat(pointPolicy).isInstanceOf(PointPolicy.class);
    }
}
