# 변경 이력

형식: 날짜별로 추가·변경·수정·보안·배포를 구분해 적는다. 커밋 단위는 기능 하나이며 항목은 커밋 제목을 요약한 것이다.

## 2026-10-22

### 변경
- 사용처 없는 spring-batch 의존성 제거
- springfox 제거, API 문서는 springdoc(swagger-ui) 하나로 통일. 노출 여부 키는 `springdoc.api-docs.enabled`

## 2026-10-21

### 추가
- 요청 추적 ID(MDC requestId, 응답 헤더 `X-Request-Id`)를 모든 로그 줄에 표시
- 처리되지 않은 예외의 공통 처리(화면 500 페이지, API JSON 고정 문구+requestId). 예외 메시지·스택은 로그에만 기록
- 모비젠 API 연결 실패(연결 거부·연결 타임아웃·호스트 조회 실패) 시 재시도(기본 2회, `mobigen.retry.max-attempts`). 읽기 타임아웃·HTTP 오류는 재시도하지 않음

### 설정
- `logback-spring.xml` 추가: 콘솔 + 파일 일자 롤링(30일 보관, 100MB 분할, 3GB 상한, gz 압축)
- multipart 업로드 한도를 `UPLOAD_MAX_FILE_SIZE`/`UPLOAD_MAX_REQUEST_SIZE`로 조정, 초과 시 413 응답

### 배포
- Dockerfile: `JAVA_OPTS` 환경변수(기본 MaxRAMPercentage=75), `/actuator/health` HEALTHCHECK, 런타임 이미지에 curl 설치
- docker-compose: 로컬용 PostgreSQL(`thirdeye-db`, k8s 초기화 스크립트 재사용)·Redis(`thirdeye-redis`) 서비스, prod 프로파일 `DB_URL`·`REDIS_NODES` 환경변수 덮어쓰기

### 보안
- 응답 헤더 Referrer-Policy(strict-origin-when-cross-origin), Content-Security-Policy-Report-Only 추가

## 2026-10-20

### 수정
- 메타데이터 파일 미리보기 실패 시 null 반환으로 발생하던 NPE
- 로그인 실패 처리에서 분기 후 응답이 중복 전송되던 문제
- 존재하지 않는 아이디로 로그인 시 NPE 대신 UsernameNotFoundException 처리
- 파일 존재 확인(checkFile)의 미등록 fileId null 가드
- 모비젠 연동 로그의 API 코드 태그 오기
- LocaleChangeInterceptor 중복 등록 제거
- DatasetService의 미사용 서비스 주입 제거
- ApiResponse의 미사용 필드 제거
- 원본데이터 업로드 multipart 텍스트 파트 Content-Type 명시
- 비local 프로파일 보안 설정에도 인증 제공자 명시 등록

### 보안
- 임시 파일 다운로드의 경로 탐색 차단
- 회원가입 비밀번호 검증을 비밀번호 변경과 동일한 규칙으로 통일
- 로그인 IP 지역 조회 외부 호출에 타임아웃 적용
- 세션 쿠키 HttpOnly·SameSite 적용, Secure는 `COOKIE_SECURE`로 제어
- CORS를 와일드카드+자격증명 조합에서 허용 출처 목록(`ALLOWED_ORIGINS`) 방식으로 변경
- SecurityRoles.PERMIT_ALL_URLS를 final로 고정

### 설정
- prod 프로파일 `mobigen.url` 추가(`MOBIGEN_URL`)
- 세션 저장소(Redis/메모리)를 `SESSION_STORE_TYPE`으로 선택
- kube 프로파일 `MOBIGEN_URL`을 Deployment 환경변수로 주입(내부 IP 기본값 제거)
- Actuator health 노출과 프로브용 인증 예외
- 모비젠 API 타임아웃을 `mobigen.timeout.*` 설정 키로 분리
- graceful shutdown
- HikariCP 최대 연결 수를 `DB_POOL_SIZE`로 조정

### 배포
- Dockerfile: bootWar만 생성해 WAR 다중 매칭 빌드 실패 방지, `BUILD_PROFILE` 인자
- docker-compose: 환경변수 항목과 `.env.example`
- k8s: 프로브, 리소스 requests/limits, Redis Service·Deployment, 메일 계정 Secret 주입, 로그 경로를 PVC 안으로
- NFS 프로비저너 스크립트의 경로·스토리지클래스 불일치 수정
- 설치 매뉴얼 오기 수정, 구 매니페스트 스냅샷 `k8s/k8s.zip` 제거

### 문서
- `.editorconfig`, `README.md`, `CHANGELOG.md` 추가

## 2026-09-11
- Eclipse 프로젝트명을 kware_thirdeye로 통일

## 2026-09-10
- 설정·문서에서 DB/메일 자격증명 제거, 환경변수 참조로 전환, k8s 샘플 시크릿 더미화
