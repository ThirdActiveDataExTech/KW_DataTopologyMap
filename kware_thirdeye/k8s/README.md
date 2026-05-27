# Kware ThirdEye — Kubernetes 배포 가이드

본 디렉토리(`k8s/`)는 ThirdEye 시스템을 쿠버네티스 환경에 배포하기 위한 매니페스트와 스크립트를 포함합니다.
상세한 설치 절차는 **[INSTALL_MANUAL.md](./doc/INSTALL_MANUAL.md)** 를 참조하십시오.

---

## 📁 파일 구성

| 파일명 | 역할 | 비고 |
| :--- | :--- | :--- |
| `00-namespace.yaml` | 네임스페이스 생성 | 수동 적용 (최초 1회) |
| `01-secret.yaml` | DB 계정/비밀번호 시크릿 | 수동 적용 (최초 1회) |
| `10-storage-local.yaml` | 로컬 스토리지 PV/PVC | **옵션 A** — 단일 노드 테스트용 |
| `11-storage-nfs.yaml` | NFS 스토리지 PVC | **옵션 B** — 다중 노드 운영용 |
| `20-postgres.yaml` | PostgreSQL DB | Kustomize 관리 |
| `30-application.yaml` | Spring Boot App | Kustomize 관리 |
| `kustomization.yaml` | Kustomize 통합 배포 설정 | ConfigMap 자동 생성 포함 |
| `db-00-create-user.sh` | 환경변수를 통한 DB 사용자 생성 | 자동 실행 (환경변수 사용) |
| `db-01-schema.sql` | 스키마 및 테이블 생성 | 자동 실행 (initdb) |
| `db-02-init-data.sql` | 초기 데이터 시딩 | 자동 실행 (initdb) |
| `db-99-reassign-owner.sh` | 환경변수를 통한 소유권 이전 | 자동 실행 (환경변수 사용) |
| `script-nfs-server.sh` | NFS 서버 설치 스크립트 | 마스터 노드에서 실행 |
| `script-nfs-client.sh` | NFS 클라이언트 설치 스크립트 | 모든 노드에서 실행 |
| `script-11-nfs-provisioner-install.sh` | NFS 프로비저너 설치 | NFS 사용 시 실행 |

---

## ⚡ 빠른 시작 (Quick Start)

### 로컬 스토리지로 시작하기
```bash
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-secret.yaml
kubectl apply -f k8s/10-storage-local.yaml
kubectl apply -k k8s/
```

---

## 🌐 접속 정보

| 서비스 | 접속 주소 | 기본 계정 (Secret에서 변경 가능) |
| :--- | :--- | :--- |
| **Web App** | `http://<Node-IP>:30094` | admin / (초기화 데이터 참조) |
| **PostgreSQL** | `<Node-IP>:30543` | `ketiagc` / `ketiagc123!` |

- **Namespace**: `thirdeye`
- **모든 계정 정보는 `01-secret.yaml`에서 통합 관리됩니다.**

---

## 🔍 상태 확인

| 대상 | 명령어 | 정상 상태 |
| :--- | :--- | :--- |
| 저장소 연결 | `kubectl get pvc -n thirdeye` | 모든 PVC가 `Bound` |
| DB 가동 | `kubectl get pods -n thirdeye` | `thirdeye-db-0` → `1/1 Running` |
| 앱 가동 | `kubectl get pods -n thirdeye` | `thirdeye-app-xxx` → `1/1 Running` |
