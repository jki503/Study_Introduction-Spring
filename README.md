---
title: 스프링 입문 - 스프링 부트, 웹 MVC, DB 접근 기술
category: Introduction Spring
tags: 벡엔드 개발, MVC, API 설계, JPA, DB
Author: 정
---

## **My Goal**

---

- 마구잡이 개발이 아닌 의도가 들어간 개발 지향
- API 설계 능력 습득
- 상황에 맞는 적절한 전략 습득

</br>

## **Table of Contents**

---

- [**My Goal**](#my-goal)
- [**Table of Contents**](#table-of-contents)
  - [**프로젝트 생성**](#프로젝트-생성)
  - [**라이브러리**](#라이브러리)
  - [**h2 설치**](#h2-설치)
  - [**reference**](#reference)
  - [빌드하고 실행](#빌드하고-실행)
- [**@RequestBody**](#requestbody)
  - [원리](#원리)
- [**@Test**](#test)
- [**@Component, @Controller, @RestController, @Service, @Repository**](#component-controller-restcontroller-service-repository)
  - [**차이점?**](#차이점)
- [**웹 MVC**](#웹-mvc)
  - [**@Transactional**](#transactional)
  - [**JPA와 스프링 데이터 JPA**](#jpa와-스프링-데이터-jpa)
    - [**JPA**](#jpa)
    - [**스프릥 데이터 JPA**](#스프릥-데이터-jpa)
  - [**AOP**](#aop)
    - [**@Aspect**](#aspect)
    - [**@Around**](#around)
    - [**빈 순환 참조 문제**](#빈-순환-참조-문제)

</br>

### **프로젝트 생성**

---

</br>

- Prjoect : Gradle Prject
- Spring boot : 2.6.2
- Language : JAVA
- Packaging : Jar
- JAVA ver. : 11
- Project Meta
  - groupId : hello
  - artifact : project(hello-spring)
  - Dependencies: Spring web, Thymeleaf, lombok

</br>

### **라이브러리**

</br>

- spring-boot-starter-web
  - spring-boot-starter-tomcat
  - spring-webmvc
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
- spring-boot
- spring-boot-starter-jdbc : jdbc 연동
- spring-boot-starter-data-jpa : JPA 라이브러리 제공

  - spring-core

- spring-boot-starter-logging

  - logback, slf4j

- spring-boot-starter-test
  - junit
  - mockito
  - assertj
  - spring-test

</br>

### **h2 설치**

</br>

- version : 1.4.200 선택
- [h2 아카이브 다운로드](http://www.h2database.com/html/download-archive.html)
- open 후 디렉토리 이동 : cd ~/h2/bin
- bin $ chmod 755 h2.sh 권한 변경
- bin $ ./h2.sh 실행
- home에서 /test.mv.db 파일 생성 확인.
- jdbc url 소켓 이용해서 접근 -> jdbc:h2:tcp://localhost/~/test

### **reference**

- [스프링 부트게 제공하는 Welcome Page](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-welcome-page)
- [thymeleaf 공식 사이트](https://www.thymeleaf.org/)
- [스프링 공식 튜토리얼](https://spring.io/guides/gs/serving-web-content/)
- [스프링부트 메뉴얼](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-template-engines)

### 빌드하고 실행

- project directory에서 ./gradlew build
- cd build/libs
- java -jar hello-spring-0.0.1-SNAPSHOT.jar 명령어
- 실행 확인

## **@RequestBody**

> - viewResolver 사용 X
> - @ResponseBody 사용 후 객체 반환 JSON 변환

### 원리

> - HTTP Body에 문자 내용을 직접 반환
> - HttpMessageConverter 동작
> - 기본 문자 처리 : StringHttpMessageConverter
> - 기본 객체 처리 : MappingJackson2HttpMessageConverter
> - byte 처리 등 여러 HttpMessageConverter 기본 등록 되어 있음.

</br>

> client HTTP Accept 헤더와 서버 컨트롤러 반환 타입 둘을 조합해서 HttpMessageConverter가 선택 됨.

</br>

## **@Test**

> - 요즘 Spring은 Junit5 통해 Test 간편
> - Test는 각 테스트 순서에 영향을 받아서 안됨.
>   - 메모리를 갱신 안했다던가, 초기 생성에 의존하는 실수 X
> - 테스트 시작 전에 실행해야할 메소드는 -> @BeforeEach
> - 테스트 종료 후에 실행해야할 메소드 -> @AfterEach

## **@Component, @Controller, @RestController, @Service, @Repository**

> - 모두 Bean을 등록하는 Annotation

### **차이점?**

> - 어노테이션의 기능상 차이점은 없다.
> - 단 bean의 역할에 대해 `명시성` 부여.

</br>

- @Component

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	String value() default "";

}
```

</br>

- @Controller

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
```

</br>

- @Service

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
```

</br>

- @Repository

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}

```

</br>

> - 주석에서 볼 수 있듯 논리적 component 이름
> - MVC 패턴 및 비즈니스 로직이 커질 때 명시성 장점 부여

</br>

- @RestController

```java

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 * @since 4.0.1
	 */
	@AliasFor(annotation = Controller.class)
	String value() default "";

}

```

> - 단 RestController에는 @ResponseBody가 붙어있다.
> - 엄연히 Controller와 다른 용도임으로 주의.

</br>

## **웹 MVC**

> - Controller에서 요청 Mapping 후
> - Service에서 지정한 acition 실행
> - Service의 action에 따라 repository에서 DB CRUD 진행

> Controller가 정적 파일보다 우선 순위가 높다.
>
> - 즉 스프링 컨테이너에서 controller가 반환하는 view를 먼저 찾은 후, 없으면 정적 파일 반환.

</br>

### **@Transactional**

</br>

```java

@Transacitonal
public class MemberService{

}

```

</br>

> - 스프링에서 선언적 트랜잭션 방식으로 메소드, 클래스, 인터페이 위에 추가하여 사용하는 방식
> - 트랜잭션 기능이 포함된 프록시 객체가 자동으로 commit 혹은 rollback 수행. (추후 수정 계획 - 2022.1.21)

</br>

### **JPA와 스프링 데이터 JPA**

</br>

#### **JPA**

</br>

> - JPA는 사용자 EntityManager를 이용하여 SQL 대체
> - EntityManager로 쿼리 but id로 찾는 조회 이외에는 createQuery로 JPQL

</br>

#### **스프릥 데이터 JPA**

> - Repository를 interface로 선언한후 JpaRepository 상속받아 이용.
> - Repository 구현체는?
>   - JpaRepository를 상속 받으면
>   - 스프링 컨테이너가 알아서 구현체를 만든 후 스프링 빈에 자동 등록
>   - 즉 스프링 Jpa가 세팅 후 사용자는 사용만 하면 됨.

</br>

### **AOP**

</br>

> - 공통 관심사항과 핵심관심사항(비즈니스 로직)을 분리해서 보고싶을때 사용. ex) method 수행시간
> - 비즈니스 로직과 공통 관심 사항이 섞이는 것을 방지.
> - 공통 관심 사항은 수동 등록으로 빈 설정 권장.
>   - 특별한 빈으로 명시화

</br>

#### **@Aspect**

</br>

> 공통관심 대상이 되는 클래스임을 명시

</br>

#### **@Around**

</br>

> AOP 클래스의 함수에서 적용 대상이 될 파일 타켓팅

```java
@Aspect
public class TimeTraceAop {
      @Around("execution(* com.hello.hellospring..*(..))")
      public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

	}
}

```

#### **빈 순환 참조 문제**

- 시나리오
  - AOP를 @Component로 등록하였을 경우 -> OK
  - but Config.class에 @Bean으로 등록할 경우 -> X

```java
// com.hello.hellosprin.SpringConfig.java
@Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

```

- 원인

  - TimeTraceAop의 @Around 타겟이 SpringConfig를 포함.
  - 이때 발생하는 문제는 SpringConfig의 timeTraceAop 함수도 AOP로 처리.
  - 이것이 TimeTraceAop를 생성하는 코드임으로 -> 순환 참조 문제...

- 해결 방법 고민

  - dependency 끊어라 -> 독립적인 빈임으로 dependency를 끊을 관계가 없다.
  - allow-circular-reference -> default로 막았는데 권한 허가?

- 해결
  - SpringConfig는 설정 class로 공통관심사 제외해도 문제X
  - Around에서 SpiringConfig Target 제외!
  - @Around("execution(com.hello.hellospring..\_(..))&&!target(com.hello.hellospring.SpringConfig)")으로 SpringConfig 제외.

</br>

> 흔히 겪을 수 있는 문제임으로 등록된 bean 관계 파악하기.
> 순환 참조가 1개일 경우 @configuration도 bean임을 항상 유의.
