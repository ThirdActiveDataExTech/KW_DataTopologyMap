# kware_thirdeye

KETI AGC 데이터셋 포털(ThirdEye). 모비젠(Mobigen) 외부 API와 연동해 메타데이터·원본데이터·패키지를 관리·진열·배포하는 Spring Boot 웹 애플리케이션이다.

## 기술 스택

| 구분 | 내용 |
| :--- | :--- |
| 런타임 | Java 11, Spring Boot 2.7, Undertow |
| 데이터 | PostgreSQL(스키마 `k_thirdeye`), MyBatis, HikariCP |
| 화면 | Thymeleaf + Layout Dialect |
| 인증 | Spring Security(세션 기반), 세션 저장소는 메모리 또는 Redis |
| 연동 | 모비젠 API(WebClient), TUS 대용량 업로드 |
| 배포 | Docker(bootWar), Kubernetes(`k8s/`) |

## 빌드와 실행

프로파일은 `local`, `dev`, `prod`, `dev-mobigen`, `kube` 다섯 가지이며 `src/main/resources-env/<profile>/application-<profile>.yml`에 있다. 빌드 시 `-Pprofile`로 리소스에 포함할 프로파일을 고른다.

```cmd
set DB_USERNAME=...
set DB_PASSWORD=...
gradlew bootRun -Pprofile=local
```

실행 가능한 WAR 생성:

```cmd
gradlew bootWar -Pprofile=prod
```

계정·비밀번호·외부 주소는 설정 파일에 쓰지 않고 환경변수로 주입한다. 필요한 변수 목록과 기본값은 [`src/main/resources-env/README.md`](src/main/resources-env/README.md)에 있다. 변수를 주입하지 않으면 기동에 실패한다.

### 테스트

```cmd
gradlew test
```

단위 테스트는 `src/test/java` 아래에 대상 클래스와 같은 패키지로 두며(`<클래스명>Test`), DB·외부 서비스 없이 실행된다. 보고서는 `build/reports/tests/test/index.html`.

## 컨테이너·Kubernetes

- Docker: `docker/Dockerfile`(2단계 빌드, bootWar만 복사), `docker/docker-compose.yml`(환경변수는 `docker/.env`, 예시는 `docker/.env.example`). compose 에는 로컬용 PostgreSQL(`thirdeye-db`, 최초 기동 시 `k8s/db-*` 스크립트로 초기화)과 Redis(`thirdeye-redis`)가 포함되며 외부 DB 를 쓰려면 `.env` 의 `DB_URL`·`REDIS_NODES` 를 지정한다.
- Kubernetes: `k8s/README.md`와 `k8s/doc/INSTALL_MANUAL.md`를 따른다. 시크릿(`k8s/01-secret.yaml`)의 값은 샘플이므로 적용 전에 교체한다.
- 상태 확인: `/actuator/health`(프로브용, 인증 불필요)

## 저장소 구조

```
src/main/java/cetus/      공통 프레임워크(DAO·서비스 추상, 설정, 유틸)
src/main/java/kware/      애플리케이션(apps/{mobigen, superrole, system, thirdeye}, common)
src/main/resources/       공통 설정, 템플릿, 정적 자원
src/main/resources-env/   프로파일별 설정
k8s/, docker/             배포 구성
libs/                     로컬 의존 jar(egovframework, TUS)
```

SQL Map XML(`*-postgresql-sqlmap.xml`)은 Java 소스 트리의 `domain/` 폴더에 DAO와 함께 둔다.

## 개발 규칙

- 커밋은 기능·수정 하나 단위로 나누고 메시지는 한국어로 무엇을 왜 바꿨는지 적는다.
- 자격증명·내부 주소·개인정보는 어떤 파일에도 커밋하지 않는다.
- 줄바꿈·들여쓰기 기준은 `.editorconfig`를 따른다.

변경 이력은 [`CHANGELOG.md`](CHANGELOG.md)에 기록한다.
