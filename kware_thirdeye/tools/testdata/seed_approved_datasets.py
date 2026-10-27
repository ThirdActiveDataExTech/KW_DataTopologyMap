#!/usr/bin/env python3
"""진열 데이터셋 시험 데이터 생성 (병원·의료 주제).

개발 DB(k_thirdeye) 에 진열 등록 데이터셋(cetus_approved_dataset + cetus_dataset_ui)을 넣는다.
목록·검색·페이징·성능 확인용이며, 생성 데이터임을 알 수 있게 metadata_id 는 TEST-HOSP- 로 시작하고
extra_json.addInfo 에 "데이터 구분: 테스트 데이터(생성)" 을 넣는다. 모비젠에는 존재하지 않는 ID 이므로
상세 화면의 모비젠 정보(파일·태그)는 비어 있고 제목은 extra_json.title 로 표시된다.

    python tools/testdata/seed_approved_datasets.py --count 10000            # 생성(이미 있으면 그 수까지 보충)
    python tools/testdata/seed_approved_datasets.py --count 100 --dry-run    # 넣지 않고 표본만 출력
    python tools/testdata/seed_approved_datasets.py --purge                  # TEST-HOSP- 데이터 전부 삭제

접속 정보: DB_USERNAME / DB_PASSWORD 환경변수, 없으면 --secret (기본 ./local-secret.properties) 에서 읽는다.
호스트·포트·DB 는 local 프로파일 기본값이며 인자로 바꿀 수 있다. 의존: pip install pg8000
"""
import argparse
import datetime as dt
import json
import os
import pathlib
import random
import sys

try:
    import pg8000.native
except ImportError:  # pragma: no cover
    sys.exit("pg8000 이 필요합니다: pip install pg8000")

PREFIX = "TEST-HOSP-"

# 카테고리명 → (표본 제목 목록, 조합용 주제 목록)
CATEGORIES = {
    "병원·의료기관": (
        ["전국 병원급 의료기관 현황", "세브란스병원 진료과별 외래환자 수", "강남세브란스병원 병상 운영 현황", "용인세브란스병원 진료과 개설 현황",
         "서울특별시 종합병원 소재지 및 병상 수", "상급종합병원 지정 현황", "요양병원 입원 병상 현황", "의원급 의료기관 진료과목 정보",
         "약국 운영시간 및 위치 정보", "치과병원 개설 현황", "한방병원 운영 현황", "연세의료원 산하 병원 진료 실적 요약", "SBSI 협력 의료기관 목록"],
        ["의료기관 개설 현황", "병상 운영 현황", "진료과목 정보", "외래환자 수", "입원환자 수", "의료기관 소재지 정보", "진료 실적 요약", "휴·폐업 의료기관 현황"],
    ),
    "진료·질병 통계": (
        ["다빈도 질병 진료 통계", "암 등록 통계(발생·생존율)", "고혈압·당뇨 진료 현황", "연령별 진료 인원 및 진료비", "수술 종류별 시행 건수",
         "세브란스병원 진료과별 평균 재원일수", "희귀질환 진료 현황", "정신건강 진료 통계", "소아청소년 질병 통계", "노인성 질환 진료 통계",
         "치과 진료 통계", "한방 진료 통계", "진료비 청구 통계(질병 분류별)"],
        ["진료 인원 통계", "진료비 통계", "재원일수 통계", "수술 건수 통계", "외래 진료 통계", "입원 진료 통계", "질병 분류별 통계", "진료 결과 통계"],
    ),
    "응급의료": (
        ["응급의료기관 현황", "응급실 병상 및 장비 현황", "구급차 운영 현황", "응급환자 이송 통계", "세브란스병원 응급진료센터 내원 통계",
         "심정지 환자 생존 통계", "중증외상센터 현황", "응급실 체류시간 통계", "야간·휴일 진료기관 현황", "자동심장충격기(AED) 설치 현황",
         "응급의료 취약지 현황", "소아 응급진료 현황", "재난의료 대응 자원 현황"],
        ["응급실 내원 통계", "구급 이송 통계", "응급의료 자원 현황", "응급환자 분류 통계", "응급실 체류시간", "응급의료기관 평가 결과"],
    ),
    "의약품·의료기기": (
        ["의약품 허가 정보", "의료기기 허가·인증 현황", "의약품 부작용 보고 통계", "마약류 취급 현황", "의약품 공급 내역 통계",
         "희귀의약품 지정 현황", "의료기기 회수 정보", "백신 공급 및 접종 현황", "항생제 사용량 통계", "의약품 안전사용 정보(DUR)",
         "체외진단 의료기기 허가 현황", "의약품 가격 정보", "임상시험 승인 현황"],
        ["허가 정보", "공급 통계", "부작용 보고", "사용량 통계", "회수·폐기 정보", "가격 정보", "임상시험 현황"],
    ),
    "건강검진": (
        ["국가건강검진 수검 현황", "건강검진 결과 통계(검사항목별)", "암 검진 수검률", "영유아 건강검진 결과", "학생 건강검사 통계",
         "직장인 건강검진 통계", "세브란스 체크업 수검자 통계", "구강검진 결과 통계", "검진기관 지정 현황", "건강검진 판정 결과 분포",
         "생애전환기 건강진단 통계", "건강검진 문진 통계", "검진 사후관리 현황"],
        ["수검 현황", "검진 결과 통계", "검진기관 현황", "수검률 통계", "판정 결과 분포", "검사항목별 통계"],
    ),
    "감염병": (
        ["법정감염병 발생 현황", "인플루엔자 표본감시 통계", "코로나19 확진·사망 통계", "결핵 신고 현황", "의료관련감염 감시 결과",
         "예방접종 실시 현황", "해외유입 감염병 통계", "손상·감염병 응급실 감시", "수인성·식품매개 감염병 발생 현황", "항생제 내성균 감시 통계",
         "감염병 격리병상 현황", "방역 자원 현황", "감염병 역학조사 통계"],
        ["발생 현황", "표본감시 통계", "신고 현황", "예방접종 현황", "감시 결과", "격리병상 현황"],
    ),
    "의료인력": (
        ["의사·간호사 면허 등록 현황", "전공의 수련병원 현황", "간호인력 배치 현황", "의료인 지역별 분포", "세브란스병원 진료과별 전문의 수",
         "약사 면허 현황", "의료기사 현황", "보건의료인 국가시험 통계", "의료인 보수교육 이수 현황", "공공의료 인력 현황",
         "응급구조사 현황", "간호조무사 현황", "의료인력 수급 추계"],
        ["면허 현황", "인력 배치 현황", "지역별 분포", "수련 현황", "수급 추계", "국가시험 통계"],
    ),
    "바이오·연구": (
        ["임상연구 과제 현황", "연세의료원 연구 논문 성과", "SBSI 바이오 연구 과제 목록", "바이오뱅크 검체 보유 현황", "연구용 의료데이터 제공 현황",
         "의료 인공지능 학습 데이터 목록", "유전체 연구 코호트 현황", "의료기기 임상시험 기관 현황", "연구 윤리위원회(IRB) 심의 통계", "보건의료 연구개발 사업 현황",
         "신약 개발 파이프라인 현황", "재생의료 임상연구 현황", "의료 빅데이터 개방 현황"],
        ["연구 과제 현황", "논문 성과", "검체 보유 현황", "데이터 제공 현황", "임상시험 현황", "연구개발 사업 현황"],
    ),
}

INSTITUTIONS = ["연세대학교 의료원", "세브란스병원", "강남세브란스병원", "용인세브란스병원", "SBSI", "건강보험심사평가원", "국민건강보험공단",
                "질병관리청", "보건복지부", "국립중앙의료원", "서울특별시", "식품의약품안전처", "국립암센터", "한국보건의료연구원"]
REGIONS = ["전국", "서울", "경기", "인천", "부산", "대구", "광주", "대전", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"]
PERIODS = ["2023년", "2024년", "2025년", "2024년 상반기", "2024년 하반기", "2025년 1분기", "2025년 2분기", "2025년 3분기", "월별", "연도별"]
UPDATE_CYCLES = ["월 1회", "분기 1회", "연 1회", "수시", "일 1회"]
FORMATS = ["CSV", "JSON", "XLSX", "API"]


def load_credentials(secret_path):
    user, pw = os.environ.get("DB_USERNAME"), os.environ.get("DB_PASSWORD")
    if user and pw:
        return user, pw
    p = pathlib.Path(secret_path)
    if not p.exists():
        sys.exit(f"DB_USERNAME/DB_PASSWORD 환경변수가 없고 {p} 도 없습니다")
    kv = {}
    for line in p.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if line and not line.startswith("#") and "=" in line:
            k, v = line.split("=", 1)
            kv[k.strip()] = v.strip()
    try:
        return kv["DB_USERNAME"], kv["DB_PASSWORD"]
    except KeyError as e:
        sys.exit(f"{p} 에 {e} 가 없습니다")


def make_rows(count, rng, start_index=1):
    """(category_nm, title, summary, description, institution, approved_dt, target, show_at, cycle, fmt) 목록"""
    rows = []
    cat_names = list(CATEGORIES)
    # 앞부분은 다듬어진 표본 제목, 나머지는 기관·지역·주제·기간 조합
    curated = [(c, t) for c, (titles, _) in CATEGORIES.items() for t in titles]
    for i in range(start_index, start_index + count):
        if i - 1 < len(curated):
            cat, title = curated[i - 1]
            inst = next((x for x in sorted(INSTITUTIONS, key=len, reverse=True) if x in title), rng.choice(INSTITUTIONS))
        else:
            cat = rng.choice(cat_names)
            subject = rng.choice(CATEGORIES[cat][1])
            inst = rng.choice(INSTITUTIONS)
            region = rng.choice(REGIONS)
            period = rng.choice(PERIODS)
            title = f"{region} {subject} ({period})" if rng.random() < 0.6 else f"{inst} {subject} {period}"
        cycle, fmt = rng.choice(UPDATE_CYCLES), rng.choice(FORMATS)
        summary = f"{inst}이(가) 제공하는 {cat} 분야 '{title}' 데이터셋. 갱신 {cycle}, 형식 {fmt}."
        description = (f"{title} 데이터셋은 {cat} 영역의 통계·현황 자료입니다. 제공기관 {inst}, 갱신주기 {cycle}, 제공형식 {fmt}. "
                       f"목록 검색·페이징·성능 확인을 위해 생성한 테스트 데이터이며 실제 수치를 담고 있지 않습니다.")
        # 승인일: 목록 기본 기간(2025-01-01~2026-01-01)에 대부분 들어가도록 85% 는 2025년
        if rng.random() < 0.85:
            day = dt.datetime(2025, 1, 1) + dt.timedelta(days=rng.randrange(365), seconds=rng.randrange(86400))
        else:
            day = dt.datetime(2026, 1, 1) + dt.timedelta(days=rng.randrange(296), seconds=rng.randrange(86400))
        target = "MOBIGEN" if rng.random() < 0.85 else "KWARE"
        show_at = "Y" if rng.random() < 0.95 else "N"
        rows.append((cat, title, summary, description, inst, day, target, show_at, cycle, fmt))
    return rows


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--count", type=int, default=100, help="목표 건수(기존 TEST-HOSP- 건수를 빼고 보충)")
    ap.add_argument("--workplace", type=int, default=22)
    ap.add_argument("--approver", type=int, default=214, help="승인자 cetus_user.uid (기본 214 데이터 제공자)")
    ap.add_argument("--host", default="172.30.1.12")
    ap.add_argument("--port", type=int, default=5434)
    ap.add_argument("--db", default="ketiagc_db")
    ap.add_argument("--schema", default="k_thirdeye")
    ap.add_argument("--secret", default="local-secret.properties")
    ap.add_argument("--seed", type=int, default=20261024)
    ap.add_argument("--dry-run", action="store_true")
    ap.add_argument("--purge", action="store_true", help="TEST-HOSP- 데이터와 빈 시험 카테고리를 삭제")
    a = ap.parse_args()
    sys.stdout.reconfigure(encoding="utf-8")

    user, pw = load_credentials(a.secret)
    conn = pg8000.native.Connection(user=user, password=pw, host=a.host, port=a.port, database=a.db, timeout=30)
    conn.run(f"SET search_path TO {a.schema}")
    try:
        if a.purge:
            conn.run("BEGIN")
            n = conn.run("DELETE FROM cetus_approved_dataset WHERE metadata_id LIKE :p RETURNING uid", p=PREFIX + "%")
            # 진열 UI 는 FK ON DELETE CASCADE 로 함께 삭제됨. 시험 카테고리는 더 이상 쓰는 행이 없을 때만 삭제
            c = conn.run("""DELETE FROM cetus_dataset_category c WHERE c.workplace_uid = :wp AND c.category_nm = ANY(:names)
                            AND NOT EXISTS (SELECT 1 FROM cetus_dataset_ui u WHERE u.category_uid = c.uid) RETURNING uid""",
                         wp=a.workplace, names=list(CATEGORIES))
            conn.run("COMMIT")
            print(f"삭제: 데이터셋 {len(n)}건, 카테고리 {len(c)}건")
            return

        existing = conn.run("SELECT count(*) FROM cetus_approved_dataset WHERE metadata_id LIKE :p", p=PREFIX + "%")[0][0]
        need = a.count - existing
        print(f"기존 {PREFIX}* {existing}건, 목표 {a.count}건 → 추가 {max(need, 0)}건")
        if need <= 0:
            return
        rng = random.Random(a.seed + existing)
        rows = make_rows(need, rng, start_index=existing + 1)
        if a.dry_run:
            for r in rows[:15]:
                print(f"  [{r[0]}] {r[1]} | {r[4]} | {r[5]:%Y-%m-%d} | {r[6]} | show={r[7]}")
            print(f"  ... 총 {len(rows)}건 (dry-run, 넣지 않음)")
            return

        main_uis = [r[0] for r in conn.run("SELECT uid FROM cetus_dataset_main_ui WHERE workplace_uid = :wp AND use_at = 'Y' AND delete_at = 'N' ORDER BY uid", wp=a.workplace)]
        if not main_uis:
            sys.exit("사용 중인 메인 UI 가 없습니다(cetus_dataset_main_ui)")

        conn.run("BEGIN")
        # 카테고리 보장
        cat_uid = {r[1]: r[0] for r in conn.run("SELECT uid, category_nm FROM cetus_dataset_category WHERE workplace_uid = :wp", wp=a.workplace)}
        max_sort = conn.run("SELECT COALESCE(MAX(sort_no), 0) FROM cetus_dataset_category WHERE workplace_uid = :wp", wp=a.workplace)[0][0]
        for name in CATEGORIES:
            if name not in cat_uid:
                max_sort += 1
                cat_uid[name] = conn.run("""INSERT INTO cetus_dataset_category (workplace_uid, category_nm, reg_uid, sort_no)
                                            VALUES (:wp, :nm, :reg, :sort) RETURNING uid""", wp=a.workplace, nm=name, reg=a.approver, sort=max_sort)[0][0]
        next_sort = conn.run("SELECT COALESCE(MAX(sort_no), 0) FROM cetus_dataset_ui")[0][0]

        inserted = 0
        for chunk_start in range(0, len(rows), 1000):
            chunk = rows[chunk_start:chunk_start + 1000]
            ids = [f"{PREFIX}{existing + chunk_start + k + 1:05d}" for k in range(len(chunk))]
            approved = conn.run("""INSERT INTO cetus_approved_dataset (metadata_id, workplace_uid, approved_dt, approver_uid, delete_at, target_tp_cd)
                                   SELECT * FROM unnest(:ids::text[], :wps::int8[], :dts::timestamp[], :apprs::int8[], :dels::text[], :tgts::text[])
                                   RETURNING uid""",
                                ids=ids, wps=[a.workplace] * len(chunk), dts=[r[5] for r in chunk],
                                apprs=[a.approver] * len(chunk), dels=["N"] * len(chunk), tgts=[r[6] for r in chunk])
            uids = [r[0] for r in approved]
            extras = []
            for r in chunk:
                extras.append(json.dumps({
                    "title": r[1], "summary": r[2], "description": r[3],
                    "addInfo": [{"label": "제공기관", "value": r[4]}, {"label": "갱신주기", "value": r[8]},
                                {"label": "제공형식", "value": r[9]}, {"label": "데이터 구분", "value": "테스트 데이터(생성)"}],
                }, ensure_ascii=False))
            sorts = list(range(next_sort + 1, next_sort + 1 + len(chunk)))
            next_sort += len(chunk)
            conn.run("""INSERT INTO cetus_dataset_ui (approved_dataset_uid, sort_no, main_ui_uid, show_at, thumb_uid, reg_uid, extra_json, category_uid)
                        SELECT u.uid, u.sort_no, u.main_ui_uid, u.show_at, NULL, :reg, u.extra::jsonb, u.category_uid
                          FROM unnest(:uids::int8[], :sorts::int8[], :mains::int8[], :shows::text[], :extras::text[], :cats::int8[])
                               AS u(uid, sort_no, main_ui_uid, show_at, extra, category_uid)""",
                     reg=a.approver, uids=uids, sorts=sorts, mains=[rng.choice(main_uis) for _ in chunk],
                     shows=[r[7] for r in chunk], extras=extras, cats=[cat_uid[r[0]] for r in chunk])
            inserted += len(chunk)
            print(f"  {inserted}/{len(rows)}")
        conn.run("COMMIT")
        total = conn.run("SELECT count(*) FROM cetus_approved_dataset WHERE metadata_id LIKE :p", p=PREFIX + "%")[0][0]
        print(f"완료: 추가 {inserted}건, {PREFIX}* 총 {total}건, 카테고리 {len(CATEGORIES)}개 보장")
    finally:
        conn.close()


if __name__ == "__main__":
    main()
