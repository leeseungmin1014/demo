항목,상세 내용 및 구현 기술
주요 목표,"블로그 게시판에 새로운 필드를 추가하고, DB 연동 및 조회 기능을 구현."
핵심 기술,"Spring Data JPA, Thymeleaf, DB(board 테이블)"
데이터베이스,"Board 테이블 생성 및 필드 추가 (ID, 제목, 내용, 작성자(user), 작성일(newdate), 조회수(count), 좋아요(likec))."
JPA Repository,"BoardRepository 인터페이스를 생성하고 JpaRepository<Board, Long>을 상속받아 DB 연동 준비."
Service Layer,BlogService.java에 findAll()(전체 목록 조회) 및 findById(id)(특정 글 조회) 메서드를 Board 객체를 사용하도록 수정.
Controller,"/board_list 맵핑을 추가하여 새로운 게시판 목록(board_list.html)을 연결하고, 전체 게시글 목록을 모델에 추가하여 전달."
View (Thymeleaf),"board_list.html을 생성/수정하여 추가된 필드(작성자, 작성일, 조회수, 좋아요)를 테이블에 출력하고 , 글 제목에 /board_view/{id} 링크를 추가하여 상세 조회 페이지로 이동하도록 구현."
상세 보기 및 버튼,/board_view/{id} 맵핑을 추가하고 board_view.html을 통해 글 내용과 수정/삭제 버튼을 표시하도록 구현.
연습 문제,"게시판 글 수정 기능 구현 (/board_edit 맵핑 및 board_edit.html 작성, BlogService.java의 update 메서드 수정)."

항목,상세 내용 및 구현 기술
주요 목표,블로그 게시판에 검색 기능과 페이징 기능을 추가하여 웹 서버 성능을 최적화하고 사용자 경험을 개선.
핵심 기술,"Spring Data JPA PagingAndSorting, @RequestParam, Pageable, PageRequest"
웹 서버 성능 최적화,"사용자 경험을 저해하는 3초 이상의 지연을 방지하고, 데이터 규모에 따라 최적화 전략 (소규모: 단순 검색, 중규모: 페이징+인덱스 최적화, 대규모: 전문 검색 엔진 도입)을 적용할 필요성 학습."
검색 기능 (프론트),"board_list.html 상단에 검색창을 추가하고, /board_list로 keyword를 GET 방식으로 전송하도록 폼 구현."
검색/페이징 (Controller),"BlogController.java의 /board_list 맵핑을 수정하여 @RequestParam으로 page(기본값 0)와 keyword(기본값 """")를 받아 처리. PageRequest.of(page, 3)을 사용하여 한 페이지당 게시글 수를 3개로 설정."
검색/페이징 (Service & Repository),"BlogService.java에 findAll(Pageable)(기본 전체 목록) 및 searchByKeyword(String, Pageable)(키워드 검색) 메서드를 구현. BoardRepository.java에 findByTitleContainingIgnoreCase(String, Pageable) 인터페이스를 정의하여 LIKE 검색(대소문자 무시)을 지원."
페이징 (View),"board_list.html 하단에 페이징 기능을 추가하여 전체 페이지 수(totalPages), 현재 페이지(currentPage), 키워드(keyword)를 기반으로 페이지 이동 링크(<<, 1, 2, >>)를 동적으로 출력하도록 구현."
연습 문제,"게시글 목록의 ID 대신 현재 페이지 기준의 글 번호(순서) 를 출력하도록 컨트롤러와 View를 수정하고 , 글 삭제 버튼 기능을 구현 (/api/board_delete 맵핑 추가)."

항목,상세 내용 및 구현 기술
주요 목표,"Spring Security를 도입하여 회원가입 및 로그인 기능을 구현하고, 비밀번호 암호화 처리를 적용."
핵심 기술,"Spring Security, JPA @Entity, @Repository, @Service, BCryptPasswordEncoder"
Spring Security 도입,"pom.xml에 spring-boot-starter-security 의존성을 추가. 기본 로그인 화면 비활성화 및 보안 설정을 위한 SecurityConfig.java 클래스를 생성하고 @Configuration, @EnableWebSecurity 적용."
암호화 설정,SecurityConfig.java에 BCryptPasswordEncoder를 PasswordEncoder Bean으로 등록하여 비밀번호 암호화 지원.
회원 DB 모델,"Member.java Entity를 생성하여 이름, 이메일, 비밀번호, 나이, 전화번호, 주소 필드를 정의하고 Builder 패턴 적용. 이메일은 unique = true로 설정하여 중복 가입을 방지."
회원 리포지토리,"MemberRepository.java를 생성하고 JpaRepository를 상속하며, 이메일 조회를 위한 전용 메서드 findByEmail(String email)을 정의."
회원 서비스,MemberService.java에 @Transactional 및 @RequiredArgsConstructor 적용. validateDuplicateMember 메서드로 이메일 중복 체크 구현. saveMember 메서드에서 비밀번호를 passwordEncoder.encode()로 암호화하여 저장.
회원가입 화면/DTO,AddMemberRequest DTO를 정의하여 회원가입 폼 데이터를 받도록 구성. join_new.html 및 join_end.html을 작성하고 /join_new 및 /api/members 맵핑 구현.
로그인 기능,"login.html을 작성하고 /member_login 맵핑 구현. MemberController.java에 /api/login_check POST 맵핑을 구현하여 MemberService.loginCheck(email, rawPassword)를 호출하도록 처리."
로그인 검증 (Service),MemberService.java에 loginCheck 메서드를 구현하여 이메일 존재 여부 확인 및 passwordEncoder.matches()를 사용하여 평문 비밀번호와 암호화된 비밀번호 일치 여부를 검증.
연습 문제,"spring-boot-starter-validation 의존성을 추가하고 , DTO 필드에 @NotBlank, @Email, @Pattern, @Size, @Min, @Max 등의 @어노테이션을 사용하여 입력값 필터링 및 검증을 적용. 컨트롤러와 서비스 메서드에 @Valid와 @Validated를 사용하여 검증 동작 활성화."

항목,상세 내용 및 구현 기술
주요 목표,"로그인 상태 유지를 위한 스프링 세션 관리 기능을 추가하고, 기본적인 보안 필터 설정을 적용."
핵심 기술,"Spring Session (HttpSession), JSESSIONID, HttpServletRequest, HttpServletResponse, SecurityConfig"
세션 기본 이해,"쿠키는 사용자 PC에, 세션은 서버에 정보를 저장하여 상태 정보를 관리 (로그인 정보, 장바구니 상품 등). 서버는 세션 ID를 생성하고 이를 쿠키(JSESSIONID)로 사용자 브라우저에 저장하여 세션 유지."
로그인 세션 생성,"로그인 성공 시 ( /api/login_check POST 맵핑), UUID.randomUUID().toString()으로 임의의 고유 ID를 생성하여 session.setAttribute(""userId"", ...)에 저장. 또한, 실제 사용자 이메일을 세션에 session.setAttribute(""email"", email)로 추가 저장하여 활용."
게시판 접근 제어,BlogController.java의 /board_list 맵핑에서 HttpSession 객체를 받아 세션에 userId가 없으면 /member_login 페이지로 리다이렉트하여 로그인 체크 기능 구현.
사용자 정보 출력,"로그인 성공 후 게시판 화면(board_list.html) 상단에 세션에 저장된 이메일을 이용해 ""이메일님 환영합니다."" 문구와 로그아웃 버튼을 출력하도록 구현."
단일 사용자 로그인 처리,"/api/login_check 맵핑에서 기존 세션이 존재하면 session.invalidate()로 무효화하고 JSESSIONID 쿠키를 삭제(MaxAge=0)한 후, 새로운 세션을 생성하여 단일 사용자 로그인만 유지되도록 구현."
로그아웃 기능,"/api/logout 맵핑을 구현하여 세션을 무효화(session.invalidate())하고, JSESSIONID 쿠키를 삭제하여(MaxAge=0) 로그아웃 처리 후 /login 페이지로 리다이렉트."
세션 보안 설정,"SecurityConfig.java에 세션 관련 보안 설정 추가: invalidSessionUrl, maximumSessions(1)(사용자당 세션 최대 1개), maxSessionsPreventsLogin(true)(동시 세션 제한). 응답 헤더에 XSS-Protection 등을 추가 설정."
세션/쿠키 속성,application.properties에 server.servlet.session.timeout=300s(세션 만료 시간 5분) 및 server.servlet.session.cookie.secure=true(HTTPS 전송 활성화) 설정 추가.
연습 문제,"게시글 추가 시, 작성자를 현재 로그인한 사용자의 이메일로 저장하도록 수정하고 , 게시글 내용 보기 페이지에서 글의 작성자만 수정/삭제 버튼을 보이도록 조건부 출력 기능 구현."

항목,상세 내용 및 구현 기술
주요 목표,"개인 포트폴리오 웹사이트의 UI/UX를 완성하고, 방문자 메일 업로드 기능 및 GitHub Pages 배포를 진행하여 최종 개인 포트폴리오를 완성."
핵심 기술,"파일 I/O (BufferedWriter), @Value, spring.servlet.multipart.location, GitHub Pages"
UI/UX 개선,"메인 화면(index.html)의 연락처, 주소, SNS 정보를 한글로 수정 및 최하단 연락하기 폼을 수정하여 메일 제목, 메시지 등을 한글로 표시."
메일 업로드 기능 (Controller),"FileController.java를 생성하고 /upload-email POST 맵핑을 구현. @Value(""${spring.servlet.multipart.location}"")을 사용하여 파일 저장 경로를 주입받음."
메일 업로드 기능 (로직),"전달받은 이메일, 제목, 메시지를 이용하여 업로드 폴더에 '이메일.txt' 파일로 저장하는 로직을 구현. BufferedWriter를 사용하여 파일에 내용을 쓰도록 처리."
파일 업로드 설정,"application.properties에 파일 업로드 설정을 추가: spring.servlet.multipart.enabled=true, 파일 저장 경로 지정(spring.servlet.multipart.location), 최대 파일/요청 크기 설정."
보안 설정 수정 (CSRF 비활성화),개발 편의를 위해 SecurityConfig.java에서 CSRF 보호를 비활성화(csrf.disable()) 처리 (실제 환경에서는 권장되지 않음).
맵 (주소) 정보 추가,구글 맵 또는 기타 지도 서비스에서 원하는 위치를 검색하고 '지도 퍼가기' 기능을 통해 얻은 <iframe> 소스코드를 index.html의 맵 영역에 삽입하여 주소 위치 시각화.
GitHub Pages 배포,"스프링 부트 프로젝트가 아닌 정적 웹사이트 폴더 구조(MYSITE 폴더 생성 및 templates, static 파일 복사)로 변경. 해당 폴더를 Git 저장소로 초기화, 커밋, GitHub에 공개 저장소로 게시. GitHub 저장소의 Settings → Pages 메뉴에서 main 브랜치를 선택하고 /(root) 경로로 설정하여 정적 웹사이트 호스팅 활성화."
최종 점검,"전체 프로젝트 소스코드 정리, 들여쓰기, 주석 처리, README.MD 상세 작성."
연습 문제,"다중 사용자 로그인을 위해 로그인 시 사용자마다 다른 이름의 세션을 생성하도록 수정하고, 로그아웃 시 현재 사용자의 세션과 쿠키만 삭제하도록 수정. 파일 업로드 시 동일 파일 이름의 경우 다른 이름으로 업로드되도록 수정."
