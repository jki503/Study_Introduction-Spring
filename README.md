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
  - [**reference**](#reference)
  - [빌드하고 실행](#빌드하고-실행)
- [**@RequestBody**](#requestbody)
    - [원리](#원리)

</br>

### **프로젝트 생성**

---

- Prjoect : Gradle Prject
- Spring boot : 2.6.2
- Language : JAVA
- Packaging : Jar
- JAVA ver. : 11
- Project Meta
  - groupId : hello
  - artifact : project(hello-spring)
  - Dependencies: Spring web, Thymeleaf, lombok

### **라이브러리**

- spring-boot-starter-web
  - spring-boot-starter-tomcat
  - spring-webmvc
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
- spring-boot

  - spring-core

- spring-boot-starter-logging

  - logback, slf4j

- spring-boot-starter-test
  - junit
  - mockito
  - assertj
  - spring-test

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

#### 원리

> - HTTP Body에 문자 내용을 직접 반환
> - HttpMessageConverter 동작
> - 기본 문자 처리 : StringHttpMessageConverter
> - 기본 객체 처리 : MappingJackson2HttpMessageConverter
> - byte 처리 등 여러 HttpMessageConverter 기본 등록 되어 있음.

</br>

> client HTTP Accept 헤더와 서버 컨트롤러 반환 타입 둘을 조합해서 HttpMessageConverter가 선택 됨.
