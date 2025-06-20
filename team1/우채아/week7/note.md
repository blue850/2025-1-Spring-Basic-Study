# 7주차 학습내용 정리
## 비즈니스 요구사항과 설계
- 주의: 프로젝트 구조를 위해 스프링을 사용한 것, 코드는 순수 자바로만 구현할 것임
- 회원 도메인 요구사항
    - 회원을 가입하고 조회할 수 있다.
    - 회원은 일반과 VIP 두 가지 등급이 있다.
    - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)
- 주문과 할인 정책
  - 회원은 상품을 주문할 수 있다.
  - 회원 등급에 따라 할인 정책을 적용할 수 있다.
  - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다.  
  최악의 경우 할인을 적용하지 않을 수도 있다. (미확정)

## 회원 도메인 설계, 개발
- member 패키지에 구현
- 인터페이스 MemberRepository -> 구현체 MemoryMemberRepository
- 인터페이스 MemberService -> 구현체 MemberServiceImpl

## 회원 도메인 실행과 테스트
- org.assertj.core.api.Assertions 클래스 이용하여 객체 비교
- 문제점
  - MemberServiceImpl이 MemberRepository, MemoryMemberRepository 둘에게 의존

## 주문과 할인 도메인 설계, 개발
- 인터페이스 OrderService -> 구현체 OrderSeriveImpl
- 인터페이스 DiscountPolicy -> 구현체 FixDiscountPolicy

## 새로운 할인 정책 개발
- 정률 할인 정책으로 변경하기
- RateDiscountPolicy 클래스 작성 후 테스트

## 새로운 할인 정책 적용과 문제점
- 할인 정책을 변경하려면 OrderServiceImpl의 코드를 수정해야 함
- 현재 코드는 인터페이스와 구현체 모두에게 의존하고 있음 
- 이 문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해줘야 함

## 관심사의 분리
- 이전 코드는 한 클래스가 여러 책임을 가지고 있다는 문제점이 있음  
-> AppConfig 라는 클래스를 새로 생성하여, 구현체를 생성하고 연결하는 역할을 분리하자

## 새로운 구조와 할인 정책 적용
- 처음으로 돌아가서 정액 할인 정책을 정률% 할인 정책으로 변경해보자.
- FixDiscountPolicy -> RateDiscountPolicy
- AppConfig를 생성했으므로 AppConfig의 코드만 수정하면 됨

## 좋은 객체 지향 설계의 5가지 원칙의 적용
- SRP : 한 클래스는 하나의 책임만 가져야 함
  - AppConfig 는 구현 객체 생성 및 연결만 담당
  - 클라이언트 객체는 실행만 담당
- DIP : 추상화에 의존해야지, 구체화에 의존하면 안됨 
  - AppConfig 생성을 통해 클라이언트가 인터페이스에만 의존하도록 함
- OCP : 확장에는 열려있으나 변경에는 닫혀있어야 함
  - 할인 정책을 변경하더라도 AppConfig 외에 수정해야 하는 코드가 없음

## IoC, DI, 컨테이너
### IoC 제어의 역전
- 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것
- AppConfig가 제어 흐름에 대한 권한을 모두 가지고 있음
- 구현 객체는 자신의 로직을 실행하는 역할만 담당
### DI 의존관계 주입
- 정적인 클래스 의존관계
  - import 코드만 보고 판단할 수 있는 의존관계
- 동적인 객체 인스턴스 의존관계
  - 애플리케이션 실행 시점에 생성된 객체 인스턴스의 참조가 연결된 의존관계
### IoC 컨테이너, DI 컨테이너
- AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것
- 최근에는 주로 DI 컨테이너 라고 함

## 스프링으로 전환하기
- AppConfig에 @Configuration, @Bean 추가
- ApplicationContext : 스프링 컨테이너
- 스프링 컨테이너에 등록된 객체 : 스프링 빈 
- `ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);`  
  AppConfig에 있는 구성 정보가 스프링 컨테이너에 등록됨
- `MemberService memberService = applicationContext.getBean("memberService", MemberService.class);`  
  스프링 빈으로 등록된 객체를 꺼내오는 코드
