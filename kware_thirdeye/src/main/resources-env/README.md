# 환경별 설정과 자격증명 주입

`application-<profile>.yml`에는 계정·비밀번호를 직접 쓰지 않는다. 아래 환경변수를 실행 환경에서 주입한다.

| 환경변수 | 용도 | 사용 프로파일 |
| :--- | :--- | :--- |
| `DB_USERNAME` | 애플리케이션 DB 계정 | dev, local, prod, dev-mobigen, kube |
| `DB_PASSWORD` | 애플리케이션 DB 비밀번호 | dev, local, prod, dev-mobigen, kube |
| `MAIL_USERNAME` | 초대 메일 발송 SMTP 계정 | dev, local, dev-mobigen, kube |
| `MAIL_PASSWORD` | SMTP 비밀번호(앱 비밀번호) | dev, local, dev-mobigen, kube |

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
