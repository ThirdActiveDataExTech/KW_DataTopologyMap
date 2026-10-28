-- 조회 인덱스. 기존 DB 는 이 파일만 다시 실행하면 된다(IF NOT EXISTS, 재실행 안전).
-- 근거(2026-10-28 EXPLAIN): PK 외 인덱스가 없어 목록·홈·상세·북마크 조회가 cetus_dataset_ui 를 Seq Scan 으로 조인했다.
--   cetus_dataset_ui.approved_dataset_uid : 모든 데이터셋 조회의 조인 키(FK 이지만 PostgreSQL 은 자식 인덱스를 만들지 않는다)
--   cetus_dataset_ui.main_ui_uid          : 홈 화면이 메인 UI 별로 진열 행을 찾는다
--   cetus_approved_dataset.approved_dt    : 목록의 기간 조건과 정렬
--   cetus_approved_dataset.metadata_id    : 진열 여부 확인·검색데이터 갱신이 데이터셋 ID 로 찾는다
--   cetus_dataset_bookmark(user_uid, approved_dataset_uid) : 북마크 목록·존재 확인·목록의 북마크 여부 조인
--   cetus_dataset_comment.approved_dataset_uid : 목록 행마다 평점을 집계하는 하위 질의
CREATE INDEX IF NOT EXISTS ix_cetus_dataset_ui__approved_dataset_uid ON k_thirdeye.cetus_dataset_ui (approved_dataset_uid);
CREATE INDEX IF NOT EXISTS ix_cetus_dataset_ui__main_ui_uid ON k_thirdeye.cetus_dataset_ui (main_ui_uid);
CREATE INDEX IF NOT EXISTS ix_cetus_approved_dataset__approved_dt ON k_thirdeye.cetus_approved_dataset (approved_dt);
CREATE INDEX IF NOT EXISTS ix_cetus_approved_dataset__metadata_id ON k_thirdeye.cetus_approved_dataset (metadata_id);
CREATE INDEX IF NOT EXISTS ix_cetus_dataset_bookmark__user_uid_approved_dataset_uid ON k_thirdeye.cetus_dataset_bookmark (user_uid, approved_dataset_uid);
CREATE INDEX IF NOT EXISTS ix_cetus_dataset_comment__approved_dataset_uid ON k_thirdeye.cetus_dataset_comment (approved_dataset_uid);
