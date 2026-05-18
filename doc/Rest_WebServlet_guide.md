# WebServlet + RestController 동시 사용 설정

Spring Boot 환경에서 `@WebServlet`과 `@RestController`를 함께 사용할 때 필요한 설정과 적용 방법 정리

---

## 1. @WebServlet을 Spring Boot에서 동작시키기 위한 기본 조건

`@WebServlet`은 Spring이 관리하는 컴포넌트가 아니기 때문에, Spring Boot가 이를 인식하려면 main 클래스에 `@ServletComponentScan`을 추가해야 한다.

```java
@SpringBootApplication
@ServletComponentScan  // 없으면 @WebServlet이 무시됨
public class LibrarySystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibrarySystemApplication.class, args);
    }
}
```

---

## 2. Spring Security가 서블릿 URL을 JWT 없이 차단

`SecurityConfig`에 `.anyRequest().authenticated()` 설정이 있어, 허가 목록에 없는 모든 URL은 JWT 토큰 없이 접근하면 403이 반환된다.

현재 허가된 URL:
```
/users/login
/users/login.do
/users/register.do
/spring/users/login
/error
```

허가되지 않은 서블릿 URL (→ JWT 없이 접근 시 403):
```
POST /users/login.do   (로그인 처리)
POST /users/register.do (회원가입 처리)
/book/*
/reserve/*
```

**수정 방법**: 서블릿 URL들은 서블릿에서 자체적으로 세션 인증을 처리하므로 `SecurityConfig`에서 해당 URL들을 `permitAll()` 설정

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/users/**").permitAll()   // 서블릿이 세션으로 직접 처리
    .requestMatchers("/book/**").permitAll()    // 서블릿이 처리
    .requestMatchers("/reserve/**").permitAll() // 서블릿이 세션으로 직접 처리
    .requestMatchers("/spring/users/login").permitAll()
    .requestMatchers("/error").permitAll()
    .anyRequest().authenticated())             // /spring/** REST API는 JWT 필요
```

---

## 3. @WebServlet에서 Spring Bean 사용하기

`@WebServlet`은 Spring이 생성하지 않으므로 `@Autowired`가 동작하지 않는다.
`HttpServlet` 대신 Spring의 `HttpServletBean`을 상속하면 `@Autowired`가 자동으로 동작한다.

```java
import org.springframework.web.servlet.HttpServletBean;

@WebServlet("/example/*")
public class ExampleServlet extends HttpServletBean {  // HttpServlet 대신 HttpServletBean으로

    @Autowired
    private SomeService someService;  // 자동 주입됨
}
```

---

## 4. 세션 vs JWT 인증 공존 구조

| 구분 | 컨트롤러 | 인증 방식 |
|---|---|---|
| 서블릿 (`@WebServlet`) | BookController, ReservationController, UsersController | HttpSession (서블릿이 직접 관리) |
| Spring REST (`@RestController`) | UsersSpringController | JWT (Spring Security 필터) |
