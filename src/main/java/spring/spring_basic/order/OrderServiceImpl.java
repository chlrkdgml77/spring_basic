package spring.spring_basic.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.spring_basic.discount.DiscountPolicy;
import spring.spring_basic.discount.FixDiscountPolicy;
import spring.spring_basic.member.Member;
import spring.spring_basic.member.MemberRepository;
import spring.spring_basic.member.MemoryMemberRepository;
import spring.spring_basic.point.PointPolicy;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //추가
    private final PointPolicy pointPolicy;

//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order creatOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        //추가
        int bonusPoint = pointPolicy.bonusPoint(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice, bonusPoint);
    }
}
