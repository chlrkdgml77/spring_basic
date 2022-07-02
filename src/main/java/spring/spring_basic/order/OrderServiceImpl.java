package spring.spring_basic.order;

import lombok.RequiredArgsConstructor;
import spring.spring_basic.discount.DiscountPolicy;
import spring.spring_basic.discount.FixDiscountPolicy;
import spring.spring_basic.member.Member;
import spring.spring_basic.member.MemberRepository;
import spring.spring_basic.member.MemoryMemberRepository;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order creatOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
