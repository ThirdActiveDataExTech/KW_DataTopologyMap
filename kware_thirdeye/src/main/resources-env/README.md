# 환경별 설정과 자격증명 주입

`application-<profile>.yml`에는 계정·비밀번호를 직접 쓰지 않는다. 아래 환경변수를 실행 환경에서 주입한다.

| 환경변수 | 용도 | 사용 프로파일 |
| :--- | :--- | :--- |
| `DB_USERNAME` | 애플리케이션 DB 계정 | dev, local, prod, dev-mobigen, kube |
| `DB_PASSWORD` | 애플리케이션 DB 비밀번호 | dev, local, prod, dev-mobigen, kube |
| `MAIL_USERNAME` | 초대 메일 발송 SMTP 계정 | dev, local, dev-mobigen, kube |
| `MAIL_PASSWORD` | SMTP 비밀번호(앱 비밀번호) | dev, local, dev-mobigen, kube |
| `SESSION_STORE_TYPE` | 세션 저장소: `redis`(Redis 클러스터, 다중 인스턴스) 또는 `none`(메모리, 단일 인스턴스). 기본 prod=redis, kube=none | prod, kube |
| `COOKIE_SECURE` | 세션 쿠키 Secure 속성(HTTPS 종단이면 `true`). 기본 false | 전체 |
| `ALLOWED_ORIGINS` | CORS 허용 출처 목록(쉼표 구분). 기본은 로컬 개발 주소 | 전체 |
| `DB_POOL_SIZE` | HikariCP 최대 연결 수(기본 2) | 전체 |
| `DB_URL` | JDBC URL 전체 덮어쓰기(docker-compose 의 로컬 DB 등). 기본은 프로파일 파일의 값 | prod |
| `REDIS_NODES` | Redis 노드 목록(쉼표 구분). 1개면 단일 인스턴스, 2개 이상이면 클러스터 | prod, kube |
| `UPLOAD_MAX_FILE_SIZE` / `UPLOAD_MAX_REQUEST_SIZE` | multipart 업로드 파일·요청 한도(기본 10MB, 예 `50MB`). TUS 대용량 업로드에는 적용되지 않음 | 전체 |
| `MOBIGEN_URL` | 모비젠 API base URL (예: `http://<host>:<port>/v1`) | prod(필수), kube |

- kube 프로파일은 `k8s/01-secret.yaml`의 Secret에서 주입된다. 그 외 `DB_HOST`, `DB_PORT`, `DB_NAME`, `MAIL_HOST`, `MAIL_PORT`, `REDIS_NODES`, `MOBIGEN_URL`, `MAIL_SENDER`는 `application-kube.yml`의 기본값을 참조한다.
- 로컬 실행 예 (Windows cmd):

```cmd
set DB_USERNAME=...
set DB_PASSWORD=...
set MAIL_USERNAME=...
set MAIL_PASSWORD=...
gradlew bootRun -Pprofile=local
```

- IDE에서는 실행 구성의 환경변수 항목에 같은 값을 넣는다. 값을 저장소에 커밋하지 않는다.
- 모비젠 API 타임아웃은 환경변수가 아니라 설정 키 `mobigen.timeout.connect-ms`(기본 5000), `mobigen.timeout.read-write-ms`(10000), `mobigen.timeout.response-sec`(10)로 조정한다. 연결 실패 재시도 횟수는 `mobigen.retry.max-attempts`(기본 2, 0이면 재시도 없음).
