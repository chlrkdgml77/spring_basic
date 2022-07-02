<스프링-기본>

- 프로젝트 이름: spring_basic
- 사용 언어: Java 17
- 프레임워크: SpringBoot 2.7.1
- DB: x
- 기능: 회원 등록 및 조회, 상품 주문(할인률 존재) 
- 목적: 좋은 객체지향 설계를 지향하고, 스프링을 통해 의존관계 주입 방법에 대해 알아보기
- 필수사항: 설계 후 구현 전 테스트 코드로 테스트

ver_01 <기능 구현을 위한 설계(only 자바)>
 
  - 비즈니스 요구사항과 설계
    [회원]
    회원을 가입하고 조회할 수 있다.
    회원은 일반과 VIP 두 가지 등급이 있다.
    회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

    [주문과 할인 정책]
    회원은 상품을 주문할 수 있다.
    회원 등급에 따라 할인 정책을 적용할 수 있다.
    할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수
    있다.)
    할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을
    미루고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)
  
  - 구성
    1) 회원 도메인 설계
    [클라이언트] -> [회원 서비스] -> [회원 저장소]

    [MemberService(interface)] <구현= [MemberServiceImpl] -> [MemberRepository(interface)] <구현= [MemoryMemberRepository or DbMemberRepository]

    2) 주문과 할인 도메인 설계
    [클라이언트] -주문생성> [주문 서비스] -회원조회> [회원 저장소]
                                         -할인적용> [할인 정책]

    [OrderService(interface)] <구현- [OrderServiceImpl] -> [MemberRepository(interface)] <구현= [MemoryMemberRepository or DbMemberRepository]
                                                        -> [DiscountPolicy(interface)] <구현- [FixDiscountPolicy or RateDiscountPolicy]
  
