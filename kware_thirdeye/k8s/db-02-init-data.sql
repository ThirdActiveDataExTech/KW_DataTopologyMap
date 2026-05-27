-- =============================================================================
-- 1. 권한 및 제약 조건 설정 (강제 입력 모드)
-- =============================================================================
ALTER SCHEMA k_thirdeye OWNER TO ketiagc;
GRANT ALL ON SCHEMA k_thirdeye TO ketiagc;
SET search_path TO k_thirdeye, public;

-- [추가] bigint와 varchar 간의 비교 시 발생하는 타입 불일치 오류 해결을 위한 캐스트 설정
CREATE OR REPLACE FUNCTION public.cast_varchar_to_bigint(varchar) RETURNS bigint AS $$
    SELECT CASE WHEN $1 ~ '^[0-9]+$' THEN $1::bigint ELSE NULL END;
$$ LANGUAGE sql IMMUTABLE;

DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast WHERE castsource = 'character varying'::regtype AND casttarget = 'bigint'::regtype) THEN
        CREATE CAST (varchar AS bigint) WITH FUNCTION public.cast_varchar_to_bigint(varchar) AS IMPLICIT;
    END IF;
EXCEPTION WHEN others THEN 
    NULL; -- 이미 존재할 경우 무시
END $$;

-- [중요] 모든 외래키 제약 조건을 일시적으로 무시합니다.
SET session_replication_role = 'replica';

-- =============================================================================
-- 2. 워크플레이스 정보 (ID 22)
-- =============================================================================
INSERT INTO cetus_workplace (uid, name, REG_UID, REG_DT, UPDT_UID, UPDT_DT)
VALUES (22, '써드파티 로컬', 206, now(), 206, now())
ON CONFLICT (uid) DO UPDATE SET name = EXCLUDED.name;

-- =============================================================================
-- 3. 프로그램 정보 (11개 컬럼)
-- =============================================================================
INSERT INTO cetus_progrm_info (uid, progrm_nm, url, progrm_dc, use_at, workplace_uid, is_root_url, reg_uid, reg_dt, updt_uid, updt_dt) VALUES
	(217, 'root', '/', NULL, 'Y', 22, 'Y', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(218, 'footer_root', '/', NULL, 'Y', 22, 'Y', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(219, '데이터 검색', '/portal/list', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(221, '메뉴 관리', '/system/menu', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(222, '프로그램 관리', '/system/program', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(223, '게시판 관리', '/system/bbs', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(224, '사용자 관리', '/system/user', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(225, '메타데이터 관리', '/admin/metadata/dataset', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(226, '데이터 진열 관리', '/admin/approved/dataset', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(227, '데이터셋 메인UI 관리', '/admin/main-ui', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(228, '데이터 카테고리 관리', '/admin/dataset/category', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(229, '워크플레이스 관리', '/system/workplace', NULL, 'Y', 22, 'N', 169, '2025-11-04 16:56:25.323', 169, '2025-11-04 16:56:25.323'),
	(230, '공지사항 게시판', '/portal/bbs/notice', NULL, 'Y', 22, 'Y', 206, '2025-11-05 17:28:43.162', 206, '2025-11-18 16:45:41.755'),
	(231, '오류신고 게시판', '/portal/bbs/report', NULL, 'Y', 22, 'Y', 206, '2025-11-05 17:29:03.786', 206, '2025-11-05 17:29:03.786'),
	(232, '이용안내 게시판', '/portal/bbs/manual', NULL, 'Y', 22, 'Y', 206, '2025-11-05 17:29:16.459', 206, '2025-11-05 17:29:16.459'),
	(233, '자유게시판 게시판', '/portal/bbs/board', NULL, 'Y', 22, 'Y', 206, '2025-11-18 16:42:25.224', 206, '2025-11-18 16:45:30.205');

-- =============================================================================
-- 4. 메뉴 정보 (19개 컬럼)
-- =============================================================================
INSERT INTO cetus_menu_info (menu_no, program_uid, upper_menu_no, menu_nm, menu_icon, sort_no, menu_dc, use_at, delete_at, author_cd, menu_style1, menu_style2, root_menu_cd, workplace_uid, menu_style, reg_uid, reg_dt, updt_uid, updt_dt) VALUES
	(631, 217, NULL, 'root', NULL, '1', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, 'TOP_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(632, NULL, 631, '데이터 검색', NULL, '1', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(701, 219, 632, '컨텐츠 메인 (홈)', NULL, '1', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(634, NULL, 631, '데이터셋 관리', NULL, '3', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(702, 225, 634, '메타데이터 관리', NULL, '1', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(703, 226, 634, '데이터 진열 관리', NULL, '2', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(704, 227, 634, '데이터셋 메인UI 관리', NULL, '3', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(705, 228, 634, '데이터 카테고리 관리', NULL, '4', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(639, NULL, 631, '시스템 관리', NULL, '4', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(706, 221, 639, '메뉴 관리', NULL, '1', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(707, 222, 639, '프로그램 관리', NULL, '2', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(708, 223, 639, '게시판 관리', NULL, '3', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(709, 224, 639, '사용자 관리', NULL, '4', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(710, 229, 639, '워크플레이스 관리', NULL, '5', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(711, 218, NULL, 'footer_root', NULL, '2', NULL, 'Y', 'N', 'ROLE_SYSTEM', NULL, NULL, 'FOOTER_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(646, 217, NULL, 'root', NULL, '1', NULL, 'Y', 'N', 'ROLE_ADMIN', NULL, NULL, 'TOP_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(647, NULL, 646, '데이터 검색', NULL, '1', NULL, 'Y', 'N', 'ROLE_ADMIN', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(712, 219, 647, '컨텐츠 메인 (홈)', NULL, '1', NULL, 'Y', 'N', 'ROLE_ADMIN', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(713, 218, NULL, 'footer_root', NULL, '2', NULL, 'Y', 'N', 'ROLE_ADMIN', NULL, NULL, 'FOOTER_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(650, 217, NULL, 'root', NULL, '1', NULL, 'Y', 'N', 'ROLE_USER', NULL, NULL, 'TOP_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(651, NULL, 650, '데이터 검색', NULL, '1', NULL, 'Y', 'N', 'ROLE_USER', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(714, 219, 651, '컨텐츠 메인 (홈)', NULL, '1', NULL, 'Y', 'N', 'ROLE_USER', NULL, NULL, NULL, 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(715, 218, NULL, 'footer_root', NULL, '2', NULL, 'Y', 'N', 'ROLE_USER', NULL, NULL, 'FOOTER_ROOT', 22, NULL, 206, '2025-11-04 16:56:25.323', 206, '2025-11-04 16:56:25.323'),
	(654, NULL, 646, '데이터셋 관리', NULL, '1', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:37:07.868', 206, '2025-11-18 16:37:07.868'),
	(716, 225, 654, '메타데이터 관리', NULL, '1', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:37:07.868', 206, '2025-11-18 16:37:07.868'),
	(717, 226, 654, '데이터 진열 관리', NULL, '2', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:37:09.045', 206, '2025-11-18 16:37:09.045'),
	(718, 227, 654, '데이터셋 메인UI 관리', NULL, '3', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:37:09.966', 206, '2025-11-18 16:37:09.966'),
	(719, 228, 654, '데이터 카테고리 관리', NULL, '4', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:37:12.244', 206, '2025-11-18 16:37:12.244'),
	(659, NULL, 631, '게시판', NULL, '2', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-18 16:41:17.296', 206, '2025-11-18 16:41:17.296'),
	(720, 231, 659, '오류신고', NULL, '3', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-18 16:41:40.961', 206, '2025-11-18 16:41:40.961'),
	(721, 232, 659, '이용안내', NULL, '4', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-18 16:42:54.963', 206, '2025-11-18 16:42:54.963'),
	(664, NULL, 646, '게시판', NULL, '2', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:43:27.521', 206, '2025-11-18 16:43:27.521'),
	(722, 230, 659, '공지사항', NULL, '1', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-18 16:41:40.101', 206, '2025-11-18 16:41:40.101'),
	(723, 231, 664, '오류신고', NULL, '3', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:43:35.975', 206, '2025-11-18 16:43:35.975'),
	(724, 232, 664, '이용안내', NULL, '4', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:43:37.266', 206, '2025-11-18 16:43:37.266'),
	(725, 230, 664, '공지사항', NULL, '1', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:43:34.323', 206, '2025-11-18 16:43:34.323'),
	(669, NULL, 650, '게시판', NULL, '2', '', 'Y', 'N', 'ROLE_USER', '', '', NULL, 22, '', 206, '2025-11-18 16:44:20.541', 206, '2025-11-18 16:44:20.541'),
	(726, 232, 669, '이용안내', NULL, '4', '', 'Y', 'N', 'ROLE_USER', '', '', NULL, 22, '', 206, '2025-11-18 16:44:28.764', 206, '2025-11-18 16:44:28.764'),
	(727, 233, 664, '자유 게시판', NULL, '2', '', 'Y', 'N', 'ROLE_ADMIN', '', '', NULL, 22, '', 206, '2025-11-18 16:43:35.147', 206, '2025-11-18 16:43:35.147'),
	(728, 231, 669, '오류신고', NULL, '3', '', 'Y', 'N', 'ROLE_USER', '', '', NULL, 22, '', 206, '2025-11-18 16:44:28.030', 206, '2025-11-18 16:44:28.030'),
	(729, 230, 669, '공지사항', NULL, '1', '', 'Y', 'N', 'ROLE_USER', '', '', NULL, 22, '', 206, '2025-11-18 16:44:26.691', 206, '2025-11-18 16:44:26.691'),
	(730, 233, 659, '자유 게시판', NULL, '2', '', 'Y', 'N', 'ROLE_SYSTEM', '', '', NULL, 22, '', 206, '2025-11-18 16:41:42.421', 206, '2025-11-18 16:41:42.421'),
	(731, 233, 669, '자유 게시판', NULL, '2', '', 'Y', 'N', 'ROLE_USER', '', '', NULL, 22, '', 206, '2025-11-18 16:44:27.378', 206, '2025-11-18 16:44:27.378');

-- =============================================================================
-- 5. 공통 코드 및 기타 정보
-- =============================================================================
INSERT INTO CETUS_SYS_CODE (CODE,UPPER_CODE,CODE_NM,CODE_DC,USE_AT,RM_DC,ITEM1_VAL,ITEM2_VAL,SORT_NO,REG_UID,REG_DT,UPDT_UID,UPDT_DT) VALUES
	 ('FORM_GROUP',NULL,'폼 그룹 관리','폼 그룹 관리','Y',NULL,NULL,NULL,NULL,1,'2025-05-21 06:35:50.77',169,'2025-11-03 16:24:06.331832'),
	 ('SIGNUP','FORM_GROUP','회원가입',NULL,'N','','','',NULL,1,'2025-05-13 06:00:47.362',169,'2025-11-03 16:24:06.331832'),
	 ('BOARD','FORM_GROUP','게시판',NULL,'N','','','',NULL,1,'2025-05-13 15:01:08',169,'2025-11-03 16:24:06.331832'),
	 ('BBS_TP_CD',NULL,'게시판 유형 코드','게시판 유형 코드','Y',NULL,NULL,NULL,0,169,'2025-11-04 09:14:39.852801',169,'2025-11-04 09:17:42.345545'),
	 ('NOTICE','BBS_TP_CD','공지사항',NULL,'Y','','notice','',1,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('BOARD','BBS_TP_CD','자유 게시판',NULL,'Y','','board','',2,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('REPORT','BBS_TP_CD','오류 신고',NULL,'Y','','report','',3,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('MANUAL','BBS_TP_CD','이용안내',NULL,'Y','','manual','',4,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('FAQ','BBS_TP_CD','faq',NULL,'Y','','faq','',5,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('QNA','BBS_TP_CD','1:1문의',NULL,'Y','','qna','',6,169,'2025-11-04 09:16:23.531997',169,'2025-11-04 09:17:42.345545'),
	 ('DATASET_COMMENT',NULL,'데이터셋 평점/의견','데이터셋 평점/의견','Y',NULL,NULL,NULL,0,169,'2025-11-03 16:23:50.892208',169,'2025-11-04 11:32:57.009294'),
	 ('COMMENT_OPINION','DATASET_COMMENT','의견',NULL,'Y','','opinion','',0,169,'2025-11-04 11:32:57.009294',169,'2025-11-04 11:32:57.009294'),
	 ('COMMENT_REPORT','DATASET_COMMENT','오류신고',NULL,'Y','','report','',0,169,'2025-11-04 11:32:57.009294',169,'2025-11-04 11:32:57.009294'),
	 ('COMMENT_QUESTION','DATASET_COMMENT','문의',NULL,'Y','','question','',0,169,'2025-11-04 11:32:57.009294',169,'2025-11-04 11:32:57.009294');

-- =============================================================================
-- 6. 사용자 정보
-- =============================================================================
INSERT INTO cetus_user (uid, user_id, "password", user_nm, user_email, approve_at, use_at, fail_cnt, author_cd, profile_uid, meta_data, status, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(217, 'user2', '$2a$10$YjymvZd3ERz09dQtaSxAku26FHIvfTmGMaQ6HdkTi.GUA2vt.jwrG', 'user2', 'user2@naver.com', 'Y', 'Y', 0, 'ROLE_USER', NULL, '{}'::jsonb, 'APPROVED', 206, '2026-02-24 09:46:32.567', 206, '2026-02-24 09:46:32.567');
INSERT INTO cetus_user (uid, user_id, "password", user_nm, user_email, approve_at, use_at, fail_cnt, author_cd, profile_uid, meta_data, status, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(208, 'user', '$2a$10$pZRXy5APxfngL1v7F/eeX.yKU9M5dXcK9Z3/UMNjwPPK.lQ5DSiKa', '일반 유저', 'user@kware.co.kr', 'Y', 'Y', 0, 'ROLE_USER', NULL, '{}'::jsonb, 'APPROVED', 169, '2025-11-04 16:56:25.323', 206, '2026-02-19 11:04:56.250');
INSERT INTO cetus_user (uid, user_id, "password", user_nm, user_email, approve_at, use_at, fail_cnt, author_cd, profile_uid, meta_data, status, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(206, 'system', '$2a$10$Qib2akuNeKfLnaCviSvhNOreKyyVIde4IpoqFIGwTk.ZUO0wXmKxe', '시스템 관리자', 'system@kwrae.co.kr', 'Y', 'Y', 0, 'ROLE_SYSTEM', NULL, '{}'::jsonb, 'APPROVED', 169, '2025-11-04 16:56:25.323', 206, '2026-02-19 11:04:29.435');
INSERT INTO cetus_user (uid, user_id, "password", user_nm, user_email, approve_at, use_at, fail_cnt, author_cd, profile_uid, meta_data, status, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(218, 'user3', '$2a$10$WcYnlv07yd3itUH6qAh5geqtmGinmVJ.vZESLY71UrjTyV0auyggW', 'user3', 'user3@naver.com', 'Y', 'Y', 0, 'ROLE_USER', NULL, NULL, 'WAIT', NULL, '2026-02-24 09:47:19.363', NULL, '2026-02-24 09:47:19.363');
INSERT INTO cetus_user (uid, user_id, "password", user_nm, user_email, approve_at, use_at, fail_cnt, author_cd, profile_uid, meta_data, status, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(214, 'admin', '$2a$10$9wLSrKgtwygJyYFSmPYX1uGDe3LrZozfcT0PNOLsOY7ZtZmcB8XC.', '데이터 제공자', 'admin@kware.co.kr', 'Y', 'Y', 0, 'ROLE_ADMIN', NULL, '{}'::jsonb, 'APPROVED', 206, '2026-02-19 11:04:02.441', 206, '2026-02-19 11:04:02.441');

-- =============================================================================
-- 7. 기타 부가 데이터 (Dataset UI 등)
-- =============================================================================
INSERT INTO cetus_dataset_main_ui(uid, code, "name", max_count, desc_line_clamp, title_line_clamp, thumb_use_at, workplace_uid, use_at, delete_at, reg_uid, reg_dt, updt_uid, updt_dt, type_cd, sort_no) VALUES
	(28, 'test_ui', 'typeD ui', 9, 3, 1, 'N', 22, 'Y', 'N', 206, '2026-02-12 13:23:43.277', 206, '2026-02-12 16:23:23.353', 'TYPE_D', 1),
	(29, 'TYPE_C_TESTUI', 'typeC ui', 8, 3, 1, 'Y', 22, 'Y', 'N', 206, '2026-02-12 16:23:51.038', 206, '2026-02-12 16:23:51.038', 'TYPE_C', 2),
	(30, 'typeB ui', 'typeB ui', 4, 3, 1, 'Y', 22, 'Y', 'N', 206, '2026-02-12 16:25:04.262', 206, '2026-02-12 16:25:04.262', 'TYPE_B', 4),
	(31, 'typeA ui', 'typeA ui', 4, NULL, 1, 'Y', 22, 'Y', 'N', 206, '2026-02-12 16:25:43.255', 206, '2026-02-12 16:25:43.255', 'TYPE_A', 4);

INSERT INTO cetus_approved_dataset(uid, metadata_id, workplace_uid, approved_dt, approver_uid, delete_at, target_tp_cd) VALUES
	(178, '14', 22, '2026-02-12 13:29:42.297', 206, 'Y', 'MOBIGEN'),
	(179, '22', 22, '2026-02-12 14:22:57.120', 206, 'N', 'MOBIGEN'),
	(180, '23', 22, '2026-02-12 14:23:06.312', 206, 'N', 'MOBIGEN'),
	(181, '24', 22, '2026-02-12 14:23:15.814', 206, 'N', 'MOBIGEN');

INSERT INTO cetus_dataset_ui(uid, approved_dataset_uid, sort_no, main_ui_uid, show_at, reg_uid, reg_dt, updt_uid, updt_dt, extra_json, category_uid) VALUES
	(180, 181, 3, 28, 'Y', 206, '2026-02-12 14:23:15.814', 206, '2026-02-23 09:51:20.882', '{"addInfo": [], "summary": "", "description": ""}'::jsonb, 86),
	(178, 179, 1, 28, 'Y', 206, '2026-02-12 14:22:57.120', 206, '2026-02-24 09:54:01.276', '{"addInfo": [], "summary": "", "description": ""}'::jsonb, 86),
	(177, 178, 1, 28, 'Y', 206, '2026-02-12 13:29:42.297', 206, '2026-02-12 13:29:42.297', '{"addInfo": [], "summary": "테스트", "description": ""}'::jsonb, 86),
	(179, 180, 2, 28, 'Y', 206, '2026-02-12 14:23:06.312', 206, '2026-02-12 14:23:06.312', '{"addInfo": [], "summary": "", "description": ""}'::jsonb, 86);



INSERT INTO cetus_bbs (bbs_uid, bbs_nm, bbs_tp_cd, use_at, delete_at, workplace_uid, bbs_cl_use_at, atch_at, atch_num, upload_cpcty, answer_use_at, reg_uid, reg_dt, updt_uid, updt_dt) VALUES
	(30, '공지사항', 'NOTICE', 'Y', 'N', 22, 'N', 'N', 0, NULL, 'N', 206, '2026-02-19 16:55:49.202', 206, '2026-02-19 16:55:49.202'),
	(31, '자유 게시판', 'BOARD', 'Y', 'N', 22, 'N', 'N', 0, NULL, 'N', 206, '2026-02-19 16:56:02.524', 206, '2026-02-19 16:56:02.524'),
	(32, '오류 신고', 'REPORT', 'Y', 'N', 22, 'N', 'N', 0, NULL, 'N', 206, '2026-02-19 16:56:14.916', 206, '2026-02-19 16:56:14.916'),
	(33, '이용안내', 'MANUAL', 'Y', 'N', 22, 'N', 'N', 0, NULL, 'N', 206, '2026-02-19 16:56:35.609', 206, '2026-02-19 16:56:35.609');


-- =============================================================================
-- 8. 워크플레이스 사용자 매핑
-- =============================================================================
INSERT INTO cetus_workplace_user (user_uid, workplace_uid, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(214, 22, 206, '2026-02-19 11:04:02.441', NULL, NULL);
INSERT INTO cetus_workplace_user (user_uid, workplace_uid, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(217, 22, 206, '2026-02-24 09:46:32.567', NULL, NULL);
INSERT INTO cetus_workplace_user (user_uid, workplace_uid, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(218, 22, NULL, '2026-02-24 09:47:19.363', NULL, NULL);
INSERT INTO cetus_workplace_user (user_uid, workplace_uid, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(206, 22, 169, '2025-11-04 16:56:25.323', NULL, NULL);
INSERT INTO cetus_workplace_user (user_uid, workplace_uid, reg_uid, reg_dt, updt_uid, updt_dt) VALUES(208, 22, 169, '2025-11-04 16:56:25.323', NULL, NULL);

-- [최종 조치] 제약 조건 다시 활성화
SET session_replication_role = 'origin';

-- =============================================================================
-- 8. 시퀀스 조정
-- =============================================================================
SELECT setval('cetus_menu_info_menu_no_seq', 1000);
SELECT setval('cetus_progrm_info_uid_seq', 1000);
SELECT setval('cetus_user_uid_seq', 1000);
SELECT setval('cetus_workplace_uid_seq', 1000);
SELECT setval('cetus_dataset_main_ui_uid_seq', 1000);
SELECT setval('cetus_approved_dataset_uid_seq', 1000);
SELECT setval('cetus_dataset_ui_uid_seq', 1000);
SELECT setval('cetus_bbs_bbs_uid_seq', 1000);
