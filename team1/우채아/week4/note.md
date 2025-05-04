# 4주차 학습내용 정리
## 7-1 H2 데이터베이스 설치
- h2 설치 후 웹 콘솔로 DB 접속

## 7-2 순수 JDBC
- 예전에 사용하던 방식. 최근엔 이렇게 사용하지 않음
- 자바는 DB 연결하려면 JDBC driver 필수 -> build.gradle에 의존성 추가
- DataSource application.properties 에 정의 후 Repository에서 쿼리 일일히 짜야 함

## 7-3 스프링 통합 테스트
- DB까지 연결한 통합 테스트 클래스 작성
- service/MemberServiceIntegrationTest.java
- @SpringBootTest : 스프링 컨테이너와 테스트를 같이 실행
- @Transactional : 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백 (DB에 테스트 데이터 남지 않음)

## 7-4 스프링 JdbcTemplate
- hello/hello_spring/repository/JdbcTemplateMemberRepository.java
- 순수 JDBC보다는 간단하지만 쿼리 작성해야 함

## 7-5 JPA
- 쿼리 작성 필요 없음 (일부 메서드 제외)
- Member Entity에 @Entity, @Id, @GeneratedValue 추가

## 7-6 스프링 데이터 JPA
- 쿼리 작성 필요없음
- JPA 기술을 스프링으로 한 번 감싸서 제공하는 기술
- 구현체 만들 필요 없이 인터페이스만으로 기능 구현이 가능
- findByName과 같은 메서드가 JPQL을 자동으로 짜줌
- 일반 JPA보다 더 간단
