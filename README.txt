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
  
  - 테스트 기록
    회원 등록 기능 테스트 -> MemberServiceTest
    주문 서비스 기능 테스트 -> OrderServiceTest
    할인 서비스 기능 테스트 -> RateDiscountPolicyTest
    
  - 현재 코드의 문제점
    할인 정책을 바꿀려면 = new FixDiscountPolicy() 에서 = new RateDiscountPolicy()로 코드를 변경해야한다. -> OCP 위반
    현재 코드는 인터페이스에만 의존하는 것이 아닌 구현체에도 의존하고 있다(Fix 클래스인지 Rate 클래스인지) -> DIP 위반
    
  - ver_02 목표
    - 해결방법
      코드가 오직 인터페이스에만 의존하도록 설계를 변경한다.
       -> 하지만 이러면 구현체가 없어지므로 코드를 실행할 수 없게 된다.
       -> 따라서 다른 누군가 인터페이스에 구현객체를 생성하고 주입해줘야한다.
       => 구현 객체를 생성하고 연결하는 책임만을 갖는 별도의 클래스를 생성하자

  
  
ver_02 <관심사의 분리_설정파일 작성(only 자바)>

  - 설정파일 작성
    ver_01에서 나타난 문제를 해결하기 위해 관심사를 분리하여 구현 객체를 생성하고 연결하는 책임만을 갖는 별도의 클래스 AppConfig 작성
    AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
      MemberServiceImpl
      MemoryMemberRepository
      OrderServiceImpl
      FixDiscountPolicy or RateDiscountPolicy
    AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다.
      MemberServiceImpl MemoryMemberRepository
      OrderServiceImpl MemoryMemberRepository , FixDiscountPolicy or RateDiscountPolicy
      
   
   - 구성
     [AppConfig] -생성> [MemberServiceImpl] -구현> [MemberService(interface)]
                                            -연결> [MemberRepository(interface)]
                 -생성> [MemoryMemberRepository] -구현> [MemberRepository(interface)]
     MemberServiceImpl은 이제 구현체에 의존하는 것이 없어졌다. 오직 인터페이스에만 의존하고 어떤 구현 객체가 들어올지는 AppConfig가 결정함
     즉, MemberServiceImpl에 주입되는 객체는 AppConfig에서 결정하고 생성한 MemoryMemberRepository다.
     
     [AppConfig] -생성> [OrderServiceImpl] -구현> [OrderService(interface)]
                                           -연결> [MemberRepository(interface)]
                                           -연결> [DiscountPolicy(interface)]
                 -생성> [MemoryMemberRepository] -구현> [MemberRepository(interface)]
                 -생성> [FixDiscountPolicy] -구현> [DiscountPolicy(interface)]
     OrderServiceImpl은 이제 구현체에 의존하는 것이 없어졌다. 오직 인터페이스에만 의존하고 어떤 구현 객체가 들어올지는 AppConfig가 결정함
     즉, OrderServiceImpl에 주입되는 객체는 AppConfig에서 결정하고 생성한 MemoryMemberRepository다.
     즉, OrderServiceImpl에 주입되는 객체는 AppConfig에서 결정하고 생성한 FixDiscountPolicy다.
     
   - ver_01과 차이점
     Impl 클래스에서 new로 구현체를 생성하고 연결한것을 -> 생성자를 정의하여 AppConfig에서 생성자 주입으로 연결, AppConfig에서 구현체 생성(결정)
     Test에서 new로 구현체를 생성하고 연결한것을 -> @BeforeEach(Test이전에 무조건 실행)를 통해 AppConfig를 생성 및 이용하여 주입해줌
     ***기존에는 Fix에서 Rate로 바꿀려면 기존 코드를 바꿔줘야했다 -> AppConfig에서 Fix대신 Rate로 바꿔주면 끝난다.

   - 테스트 기록
     AppConfig를 적용하여..회원 등록 기능 테스트 -> MemberServiceTest
                           주문 서비스 기능 테스트 -> OrderServiceTest

   - ver_03 목표
     기존의 순수 자바로만 구성되어있던 코드들을 스프링을 이용하여 좀 더 효율적이고 좀 더 객체지향적으로 바꾸기
     
     
ver_03 <스프링 컨테이너와 스프링 빈>
   
   - 자바에서 스프링으로..
     기존의 AppConfig를 스프링 기반으로 변경하기
     @Configuration -> 설정정보 파일이라는 것을 알려준다.
     @Bean -> 각 메서드에 붙여주면 스프링 컨테이너에 스프링 빈으로 등록된다.
     => 이제는 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.
     
   - 테스트 기록
     등록된 모든 빈 찾기 -> ApplicationContextInfoTest
     내가 등록한 빈 찾기 -> ApplicationContextInfoTest
     빈 이름으로 특정 빈 찾기 -> ApplicationContextBasicFindTest
     빈 이름 없이 타입으로만 찾기 -> ApplicationContextBasicFindTest
     
   - 문제점
     기존의 설계 방식은 AppConfig를 호출할때마다 인스턴스를 새로 생성한다.
     이러면 많은 사람이 호출하면 너무 많은 인스턴스가 생성되고 소멸된다.
  
   - ver_04 목표
     - 해결방법
       싱글톤 패턴을 이용하여 객체가 딱 1개만 생성되고, 그것을 공유하도록 설계하면된다.


ver_04 <싱글톤 컨테이너>

   - 싱글톤 패턴의 문제점
     ver_03의 문제점을 해결하기 위해 싱글톤 패턴을 적용하면 되지만, 싱글톤 패턴에는 문제가 있다.
     -> 코드가 길어진다
     -> 클라이언트가 구현체에 의존하게 된다.
     -> 테스트하기 어렵다
     -> private 생성자이므로 자식 클래스를 만들기 어렵다
     그래서 나온것이 스프링의 싱글톤 컨테이너다.
     
   - 싱글톤 컨테이너
     스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도 기본적으로 싱글톤 컨테이너 역할을 수행한다.
     따라서 위의 문제들이 모두 해결된다.
     단, 주의점이 몇가지 존재한다
     -> 무상태로 설계해야 한다
      -> 특정 클라이언트에 의존적인 필드가 있으면 안된다.
      -> 특정 클라이언트가 값을 바꿀수 있는 필드가 있으면 안된다. -> 멀티 쓰레드 문제 발생
      -> 즉 지역변수, 파라미터등을 활용해서 설계해야 한다.
   
   - 포인트 적립 기능 구현
     강의에는 없는 기능이지만 공부를 위해 추가
     VIP -> 정률 포인트 적립 기능
     BASIC -> 정적 포인트 적립 기능
     
     [OrderService(interface)] <구현- [OrderServiceImpl] -> [MemberRepository(interface)] <구현= [MemoryMemberRepository or DbMemberRepository]
                                                        -> [DiscountPolicy(interface)] <구현- [FixDiscountPolicy or RateDiscountPolicy]
                                                        -> [PointPolicy(interface)] <구현- [FixPointPolicy or RatePointPolicy]
     
     [AppConfig] -생성> [OrderServiceImpl] -구현> [OrderService(interface)]
                                           -연결> [MemberRepository(interface)]
                                           -연결> [DiscountPolicy(interface)]
                                           -연결> [PointPolicy(interface)]
                 -생성> [MemoryMemberRepository] -구현> [MemberRepository(interface)]
                 -생성> [FixDiscountPolicy] -구현> [DiscountPolicy(interface)]
                 -생성> [FixPointPolicy or RatePointPolicy] -구현> [PointPolicy(interface)]
    
    Order 클래스에 bonusPoint 필드 추가(생성자에도 추가)
    OrderServieceImpl 클래스에 PoinPolicy 필드 추가, creatOrder 메서드에 bonusPoint 값 받고 리턴해주는 로직 추가
    AppConfig에 PointPolicy 생성하도록(rate로..), 그후 OrderService에 연결
   - 의문
     좋은 객체지향 설계로 이루어진건지 의문이 생김 -> 나중에 다시 고치기
   
   - ver_05 목표
     AppConfig를 만들어 수동으로 의존관계 주입을 수행해왔는데 스프링에서는 자동 의존관계 주입을 제공한다.
     기존의 의존관계를 자동 의존관계로 
   
   - 테스트 기록
     주문시 포인트 적립되는지 테스트 -> OrderServiceTest
     정률, 정적 포인트 적립 테스트 -> FixPointPolicyTest, RatePointPolicyTest
     
