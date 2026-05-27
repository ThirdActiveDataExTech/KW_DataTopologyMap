# Kware ThirdEye — Kubernetes 설치 및 운영 매뉴얼

> **대상 독자**: 쿠버네티스 기본 명령어(`kubectl`)를 사용할 수 있는 인프라 담당자  
> **최종 수정일**: 2026-05-11  
> **버전**: 0.2.0

---

## 목차

1. [시스템 개요](#1-시스템-개요)
2. [사전 요구사항](#2-사전-요구사항)
3. [공통 기초 설정](#3-공통-기초-설정)
4. [옵션 A — 로컬 스토리지 배포](#4-옵션-a--로컬-스토리지-배포)
5. [옵션 B — NFS 스토리지 배포](#5-옵션-b--nfs-스토리지-배포)
6. [배포 검증](#6-배포-검증)
7. [운영 가이드](#7-운영-가이드)
8. [완전 초기화 (삭제) 가이드](#8-완전-초기화-삭제-가이드)
9. [트러블슈팅](#9-트러블슈팅)
10. [부록 — 파일 상세 설명](#10-부록--파일-상세-설명)

---

## 1. 시스템 개요

### 아키텍처

```
┌─────────────────────────────────────────────────────┐
│                  Kubernetes Cluster                  │
│                                                      │
│   ┌──────────────────┐    ┌──────────────────────┐  │
│   │  thirdeye-app    │    │  thirdeye-db-0       │  │
│   │  (Deployment)    │───▶│  (StatefulSet)       │  │
│   │  Spring Boot     │    │  PostgreSQL 14.5     │  │
│   │  Port: 9094      │    │  Port: 5432          │  │
│   └───────┬──────────┘    └───────┬──────────────┘  │
│           │                       │                  │
│   ┌───────▼──────────┐    ┌──────▼───────────────┐  │
│   │  app-data-pvc    │    │  db-data-final-      │  │
│   │  (앱 데이터)      │    │  thirdeye-db-0       │  │
│   │  /app/data       │    │  (DB 데이터)          │  │
│   └──────────────────┘    └──────────────────────┘  │
│                                                      │
│         ▼ Storage: thirdeye-storage-class ▼          │
│   ┌──────────────────────────────────────────────┐  │
│   │  [옵션 A] hostPath (로컬)                     │  │
│   │  [옵션 B] NFS (172.30.1.1:/home/nfs_data)    │  │
│   └──────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
```

### 외부 접속 포트

| 서비스 | NodePort | 내부 포트 | 설명 |
| :--- | :--- | :--- | :--- |
| Web App | `30094` | `9094` | 브라우저 접속 |
| PostgreSQL | `30543` | `5432` | DBeaver 등 DB 툴 |

### 배포 전략 선택

| 항목 | 옵션 A (로컬) | 옵션 B (NFS) |
| :--- | :--- | :--- |
| **용도** | 단일 노드 테스트, PoC | 다중 노드 운영 환경 |
| **데이터 위치** | 노드 로컬 디스크 | 외부 NFS 서버 |
| **노드 이동** | 불가 (노드에 고정) | 가능 (어떤 노드든 마운트) |
| **설치 난이도** | 낮음 | 보통 (NFS 서버 구성 필요) |
| **파일** | `10-storage-local.yaml` | `11-storage-nfs.yaml` |

---

## 2. 사전 요구사항

### 필수 환경

- Kubernetes 클러스터 (v1.24 이상 권장)
- `kubectl` CLI 도구
- 클러스터 내 이미지 레지스트리 접근 가능 (`rnd.kware.co.kr`)

### NFS 옵션 추가 요구사항

- NFS 서버 (예: `172.30.1.1`)
- `helm` CLI 도구
- 마스터/워커 노드에 `nfs-common` 패키지 설치

---

## 3. 공통 기초 설정

> 스토리지 유형에 관계없이 **반드시 먼저 실행**해야 합니다.
> 이 자원들은 Kustomize가 아닌 수동으로 관리됩니다.

### 3-1. 네임스페이스 생성

```bash
kubectl apply -f k8s/00-namespace.yaml
```

**확인:**
```bash
kubectl get ns thirdeye
# NAME       STATUS   AGE
# thirdeye   Active   5s
```

### 3-2. 시크릿 생성

시크릿에는 DB 비밀번호가 포함되어 있습니다.
운영 환경에서는 반드시 `01-secret.yaml`의 비밀번호를 변경한 후 적용하십시오.

```bash
kubectl apply -f k8s/01-secret.yaml
```

**확인:**
```bash
kubectl get secret thirdeye-db-secret -n thirdeye
# NAME                 TYPE     DATA   AGE
# thirdeye-db-secret   Opaque   2      5s
```

> ⚠️ **주의**: 네임스페이스와 시크릿은 `kubectl delete -k k8s/` 명령으로 삭제되지 않습니다.
> 이는 의도된 설계이며, 앱을 재배포해도 기초 인프라가 유지됩니다.

---

## 4. 옵션 A — 로컬 스토리지 배포

> 단일 노드 테스트 환경에 적합합니다.
> 데이터는 노드의 `/mnt/data/thirdeye/` 경로에 저장됩니다.

### 4-1. 스토리지 생성

```bash
kubectl apply -f k8s/10-storage-local.yaml
```

이 파일은 다음 4개의 자원을 생성합니다:
- `local-app-data-pv` (PV) — 앱 데이터용 물리 볼륨
- `local-db-data-pv` (PV) — DB 데이터용 물리 볼륨
- `app-data-pvc` (PVC) — 앱 데이터 요청
- `db-data-final-thirdeye-db-0` (PVC) — DB 데이터 요청

**확인:**
```bash
kubectl get pv,pvc -n thirdeye
# PV의 STATUS가 'Bound', PVC의 STATUS가 'Bound' 인지 확인
```

### 4-2. 애플리케이션 배포

```bash
kubectl apply -k k8s/
```

### 4-3. 배포 확인

[6. 배포 검증](#6-배포-검증) 섹션으로 이동하여 상태를 확인합니다.

---

## 5. 옵션 B — NFS 스토리지 배포

> 다중 노드 운영 환경에 적합합니다.
> 데이터는 NFS 서버의 공유 디렉토리에 저장됩니다.

### 5-1. NFS 서버 구성

NFS로 사용할 서버에서 다음 스크립트를 실행합니다.

```bash
# NFS 서버(예: 172.30.1.1)에서 실행
./k8s/script-nfs-server.sh
```

이 스크립트는 다음을 수행합니다:
- `nfs-kernel-server` 패키지 설치
- `/srv/nfs/kubedata` 디렉토리 생성 및 권한 설정
- `/etc/exports` 등록 및 서비스 시작

> 💡 실제 환경에서는 NFS 경로가 다를 수 있습니다.
> 기본 프로비저너 스크립트는 `172.30.1.1:/home/nfs_data/kube`를 사용합니다.
> 환경에 맞게 스크립트 내 경로를 수정하십시오.

### 5-2. NFS 클라이언트 설치

**모든 쿠버네티스 노드**(마스터 + 워커)에서 클라이언트 패키지를 설치합니다.

```bash
# 각 노드에서 실행
./k8s/script-nfs-client.sh
```

**설치 확인** (각 노드에서):
```bash
showmount -e 172.30.1.1
# Export list for 172.30.1.1:
# /home/nfs_data/kube *
```

### 5-3. NFS 프로비저너 설치

Helm을 사용하여 쿠버네티스 클러스터에 NFS 프로비저너를 설치합니다.

```bash
./k8s/script-11-nfs-provisioner-install.sh
```

실행 중 NFS 서버 IP와 경로를 입력하라는 프롬프트가 나타납니다:
```
Enter NFS Server IP (default: 172.30.1.1): [Enter]
Enter NFS Export Path (default: /home/nfs_data/kube): [Enter]
```

**확인:**
```bash
# 프로비저너 포드가 Running 상태인지 확인
kubectl get pods -l app=nfs-subdir-external-provisioner
# NAME                          READY   STATUS    RESTARTS   AGE
# nfs-client-xxx                1/1     Running   0          30s

# StorageClass가 생성되었는지 확인
kubectl get sc thirdeye-storage-class
```

### 5-4. NFS 스토리지 생성

```bash
kubectl apply -f k8s/11-storage-nfs.yaml
```

**확인:**
```bash
kubectl get pvc -n thirdeye
# NAME                          STATUS   VOLUME     CAPACITY   ACCESS MODES
# app-data-pvc                  Bound    pvc-xxx    5Gi        RWX
# db-data-final-thirdeye-db-0   Bound    pvc-yyy    2Gi        RWO
```

### 5-5. 애플리케이션 배포

```bash
kubectl apply -k k8s/
```

### 5-6. 배포 확인

[6. 배포 검증](#6-배포-검증) 섹션으로 이동하여 상태를 확인합니다.

---

## 6. 배포 검증

아래 체크리스트를 순서대로 실행하여 모든 컴포넌트가 정상인지 확인합니다.

### 6-1. 포드 상태 확인

```bash
kubectl get pods -n thirdeye
```

**정상 출력 예시:**
```
NAME                             READY   STATUS    RESTARTS   AGE
thirdeye-db-0                    1/1     Running   0          2m
thirdeye-app-xxxxxxxxx-xxxxx     1/1     Running   0          2m
```

> ⚠️ DB 포드가 `Running`이 되기까지 1~2분 소요됩니다. (초기화 SQL 실행 시간)
> 앱 포드는 DB 포드가 완전히 기동된 후에 정상 작동합니다.

### 6-2. DB 초기화 로그 확인

```bash
kubectl logs thirdeye-db-0 -n thirdeye | tail -20
```

**정상 출력 (마지막 줄 부근):**
```
/usr/local/bin/docker-entrypoint.sh: running /docker-entrypoint-initdb.d/02_init_data.sql
...
PostgreSQL init process complete; ready for start up.
```

> ⚠️ 로그에 `ERROR`나 `constraint` 관련 메시지가 있다면 초기화에 실패한 것입니다.
> [9. 트러블슈팅](#9-트러블슈팅) 섹션을 참조하십시오.

### 6-3. 웹 접속 확인

브라우저에서 접속합니다:
```
http://<Node-IP>:30094
```

> `<Node-IP>`는 마스터 노드 또는 워커 노드의 IP 주소입니다.

### 6-4. DB 외부 접속 확인

DBeaver 등의 DB 클라이언트에서 접속합니다:

| 항목 | 값 |
| :--- | :--- |
| Host | `<Node-IP>` |
| Port | `30543` |
| Database | `postgres` |
| Username | `01-secret.yaml`의 `DB_USERNAME` 값 (기본: `ketiagc`) |
| Password | `01-secret.yaml`의 `DB_PASSWORD` 값 (기본: `ketiagc123!`) |

---

## 7. 운영 가이드

### 7-1. 앱 재시작 (데이터 보존)

DB 데이터를 유지하면서 앱만 다시 띄울 때 사용합니다.

```bash
# 방법 1: 롤링 재시작 (무중단)
kubectl rollout restart deployment/thirdeye-app -n thirdeye

# 방법 2: DB까지 포함한 전체 재기동
kubectl delete -k k8s/
kubectl apply -k k8s/
```

### 7-2. DB만 재시작

```bash
# StatefulSet의 포드를 삭제하면 자동으로 재생성됩니다.
kubectl delete pod thirdeye-db-0 -n thirdeye
```

> ⚠️ DB 포드 삭제 시 기존 데이터는 PVC에 보존됩니다.
> 단, 초기화 SQL은 **최초 1회만** 실행됩니다 (PostgreSQL의 initdb 특성).

### 7-3. SQL 스크립트 수정 후 반영

`db-02-init-data.sql` 등의 SQL 파일을 수정한 경우, ConfigMap을 갱신해야 합니다.

```bash
# 1. 기존 앱/DB 삭제
kubectl delete -k k8s/

# 2. PVC 삭제 (DB 데이터 초기화 필요 시)
kubectl delete pvc db-data-final-thirdeye-db-0 -n thirdeye

# 3. 기존 ConfigMap 삭제
kubectl delete configmap thirdeye-db-init-script -n thirdeye --ignore-not-found

# 4. 재배포 (새 ConfigMap + 새 DB 초기화)
kubectl apply -k k8s/
```

> ⚠️ **중요**: PVC를 삭제하지 않으면 PostgreSQL은 이미 초기화된 데이터 디렉토리를 감지하여
> `docker-entrypoint-initdb.d/` 내의 SQL을 **다시 실행하지 않습니다**.

### 7-4. 앱 이미지 업데이트

새 버전의 Docker 이미지가 레지스트리에 푸시된 경우:

```bash
# 30-application.yaml의 image 태그를 수정한 후
kubectl apply -k k8s/

# 또는 이미지 태그가 동일한 경우 (latest 등)
kubectl rollout restart deployment/thirdeye-app -n thirdeye
```

---

## 8. 완전 초기화 (삭제) 가이드

### 8-A. 로컬 스토리지 환경 삭제

```bash
# 1. 앱 및 DB 삭제
kubectl delete -k k8s/

# 2. PV/PVC 삭제
kubectl delete -f k8s/10-storage-local.yaml

# 3. (선택) 잔류 PV 확인 및 삭제
kubectl get pv | grep thirdeye
kubectl delete pv <PV-NAME>    # 남아있는 경우

# 4. (선택) 노드의 물리 데이터 삭제
sudo rm -rf /mnt/data/thirdeye/*
```

### 8-B. NFS 스토리지 환경 삭제

> ⚠️ **삭제 순서가 매우 중요합니다.**
> 순서를 지키지 않으면 자원이 `Terminating` 상태에서 무한 대기할 수 있습니다.

```bash
# 1단계: 앱 및 DB 삭제 (가장 먼저!)
kubectl delete -k k8s/

# 2단계: PVC 삭제 (앱이 완전히 종료된 후)
kubectl delete -f k8s/11-storage-nfs.yaml

# 3단계: NFS 프로비저너 삭제 (모든 PVC가 삭제된 후)
helm uninstall nfs-client

# 4단계: 잔류 PV 확인 및 강제 삭제
kubectl get pv
kubectl delete pv <PV-NAME>    # Released 상태인 PV가 있으면 삭제

# 5단계: NFS 서버의 물리 데이터 삭제 (최종)
# NFS 서버(172.30.1.1)에서 실행:
sudo rm -rf /home/nfs_data/kube/*
```

### 기초 자원 삭제 (필요 시)

네임스페이스와 시크릿을 완전히 제거하려면:

```bash
kubectl delete -f k8s/01-secret.yaml
kubectl delete -f k8s/00-namespace.yaml
```

---

## 9. 트러블슈팅

### 9-1. 포드가 `Pending` 상태에서 멈춤

**원인**: PVC가 `Bound` 되지 않았거나, 스토리지가 생성되지 않음.

```bash
# PVC 상태 확인
kubectl get pvc -n thirdeye

# 상세 원인 확인
kubectl describe pvc app-data-pvc -n thirdeye
kubectl describe pod thirdeye-db-0 -n thirdeye
```

**해결**:
- 로컬 환경: `10-storage-local.yaml`이 정상적으로 적용되었는지 확인
- NFS 환경: NFS 프로비저너 포드가 `Running`인지, NFS 서버가 접근 가능한지 확인

### 9-2. 포드가 `CreateContainerConfigError` 상태

**원인**: 시크릿(`thirdeye-db-secret`)이 네임스페이스에 없음.

```bash
# 시크릿 존재 여부 확인
kubectl get secret thirdeye-db-secret -n thirdeye
```

**해결**:
```bash
kubectl apply -f k8s/01-secret.yaml
```

### 9-3. DB 초기화 SQL 에러

**원인**: `db-02-init-data.sql`의 SQL 문법 오류 또는 제약 조건 위반.

```bash
# DB 포드 로그에서 에러 확인
kubectl logs thirdeye-db-0 -n thirdeye | grep -i error
```

**해결**: SQL 파일 수정 후 [7-3. SQL 스크립트 수정 후 반영](#7-3-sql-스크립트-수정-후-반영) 절차를 따릅니다.

### 9-4. `bigint = character varying` 타입 에러

**원인**: PostgreSQL의 엄격한 타입 비교 정책으로 인한 에러.

**현재 상태**: `db-02-init-data.sql`에 암시적 캐스트 설정이 포함되어 있어 자동으로 해결됩니다.
만약 에러가 발생한다면 DB를 완전 초기화하여 캐스트 설정이 다시 적용되도록 합니다.

### 9-5. 마스터 노드 IP로 접속 불가

**원인**: `kube-proxy`의 iptables 규칙 손상 (네트워크 설정 변경 후 주로 발생).

**확인**:
```bash
# 마스터 노드에서 직접 테스트
curl -v localhost:30094

# Connection refused → kube-proxy 문제
# Timeout → 방화벽 문제
```

**해결**:
```bash
# 방법 1: kube-proxy 재시작
kubectl delete pod -n kube-system -l k8s-app=kube-proxy

# 방법 2: 마스터 노드 재부팅 (가장 확실)
sudo reboot
```

### 9-6. PVC 삭제 후 PV가 `Released` 상태로 남음

**원인**: `reclaimPolicy`가 `Retain`으로 설정된 PV는 수동 삭제가 필요합니다.

```bash
kubectl get pv
kubectl delete pv <PV-NAME>
```

---

## 10. 부록 — 파일 상세 설명

### DB 초기화 SQL 실행 순서

PostgreSQL 컨테이너가 최초 기동될 때, `/docker-entrypoint-initdb.d/` 디렉토리의 파일을
**파일명의 알파벳/숫자 순서**로 자동 실행합니다.

| 순서 | 파일 | 역할 |
| :--- | :--- | :--- |
| 1 | `00_create_user.sh` | 환경변수를 읽어 `ketiagc` 사용자 생성 및 권한 부여 |
| 2 | `01_schema.sql` | `k_thirdeye` 스키마 및 전체 테이블 구조 생성 |
| 3 | `02_init_data.sql` | 초기 데이터 입력 (워크플레이스, 메뉴, 사용자 등) |
| 4 | `99_reassign_owner.sql` | 모든 DB 객체의 소유권을 `ketiagc`에게 이전 |

> 💡 `02_init_data.sql`에는 외래키 제약 조건을 일시 비활성화하는 설정이 포함되어 있습니다.
> (`SET session_replication_role = 'replica'`)
> 이는 데이터 입력 순서에 관계없이 안전하게 시딩하기 위한 표준 기법입니다.
> 데이터 입력이 완료되면 자동으로 다시 활성화됩니다.

### Kustomization 구성

`kustomization.yaml`은 다음을 통합 관리합니다:
- **네임스페이스 자동 주입**: 모든 자원에 `namespace: thirdeye` 적용
- **리소스 배포**: `20-postgres.yaml`, `30-application.yaml`
- **ConfigMap 자동 생성**: SQL 파일들을 `thirdeye-db-init-script` ConfigMap으로 묶음

### 시크릿 값 참조

| 키 | 사용처 | 기본값 |
| :--- | :--- | :--- |
| `POSTGRES_SUPER_PASSWORD` | PostgreSQL `postgres` 슈퍼유저 비밀번호 | `superadmin_password_here` |
| `DB_USERNAME` | 애플리케이션이 사용할 DB 사용자명 | `ketiagc` |
| `DB_PASSWORD` | 애플리케이션이 사용할 DB 비밀번호 | `ketiagc123!` |

---

*본 문서는 Kware ThirdEye K8s 배포를 위한 공식 운영 매뉴얼입니다.*
