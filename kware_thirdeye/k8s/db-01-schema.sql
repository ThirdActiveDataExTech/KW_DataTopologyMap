-- DROP SCHEMA k_thirdeye;

CREATE SCHEMA k_thirdeye AUTHORIZATION ketiagc;

COMMENT ON SCHEMA k_thirdeye IS '써드파티 개발';

-- DROP SEQUENCE k_thirdeye.cetus_approved_dataset_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_approved_dataset_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_bbs_bbs_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_bbs_bbs_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_bbs_cl_cl_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_bbs_cl_cl_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_bbsctt_answer_answer_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_bbsctt_answer_answer_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_bbsctt_bbsctt_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_bbsctt_bbsctt_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_columns_options_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_columns_options_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_bookmark_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_bookmark_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_category_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_category_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_comment_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_comment_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_file_file_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_file_file_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_file_log_log_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_file_log_log_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_history_history_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_history_history_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_main_ui_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_main_ui_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dataset_ui_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dataset_ui_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_dept_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_dept_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_downloads_hist_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_downloads_hist_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_file_log_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_file_log_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_file_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_file_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_form_columns_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_form_columns_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_group_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_group_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_invite_code_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_invite_code_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_menu_info_menu_no_seq;

CREATE SEQUENCE k_thirdeye.cetus_menu_info_menu_no_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_mobigen_dataset_tag_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_mobigen_dataset_tag_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_mobigen_dataset_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_mobigen_dataset_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_mobigen_registrant_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_mobigen_registrant_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_mobigen_tag_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_mobigen_tag_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_position_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_position_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_progrm_info_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_progrm_info_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_sys_code_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_sys_code_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_user_status_hist_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_user_status_hist_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_user_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_user_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE k_thirdeye.cetus_workplace_uid_seq;

CREATE SEQUENCE k_thirdeye.cetus_workplace_uid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- k_thirdeye.cetus_approved_dataset definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_approved_dataset;

CREATE TABLE k_thirdeye.cetus_approved_dataset (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	metadata_id varchar(100) NOT NULL, -- 데이터셋 식별자 ID (Unique)
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	approved_dt timestamp NOT NULL, -- 승인일시 (최초 insert 시점, 수정 불가)
	approver_uid int8 NOT NULL, -- 승인자 UID (최초 insert, 수정 불가)
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제 여부
	target_tp_cd varchar(32) NOT NULL, -- 실데이터셋 타겟 저장 위치
	search_data jsonb NULL, -- 데이터셋 필터링 데이터
	CONSTRAINT cetus_approved_dataset_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_approved_dataset IS '승인된 데이터셋이 저장되는 기준 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.metadata_id IS '데이터셋 식별자 ID (Unique)';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.approved_dt IS '승인일시 (최초 insert 시점, 수정 불가)';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.approver_uid IS '승인자 UID (최초 insert, 수정 불가)';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.delete_at IS '삭제 여부';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.target_tp_cd IS '실데이터셋 타겟 저장 위치';
COMMENT ON COLUMN k_thirdeye.cetus_approved_dataset.search_data IS '데이터셋 필터링 데이터';


-- k_thirdeye.cetus_bbs_cl definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_bbs_cl;

CREATE TABLE k_thirdeye.cetus_bbs_cl (
	cl_uid bigserial NOT NULL, -- 분류UID
	upper_cl_uid int8 NULL, -- 상위분류UID
	bbs_uid int8 NOT NULL, -- 게시판UID
	use_at bpchar NOT NULL, -- 사용여부
	sort_no varchar(16) NULL, -- 정렬순서
	cl_nm varchar(32) NOT NULL, -- 분류명
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제여부
	reg_uid int8 NOT NULL, -- 등록자UID
	reg_dt timestamp(6) NOT NULL, -- 등록일시
	updt_uid int8 NOT NULL, -- 수정자UID
	updt_dt timestamp(6) NOT NULL, -- 수정일시
	CONSTRAINT PK_cetus_bbs_cl PRIMARY KEY (cl_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_bbs_cl IS '게시판 분류';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.cl_uid IS '분류UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.upper_cl_uid IS '상위분류UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.bbs_uid IS '게시판UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.use_at IS '사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.sort_no IS '정렬순서';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.cl_nm IS '분류명';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.delete_at IS '삭제여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.reg_dt IS '등록일시';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs_cl.updt_dt IS '수정일시';


-- k_thirdeye.cetus_bbsctt definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_bbsctt;

CREATE TABLE k_thirdeye.cetus_bbsctt (
	bbsctt_uid bigserial NOT NULL, -- 게시글UID
	bbs_uid int8 NOT NULL, -- 게시판UID
	cl_uid int8 NULL, -- 분류UID
	bbsctt_nm varchar(255) NOT NULL, -- 게시글 제목
	rd_cnt int8 DEFAULT 0::bigint NULL, -- 조회수
	bbsctt_cnt text NOT NULL, -- 게시글 내용
	notice_at bpchar NULL, -- 공지여부
	file_uid int8 NULL, -- 첨부파일UID
	use_at bpchar DEFAULT 'Y'::bpchar NOT NULL, -- 사용여부
	open_at bpchar NULL, -- 공개여부
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제여부
	thumbnail_src varchar(1024) NULL, -- 썸네일 src
	reg_uid int8 NOT NULL, -- 등록자UID
	reg_dt timestamp(6) NOT NULL, -- 등록일시
	updt_uid int8 NOT NULL, -- 수정자UID
	updt_dt timestamp(6) NOT NULL, -- 수정일시
	like_cnt int4 DEFAULT 0 NOT NULL, -- 좋아요수
	CONSTRAINT PK_cetus_bbsctt PRIMARY KEY (bbsctt_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_bbsctt IS '게시판 하위 게시글';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.bbsctt_uid IS '게시글UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.bbs_uid IS '게시판UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.cl_uid IS '분류UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.bbsctt_nm IS '게시글 제목';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.rd_cnt IS '조회수';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.bbsctt_cnt IS '게시글 내용';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.notice_at IS '공지여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.file_uid IS '첨부파일UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.use_at IS '사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.open_at IS '공개여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.delete_at IS '삭제여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.thumbnail_src IS '썸네일 src';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.reg_dt IS '등록일시';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.updt_dt IS '수정일시';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt.like_cnt IS '좋아요수';


-- k_thirdeye.cetus_bbsctt_answer definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_bbsctt_answer;

CREATE TABLE k_thirdeye.cetus_bbsctt_answer (
	answer_uid bigserial NOT NULL, -- 댓글UID
	bbsctt_uid int8 NOT NULL, -- 게시글UID
	answer_cnt text NOT NULL, -- 댓글 내용
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제여부
	reg_uid int8 NOT NULL, -- 등록자UID
	reg_dt timestamp(6) NOT NULL, -- 등록일시
	updt_uid int8 NOT NULL, -- 수정자UID
	updt_dt timestamp(6) NOT NULL, -- 수정일시
	CONSTRAINT PK_cetus_bbsctt_answer PRIMARY KEY (answer_uid, bbsctt_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_bbsctt_answer IS '게시글 하위 댓글';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.answer_uid IS '댓글UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.bbsctt_uid IS '게시글UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.answer_cnt IS '댓글 내용';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.delete_at IS '삭제여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.reg_dt IS '등록일시';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbsctt_answer.updt_dt IS '수정일시';


-- k_thirdeye.cetus_dataset_bookmark definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_bookmark;

CREATE TABLE k_thirdeye.cetus_dataset_bookmark (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	user_uid int8 NOT NULL, -- 유저 UID
	approved_dataset_uid int8 NOT NULL, -- FK → cetus_approved_dataset.uid
	reg_uid int8 NOT NULL, -- 작성자 UID
	reg_dt timestamp NOT NULL, -- 작성일
	CONSTRAINT cetus_dataset_bookmark_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_bookmark IS '데이터셋에 대한 북마크 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_bookmark.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_bookmark.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_bookmark.approved_dataset_uid IS 'FK → cetus_approved_dataset.uid';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_bookmark.reg_uid IS '작성자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_bookmark.reg_dt IS '작성일';


-- k_thirdeye.cetus_dataset_category definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_category;

CREATE TABLE k_thirdeye.cetus_dataset_category (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	category_nm varchar(255) NOT NULL, -- 카테고리 이름
	reg_uid int8 NOT NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	sort_no int4 NULL, -- 카테고리 정렬 순서
	CONSTRAINT cetus_dataset_category_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_category IS '데이터셋 카테고리 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.category_nm IS '카테고리 이름';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.updt_dt IS '수정일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_category.sort_no IS '카테고리 정렬 순서';


-- k_thirdeye.cetus_dataset_comment definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_comment;

CREATE TABLE k_thirdeye.cetus_dataset_comment (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	approved_dataset_uid int8 NOT NULL, -- FK → cetus_approved_dataset.uid
	type_cd varchar(32) NOT NULL, -- 타입 코드
	ratings numeric(3, 2) NULL, -- 평점 (numeric(3,2))
	comment text NULL, -- 내용
	reg_uid int8 NOT NULL, -- 작성자 UID
	reg_dt timestamp NOT NULL, -- 작성일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	CONSTRAINT cetus_dataset_comment_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_comment IS '데이터셋에 대한 평점/의견 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.approved_dataset_uid IS 'FK → cetus_approved_dataset.uid';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.type_cd IS '타입 코드';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.ratings IS '평점 (numeric(3,2))';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.comment IS '내용';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.reg_uid IS '작성자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.reg_dt IS '작성일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_comment.updt_dt IS '수정일';


-- k_thirdeye.cetus_dataset_file definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_file;

CREATE TABLE k_thirdeye.cetus_dataset_file (
	file_uid bigserial NOT NULL, -- 파일 UID (고유 식별자)
	file_id varchar(500) NOT NULL, -- 파일 ID
	file_nm varchar(200) NULL, -- 저장된 파일명
	org_file_nm varchar(200) NULL, -- 원본 파일명
	file_path varchar(2000) NULL, -- 파일 저장 경로
	file_url varchar(2550) NULL, -- 파일 접근 URL
	file_size int8 NULL, -- 파일 크기 (byte)
	file_type varchar(100) NULL, -- 파일 유형 (MIME Type 등)
	extension varchar(20) NULL, -- 파일 확장자
	down_cnt int4 DEFAULT 0 NULL, -- 다운로드 횟수
	use_at bpchar DEFAULT 'Y'::bpchar NULL, -- 사용 여부 (Y/N)
	reg_dt timestamp(6) NULL, -- 등록일시
	updt_dt timestamp(6) NULL, -- 수정일시
	saved bpchar DEFAULT 'Y'::bpchar NULL, -- 저장 여부 (Y/N)
	reg_id varchar(100) NULL, -- 등록자 ID
	metadata_id varchar(100) NULL, -- 메타데이터 ID (상위 식별자)
	rawdata_id varchar(100) NULL, -- RAW 데이터 ID (하위 식별자)
	data_tp_cd varchar(32) NULL, -- 데이터 타입유형(메타데이터, 원본데이터)
	CONSTRAINT pk_cetus_dataset_file PRIMARY KEY (file_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_file IS '데이터셋 파일 테이블 (메타데이터-RAW데이터 계층 구조 포함)';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_uid IS '파일 UID (고유 식별자)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_id IS '파일 ID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_nm IS '저장된 파일명';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.org_file_nm IS '원본 파일명';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_path IS '파일 저장 경로';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_url IS '파일 접근 URL';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_size IS '파일 크기 (byte)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.file_type IS '파일 유형 (MIME Type 등)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.extension IS '파일 확장자';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.down_cnt IS '다운로드 횟수';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.use_at IS '사용 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.reg_dt IS '등록일시';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.updt_dt IS '수정일시';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.saved IS '저장 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.reg_id IS '등록자 ID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.metadata_id IS '메타데이터 ID (상위 식별자)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.rawdata_id IS 'RAW 데이터 ID (하위 식별자)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file.data_tp_cd IS '데이터 타입유형(메타데이터, 원본데이터)';


-- k_thirdeye.cetus_dataset_main_ui definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_main_ui;

CREATE TABLE k_thirdeye.cetus_dataset_main_ui (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	code varchar(64) NOT NULL, -- UI 유형 코드 (Unique)
	name varchar(255) NOT NULL, -- UI 고유 이름
	max_count int4 NULL, -- 최대 show 개수
	desc_line_clamp int4 NULL, -- 내용 최대 라인 수
	title_line_clamp int4 NULL, -- 제목 최대 라인 수
	thumb_use_at bpchar NOT NULL, -- 썸네일 사용 여부 (Y/N)
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	use_at bpchar DEFAULT 'Y'::bpchar NOT NULL, -- 사용 여부 (Y/N)
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제 여부 (Y/N)
	reg_uid int8 NOT NULL, -- 작성자 UID
	reg_dt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 작성일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	type_cd varchar(32) NOT NULL, -- 메인 화면 UI 타입
	sort_no int8 NULL, -- 메인 UI 정렬 순서
	category_cnt int4 DEFAULT 4 NULL, -- 카테고리 최대 개수
	CONSTRAINT cetus_dataset_main_ui_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_main_ui IS '메인 UI 유형 정의 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.code IS 'UI 유형 코드 (Unique)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.name IS 'UI 고유 이름';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.max_count IS '최대 show 개수';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.desc_line_clamp IS '내용 최대 라인 수';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.title_line_clamp IS '제목 최대 라인 수';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.thumb_use_at IS '썸네일 사용 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.use_at IS '사용 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.delete_at IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.reg_uid IS '작성자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.reg_dt IS '작성일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.updt_dt IS '수정일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.type_cd IS '메인 화면 UI 타입';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.sort_no IS '메인 UI 정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_main_ui.category_cnt IS '카테고리 최대 개수';


-- k_thirdeye.cetus_dept_user definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dept_user;

CREATE TABLE k_thirdeye.cetus_dept_user (
	dept_uid int8 NOT NULL, -- 부서 UID
	user_uid int8 NOT NULL, -- 유저 UID
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	CONSTRAINT cetus_dept_user_pkey PRIMARY KEY (dept_uid, user_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dept_user IS '유저 부서 정보';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dept_user.dept_uid IS '부서 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept_user.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept_user.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept_user.reg_dt IS '등록일';


-- k_thirdeye.cetus_downloads_hist definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_downloads_hist;

CREATE TABLE k_thirdeye.cetus_downloads_hist (
	uid bigserial NOT NULL, -- UID
	target_uid int8 NULL, -- 타겟UID
	user_uid int8 NOT NULL, -- 유저 UID
	target_cd varchar(32) NOT NULL, -- 타겟 유형
	file_uid int8 NOT NULL, -- 파일 UID
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	CONSTRAINT cetus_downloads_hist_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_downloads_hist IS '다운로드 이력';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.target_uid IS '타겟UID';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.target_cd IS '타겟 유형';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.file_uid IS '파일 UID';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_downloads_hist.reg_dt IS '등록일';


-- k_thirdeye.cetus_file definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_file;

CREATE TABLE k_thirdeye.cetus_file (
	file_uid int8 DEFAULT nextval('k_thirdeye.cetus_file_uid_seq'::regclass) NOT NULL, -- 파일 UID
	file_id varchar(500) NOT NULL, -- 파일 ID
	file_nm varchar(200) NULL, -- 파일명
	org_file_nm varchar(200) NULL, -- 원본파일명
	file_path varchar(2000) NULL, -- 파일경로
	file_url varchar(2550) NULL, -- 파일 URL
	file_size int8 NULL, -- 파일 크기
	file_type varchar(100) NULL, -- 파일 유형
	extension varchar(20) NULL, -- 확장자
	down_cnt int4 DEFAULT 0 NULL, -- 다운로드 건수
	use_at bpchar DEFAULT 'Y'::bpchar NULL, -- 파일 등록여부
	reg_dt timestamp(6) NULL, -- 등록일
	updt_dt timestamp(6) NULL, -- 수정일
	saved bpchar DEFAULT 'Y'::bpchar NULL, -- 저장여부
	reg_id varchar(100) NULL, -- 작성자
	CONSTRAINT pk_cetus_file PRIMARY KEY (file_uid, file_id)
);
COMMENT ON TABLE k_thirdeye.cetus_file IS '파일';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_file.file_uid IS '파일 UID';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_id IS '파일 ID';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_nm IS '파일명';
COMMENT ON COLUMN k_thirdeye.cetus_file.org_file_nm IS '원본파일명';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_path IS '파일경로';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_url IS '파일 URL';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_size IS '파일 크기';
COMMENT ON COLUMN k_thirdeye.cetus_file.file_type IS '파일 유형';
COMMENT ON COLUMN k_thirdeye.cetus_file.extension IS '확장자';
COMMENT ON COLUMN k_thirdeye.cetus_file.down_cnt IS '다운로드 건수';
COMMENT ON COLUMN k_thirdeye.cetus_file.use_at IS '파일 등록여부';
COMMENT ON COLUMN k_thirdeye.cetus_file.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_file.updt_dt IS '수정일';
COMMENT ON COLUMN k_thirdeye.cetus_file.saved IS '저장여부';
COMMENT ON COLUMN k_thirdeye.cetus_file.reg_id IS '작성자';


-- k_thirdeye.cetus_file_log definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_file_log;

CREATE TABLE k_thirdeye.cetus_file_log (
	log_uid int8 DEFAULT nextval('k_thirdeye.cetus_file_log_uid_seq'::regclass) NOT NULL, -- 로그 UID
	file_uid int8 NOT NULL, -- 파일 UID
	file_id varchar(500) NOT NULL, -- 파일 ID
	worker_uid varchar(32) NULL, -- 사용자 UID
	worker_nm varchar(32) NULL, -- 사용자명
	reg_dt timestamp(6) NULL, -- 생성일
	download_url varchar(500) NULL, -- 다운로드 URL
	CONSTRAINT PK_cetus_file_log PRIMARY KEY (file_uid, file_id, log_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_file_log IS '파일 로그';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_file_log.log_uid IS '로그 UID';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.file_uid IS '파일 UID';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.file_id IS '파일 ID';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.worker_uid IS '사용자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.worker_nm IS '사용자명';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.reg_dt IS '생성일';
COMMENT ON COLUMN k_thirdeye.cetus_file_log.download_url IS '다운로드 URL';


-- k_thirdeye.cetus_group_user definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_group_user;

CREATE TABLE k_thirdeye.cetus_group_user (
	group_uid int8 NOT NULL, -- 소속 UID
	user_uid int8 NOT NULL, -- 유저 UID
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp(6) DEFAULT now() NOT NULL, -- 등록일
	CONSTRAINT cetus_group_user_pkey PRIMARY KEY (group_uid, user_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_group_user IS '유저 소속 정보';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_group_user.group_uid IS '소속 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group_user.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group_user.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group_user.reg_dt IS '등록일';


-- k_thirdeye.cetus_invite_code definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_invite_code;

CREATE TABLE k_thirdeye.cetus_invite_code (
	uid bigserial NOT NULL, -- 초대코드UID
	url text NOT NULL, -- 초대URL
	expiration_date timestamp NOT NULL, -- 만료일
	use_at bpchar NULL, -- 사용 여부
	email varchar(255) NOT NULL, -- 초대이메일
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 사용자 UID
	updt_dt timestamp NULL, -- 사용일
	CONSTRAINT cetus_invite_code_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_invite_code IS '초대코드';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_invite_code.uid IS '초대코드UID';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.url IS '초대URL';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.expiration_date IS '만료일';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.email IS '초대이메일';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.updt_uid IS '사용자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_invite_code.updt_dt IS '사용일';


-- k_thirdeye.cetus_menu_info definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_menu_info;

CREATE TABLE k_thirdeye.cetus_menu_info (
	menu_no bigserial NOT NULL, -- 메뉴UID
	program_uid int8 NULL, -- 프로그램UID
	upper_menu_no int8 NULL, -- 상위메뉴UID
	menu_nm varchar(32) NULL, -- 메뉴이름
	menu_icon varchar(32) NULL, -- 메뉴아이콘
	sort_no varchar(32) NULL, -- 메뉴 정렬 순서
	menu_dc varchar(128) NULL, -- 메뉴설명
	use_at bpchar NULL, -- 메뉴 사용여부
	delete_at bpchar DEFAULT 'N'::bpchar NULL, -- 메뉴 삭제여부
	author_cd varchar(32) NULL, -- 메뉴 권한
	menu_style1 varchar(32) NULL, -- 하단 메뉴 스타일 (col-width)
	menu_style2 varchar(32) NULL, -- 하단 메뉴 스타일 (margin-left)
	root_menu_cd varchar(32) NULL, -- 루트 메뉴 코드
	workplace_uid int8 NULL, -- 워크플레이스 UID
	menu_style varchar(32) NULL, -- 상단 메뉴 스타일
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp(6) NULL, -- 등록일
	updt_uid int8 NULL, -- 사용자 UID
	updt_dt timestamp(6) NULL, -- 사용일
	CONSTRAINT pk_cetus_menu_info PRIMARY KEY (menu_no)
);
COMMENT ON TABLE k_thirdeye.cetus_menu_info IS '메뉴';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_no IS '메뉴UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.program_uid IS '프로그램UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.upper_menu_no IS '상위메뉴UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_nm IS '메뉴이름';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_icon IS '메뉴아이콘';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.sort_no IS '메뉴 정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_dc IS '메뉴설명';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.use_at IS '메뉴 사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.delete_at IS '메뉴 삭제여부';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.author_cd IS '메뉴 권한';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_style1 IS '하단 메뉴 스타일 (col-width)';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_style2 IS '하단 메뉴 스타일 (margin-left)';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.root_menu_cd IS '루트 메뉴 코드';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.menu_style IS '상단 메뉴 스타일';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.updt_uid IS '사용자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_menu_info.updt_dt IS '사용일';


-- k_thirdeye.cetus_mobigen_dataset definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_mobigen_dataset;

CREATE TABLE k_thirdeye.cetus_mobigen_dataset (
	uid bigserial NOT NULL, -- 메타데이터 고유 식별자 (PK)
	title varchar(1000) NOT NULL, -- 데이터셋 제목
	reg_dt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 데이터셋 등록일 (기본값: CURRENT_TIMESTAMP)
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 데이터셋 삭제 여부
	extdata jsonb NULL, -- 기타 메타데이터 (jsonb, 비정형 확장 필드)
	CONSTRAINT cetus_mobigen_dataset_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_mobigen_dataset IS '영상/데이터셋 메타데이터 기본 정보 저장 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset.uid IS '메타데이터 고유 식별자 (PK)';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset.title IS '데이터셋 제목';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset.reg_dt IS '데이터셋 등록일 (기본값: CURRENT_TIMESTAMP)';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset.delete_at IS '데이터셋 삭제 여부';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset.extdata IS '기타 메타데이터 (jsonb, 비정형 확장 필드)';


-- k_thirdeye.cetus_mobigen_dataset_tag definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_mobigen_dataset_tag;

CREATE TABLE k_thirdeye.cetus_mobigen_dataset_tag (
	uid bigserial NOT NULL, -- 태그 고유 식별자 (PK)
	metadata_id varchar(100) NOT NULL, -- 참조하는 메타데이터 ID (FK)
	tag_uid int8 NOT NULL, -- 태그 uid
	CONSTRAINT cetus_mobigen_dataset_tag_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_mobigen_dataset_tag IS '메타데이터와 연결된 태그 저장 테이블 (1:N)';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset_tag.uid IS '태그 고유 식별자 (PK)';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset_tag.metadata_id IS '참조하는 메타데이터 ID (FK)';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_dataset_tag.tag_uid IS '태그 uid';


-- k_thirdeye.cetus_mobigen_tag definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_mobigen_tag;

CREATE TABLE k_thirdeye.cetus_mobigen_tag (
	uid bigserial NOT NULL, -- 태그  고유 식별자 (PK)
	tag_nm varchar(500) NOT NULL, -- 태그명
	CONSTRAINT cetus_mobigen_tag_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_mobigen_tag_tag_nm_key UNIQUE (tag_nm)
);
COMMENT ON TABLE k_thirdeye.cetus_mobigen_tag IS '태그 저장 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_mobigen_tag.uid IS '태그  고유 식별자 (PK)';
COMMENT ON COLUMN k_thirdeye.cetus_mobigen_tag.tag_nm IS '태그명';


-- k_thirdeye.cetus_position_user definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_position_user;

CREATE TABLE k_thirdeye.cetus_position_user (
	position_uid int8 NOT NULL, -- 직급 UID
	user_uid int8 NOT NULL, -- 유저 UID
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	CONSTRAINT cetus_position_user_pkey PRIMARY KEY (position_uid, user_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_position_user IS '유저 직급 정보';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_position_user.position_uid IS '직급 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position_user.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position_user.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position_user.reg_dt IS '등록일';


-- k_thirdeye.cetus_progrm_info definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_progrm_info;

CREATE TABLE k_thirdeye.cetus_progrm_info (
	uid bigserial NOT NULL, -- 프로그램UID
	progrm_nm varchar(32) NULL, -- 프로그램명
	url varchar(64) NULL, -- 프로그램URL
	progrm_dc varchar(256) NULL, -- 프로그램설명
	use_at bpchar NULL, -- 사용여부
	workplace_uid int8 NULL, -- 워크플레이스 UID
	left_slide_img int8 NULL, -- 상단 왼쪽 슬라이드 이미지
	right_slide_img int8 NULL, -- 상단 오른쪽 슬라이드 이미지
	logo_img int8 NULL, -- 메인 로고 리미지
	company_logo_img int8 NULL, -- 회사 로고 이미지
	title1 varchar(1000) NULL, -- 푸터 제목1
	title2 varchar(1000) NULL, -- 푸터 제목2
	title3 varchar(1000) NULL, -- 푸터 제목3
	is_root_url bpchar DEFAULT 'N'::bpchar NOT NULL, -- 루트 URL 여부
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp(6) NULL, -- 등록일
	updt_uid int8 NULL, -- 사용자 UID
	updt_dt timestamp(6) NULL, -- 사용일
	CONSTRAINT pk_cetus_progrm_info PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_progrm_info IS '프로그램';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.uid IS '프로그램UID';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.progrm_nm IS '프로그램명';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.url IS '프로그램URL';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.progrm_dc IS '프로그램설명';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.use_at IS '사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.left_slide_img IS '상단 왼쪽 슬라이드 이미지';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.right_slide_img IS '상단 오른쪽 슬라이드 이미지';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.logo_img IS '메인 로고 리미지';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.company_logo_img IS '회사 로고 이미지';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.title1 IS '푸터 제목1';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.title2 IS '푸터 제목2';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.title3 IS '푸터 제목3';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.is_root_url IS '루트 URL 여부';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.updt_uid IS '사용자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_progrm_info.updt_dt IS '사용일';


-- k_thirdeye.cetus_sys_code definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_sys_code;

CREATE TABLE k_thirdeye.cetus_sys_code (
	uid bigserial NOT NULL, -- 코드 UID
	code varchar(32) NOT NULL, -- 코드
	upper_code varchar(32) NULL, -- 상위 코드
	code_nm varchar(32) NULL, -- 코드명
	code_dc varchar(256) NULL, -- 코드 설명
	use_at bpchar NULL, -- 사용 여부
	rm_dc varchar(256) NULL, -- 비고설명
	item1_val varchar(32) NULL, -- 항목1값
	item2_val varchar(32) NULL, -- 항목2값
	sort_no int8 NULL, -- 정렬 순서
	reg_uid int8 NULL, -- 작성자 UID
	reg_dt timestamp(6) DEFAULT now() NULL, -- 작성일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp(6) DEFAULT now() NULL, -- 수정일
	CONSTRAINT pk_cetus_sys_code PRIMARY KEY (uid, code)
);
COMMENT ON TABLE k_thirdeye.cetus_sys_code IS '시스템 코드';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_sys_code.uid IS '코드 UID';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.code IS '코드';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.upper_code IS '상위 코드';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.code_nm IS '코드명';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.code_dc IS '코드 설명';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.rm_dc IS '비고설명';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.item1_val IS '항목1값';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.item2_val IS '항목2값';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.sort_no IS '정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.reg_uid IS '작성자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.reg_dt IS '작성일';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_sys_code.updt_dt IS '수정일';


-- k_thirdeye.cetus_thirdeye_registrant definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_thirdeye_registrant;

CREATE TABLE k_thirdeye.cetus_thirdeye_registrant (
	uid int8 DEFAULT nextval('k_thirdeye.cetus_mobigen_registrant_uid_seq'::regclass) NOT NULL, -- 고유 ID (자동 증가)
	metadata_id varchar(100) NOT NULL, -- 데이터셋 ID
	registrant_uid int8 NOT NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일시 (기본값: 현재 시간)
	CONSTRAINT cetus_mobigen_registrant_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_thirdeye_registrant IS '모비젠 데이터셋 등록자 정보 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_thirdeye_registrant.uid IS '고유 ID (자동 증가)';
COMMENT ON COLUMN k_thirdeye.cetus_thirdeye_registrant.metadata_id IS '데이터셋 ID';
COMMENT ON COLUMN k_thirdeye.cetus_thirdeye_registrant.registrant_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_thirdeye_registrant.reg_dt IS '등록일시 (기본값: 현재 시간)';


-- k_thirdeye.cetus_user definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_user;

CREATE TABLE k_thirdeye.cetus_user (
	uid bigserial NOT NULL, -- 유저UID
	user_id varchar(32) NOT NULL, -- 유저ID
	password varchar(256) NOT NULL, -- 비밀번호
	user_nm varchar(32) NOT NULL, -- 유저이름
	user_email varchar(32) NOT NULL, -- 유저 이메일
	approve_at bpchar DEFAULT 'Y'::bpchar NOT NULL, -- 승인여부
	use_at bpchar DEFAULT 'Y'::bpchar NOT NULL, -- 사용여부
	fail_cnt int4 NULL, -- 비밀번호 실패 횟수
	author_cd varchar(32) NULL, -- 유저권한
	profile_uid int8 NULL, -- 프로필
	meta_data jsonb NULL, -- 추가 정보
	status varchar(32) NULL, -- 유저 상태
	reg_uid int8 NULL,
	reg_dt timestamp(6) NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자UID
	updt_dt timestamp(6) NOT NULL, -- 수정일
	CONSTRAINT pk_cetus_user PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_user IS '유저';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_user.uid IS '유저UID';
COMMENT ON COLUMN k_thirdeye.cetus_user.user_id IS '유저ID';
COMMENT ON COLUMN k_thirdeye.cetus_user.password IS '비밀번호';
COMMENT ON COLUMN k_thirdeye.cetus_user.user_nm IS '유저이름';
COMMENT ON COLUMN k_thirdeye.cetus_user.user_email IS '유저 이메일';
COMMENT ON COLUMN k_thirdeye.cetus_user.approve_at IS '승인여부';
COMMENT ON COLUMN k_thirdeye.cetus_user.use_at IS '사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_user.fail_cnt IS '비밀번호 실패 횟수';
COMMENT ON COLUMN k_thirdeye.cetus_user.author_cd IS '유저권한';
COMMENT ON COLUMN k_thirdeye.cetus_user.profile_uid IS '프로필';
COMMENT ON COLUMN k_thirdeye.cetus_user.meta_data IS '추가 정보';
COMMENT ON COLUMN k_thirdeye.cetus_user.status IS '유저 상태';
COMMENT ON COLUMN k_thirdeye.cetus_user.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_user.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_user.updt_dt IS '수정일';


-- k_thirdeye.cetus_user_status_hist definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_user_status_hist;

CREATE TABLE k_thirdeye.cetus_user_status_hist (
	uid bigserial NOT NULL, -- UID
	user_uid int8 NOT NULL, -- 유저 UID
	reason text NULL, -- 상태 변경 사유
	status varchar(32) NOT NULL, -- 상태
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	CONSTRAINT cetus_user_status_hist_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_user_status_hist IS '유저 상태 이력';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.user_uid IS '유저 UID';
COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.reason IS '상태 변경 사유';
COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.status IS '상태';
COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_user_status_hist.reg_dt IS '등록일';


-- k_thirdeye.cetus_workplace definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_workplace;

CREATE TABLE k_thirdeye.cetus_workplace (
	uid bigserial NOT NULL, -- UID
	name varchar(64) NOT NULL, -- 워크플레이스 명
	reg_uid int8 NOT NULL, -- 등록자UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자UID
	updt_dt timestamp NULL, -- 수정일
	CONSTRAINT cetus_workplace_pkey PRIMARY KEY (uid)
);
COMMENT ON TABLE k_thirdeye.cetus_workplace IS '워크플레이스';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_workplace.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace.name IS '워크플레이스 명';
COMMENT ON COLUMN k_thirdeye.cetus_workplace.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_workplace.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace.updt_dt IS '수정일';


-- k_thirdeye.cetus_workplace_user definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_workplace_user;

CREATE TABLE k_thirdeye.cetus_workplace_user (
	user_uid int8 NOT NULL, -- 유저UID
	workplace_uid int8 NOT NULL, -- 워크플레이스UID
	reg_uid int8 NULL, -- 등록자UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자UID
	updt_dt timestamp NULL -- 수정일
);
COMMENT ON TABLE k_thirdeye.cetus_workplace_user IS '워크플레이스 유저 매핑';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.user_uid IS '유저UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.workplace_uid IS '워크플레이스UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_workplace_user.updt_dt IS '수정일';


-- k_thirdeye.cetus_bbs definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_bbs;

CREATE TABLE k_thirdeye.cetus_bbs (
	bbs_uid bigserial NOT NULL, -- 게시판UID
	bbs_nm varchar(255) NOT NULL, -- 게시판명
	bbs_tp_cd varchar(128) NOT NULL, -- 게시판 유형
	use_at bpchar NOT NULL, -- 사용여부
	delete_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 삭제여부
	workplace_uid int8 NOT NULL, -- 워크플레이스UID
	bbs_cl_use_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 분류 사용 여부
	atch_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 첨부파일 사용 여부
	atch_num int4 DEFAULT 0 NOT NULL, -- 첨부파일 개수
	upload_cpcty int8 NULL, -- 첨부파일 최대용량(byte)
	answer_use_at bpchar DEFAULT 'N'::bpchar NOT NULL, -- 댓글(답변) 사용여부
	reg_uid int8 NOT NULL, -- 등록자UID
	reg_dt timestamp(6) NOT NULL, -- 등록일시
	updt_uid int8 NOT NULL, -- 수정자UID
	updt_dt timestamp(6) NOT NULL, -- 수정일시
	CONSTRAINT PK_cetus_bbs PRIMARY KEY (bbs_uid),
	CONSTRAINT cetus_bbs_cetus_workplace_fk FOREIGN KEY (workplace_uid) REFERENCES k_thirdeye.cetus_workplace(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_bbs IS '게시판';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_bbs.bbs_uid IS '게시판UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.bbs_nm IS '게시판명';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.bbs_tp_cd IS '게시판 유형';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.use_at IS '사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.delete_at IS '삭제여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.workplace_uid IS '워크플레이스UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.bbs_cl_use_at IS '분류 사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.atch_at IS '첨부파일 사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.atch_num IS '첨부파일 개수';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.upload_cpcty IS '첨부파일 최대용량(byte)';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.answer_use_at IS '댓글(답변) 사용여부';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.reg_uid IS '등록자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.reg_dt IS '등록일시';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.updt_uid IS '수정자UID';
COMMENT ON COLUMN k_thirdeye.cetus_bbs.updt_dt IS '수정일시';


-- k_thirdeye.cetus_dataset_file_log definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_file_log;

CREATE TABLE k_thirdeye.cetus_dataset_file_log (
	log_uid bigserial NOT NULL, -- 로그 UID
	file_uid int8 NOT NULL, -- 파일 UID
	file_id varchar(500) NOT NULL, -- 파일 ID
	worker_uid varchar(32) NULL, -- 사용자 UID
	worker_nm varchar(32) NULL, -- 사용자명
	reg_dt timestamp(6) NULL, -- 등록일 (생성일시)
	download_url varchar(500) NULL, -- 다운로드 URL
	CONSTRAINT pk_cetus_dataset_file_log PRIMARY KEY (file_uid, file_id, log_uid),
	CONSTRAINT fk_cetus_dataset_file_log_file FOREIGN KEY (file_uid) REFERENCES k_thirdeye.cetus_dataset_file(file_uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_file_log IS '데이터셋 파일 로그';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.log_uid IS '로그 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.file_uid IS '파일 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.file_id IS '파일 ID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.worker_uid IS '사용자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.worker_nm IS '사용자명';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.reg_dt IS '등록일 (생성일시)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_file_log.download_url IS '다운로드 URL';


-- k_thirdeye.cetus_dataset_history definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_history;

CREATE TABLE k_thirdeye.cetus_dataset_history (
	history_uid bigserial NOT NULL, -- PK (Auto Increment)
	approved_dataset_uid int8 NOT NULL, -- FK → cetus_approved_dataset.uid
	chng_cnt jsonb NOT NULL, -- 변경내용 JSONB (UI 테이블 상태 전체)
	chng_dt timestamp NOT NULL, -- 수정일시
	chng_uid int8 NOT NULL, -- 변경자 UID
	CONSTRAINT cetus_dataset_history_pkey PRIMARY KEY (history_uid),
	CONSTRAINT fk_history_approved_dataset FOREIGN KEY (approved_dataset_uid) REFERENCES k_thirdeye.cetus_approved_dataset(uid) ON DELETE CASCADE
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_history IS '데이터셋의 승인 및 UI 변경 이력 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_history.history_uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_history.approved_dataset_uid IS 'FK → cetus_approved_dataset.uid';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_history.chng_cnt IS '변경내용 JSONB (UI 테이블 상태 전체)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_history.chng_dt IS '수정일시';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_history.chng_uid IS '변경자 UID';


-- k_thirdeye.cetus_dataset_ui definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dataset_ui;

CREATE TABLE k_thirdeye.cetus_dataset_ui (
	uid bigserial NOT NULL, -- PK (Auto Increment)
	approved_dataset_uid int8 NOT NULL, -- FK → cetus_approved_dataset.uid
	sort_no int8 NOT NULL, -- UI 정렬 순서
	main_ui_uid int8 NOT NULL, -- FK → cetus_dataset_main_ui.uid
	show_at bpchar(1) DEFAULT 'Y'::bpchar NOT NULL, -- 데이터셋 show 여부 (Y/N)
	thumb_uid int8 NULL, -- 썸네일 파일 UID
	reg_uid int8 NOT NULL, -- 작성자 UID
	reg_dt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 작성일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	extra_json jsonb NULL, -- 데이터셋 정보를 위한 부가적인 정보
	category_uid int8 NOT NULL, -- FK → cetus_dataset_category.uid (데이터셋 카테고리 UID)
	CONSTRAINT cetus_dataset_ui_pkey PRIMARY KEY (uid),
	CONSTRAINT fk_ui_approved_dataset FOREIGN KEY (approved_dataset_uid) REFERENCES k_thirdeye.cetus_approved_dataset(uid) ON DELETE CASCADE,
	CONSTRAINT fk_ui_main_ui FOREIGN KEY (main_ui_uid) REFERENCES k_thirdeye.cetus_dataset_main_ui(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dataset_ui IS '데이터셋의 UI 정의 테이블';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.uid IS 'PK (Auto Increment)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.approved_dataset_uid IS 'FK → cetus_approved_dataset.uid';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.sort_no IS 'UI 정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.main_ui_uid IS 'FK → cetus_dataset_main_ui.uid';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.show_at IS '데이터셋 show 여부 (Y/N)';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.thumb_uid IS '썸네일 파일 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.reg_uid IS '작성자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.reg_dt IS '작성일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.updt_dt IS '수정일';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.extra_json IS '데이터셋 정보를 위한 부가적인 정보';
COMMENT ON COLUMN k_thirdeye.cetus_dataset_ui.category_uid IS 'FK → cetus_dataset_category.uid (데이터셋 카테고리 UID)';


-- k_thirdeye.cetus_dept definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_dept;

CREATE TABLE k_thirdeye.cetus_dept (
	uid bigserial NOT NULL, -- UID
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	name varchar(64) NOT NULL, -- 부서 명
	description text NULL, -- 부서 설명
	sort_order int8 NULL, -- 정렬 순서
	use_at bpchar NOT NULL, -- 사용 여부
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	upper_uid int8 NULL, -- 상위 UID
	CONSTRAINT cetus_dept_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_dept_cetus_workplace_fk FOREIGN KEY (workplace_uid) REFERENCES k_thirdeye.cetus_workplace(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_dept IS '부서';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_dept.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept.name IS '부서 명';
COMMENT ON COLUMN k_thirdeye.cetus_dept.description IS '부서 설명';
COMMENT ON COLUMN k_thirdeye.cetus_dept.sort_order IS '정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_dept.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_dept.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_dept.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_dept.updt_dt IS '수정일';
COMMENT ON COLUMN k_thirdeye.cetus_dept.upper_uid IS '상위 UID';


-- k_thirdeye.cetus_form_columns definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_form_columns;

CREATE TABLE k_thirdeye.cetus_form_columns (
	uid bigserial NOT NULL, -- UID
	type varchar(32) NOT NULL, -- 타입
	label varchar(64) NOT NULL, -- 라벨
	required bpchar NULL, -- 필수 여부
	description text NULL, -- 설명
	use_at bpchar NULL, -- 사용 여부
	name varchar(64) NOT NULL, -- 이름
	placeholder varchar NULL,
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	default_value varchar(64) NULL, -- 기본 값
	form_group varchar(32) NULL, -- 폼 그룹
	sort_num int4 NULL, -- 순서
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp(6) DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp(6) NULL, -- 수정일
	CONSTRAINT cetus_form_columns_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_form_columns_cetus_workplace_fk FOREIGN KEY (workplace_uid) REFERENCES k_thirdeye.cetus_workplace(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_form_columns IS '메타폼 컬럼';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_form_columns.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.type IS '타입';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.label IS '라벨';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.required IS '필수 여부';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.description IS '설명';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.name IS '이름';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.default_value IS '기본 값';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.form_group IS '폼 그룹';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.sort_num IS '순서';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_form_columns.updt_dt IS '수정일';


-- k_thirdeye.cetus_group definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_group;

CREATE TABLE k_thirdeye.cetus_group (
	uid bigserial NOT NULL, -- UID
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	name varchar(64) NOT NULL, -- 소속 명
	description text NULL, -- 소속 설명
	sort_order int8 NULL, -- 정렬 순서
	use_at bpchar NULL, -- 사용 여부
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	CONSTRAINT cetus_group_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_group_cetus_workplace_fk FOREIGN KEY (workplace_uid) REFERENCES k_thirdeye.cetus_workplace(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_group IS '소속';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_group.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_group.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group.name IS '소속 명';
COMMENT ON COLUMN k_thirdeye.cetus_group.description IS '소속 설명';
COMMENT ON COLUMN k_thirdeye.cetus_group.sort_order IS '정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_group.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_group.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_group.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_group.updt_dt IS '수정일';


-- k_thirdeye.cetus_position definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_position;

CREATE TABLE k_thirdeye.cetus_position (
	uid bigserial NOT NULL, -- UID
	workplace_uid int8 NOT NULL, -- 워크플레이스 UID
	name varchar(64) NOT NULL, -- 직급 명
	description text NULL, -- 직급 설명
	sort_order int8 NULL, -- 정렬 순서
	use_at bpchar NULL, -- 사용 여부
	reg_uid int8 NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	CONSTRAINT cetus_position_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_position_cetus_workplace_fk FOREIGN KEY (workplace_uid) REFERENCES k_thirdeye.cetus_workplace(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_position IS '직급';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_position.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_position.workplace_uid IS '워크플레이스 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position.name IS '직급 명';
COMMENT ON COLUMN k_thirdeye.cetus_position.description IS '직급 설명';
COMMENT ON COLUMN k_thirdeye.cetus_position.sort_order IS '정렬 순서';
COMMENT ON COLUMN k_thirdeye.cetus_position.use_at IS '사용 여부';
COMMENT ON COLUMN k_thirdeye.cetus_position.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_position.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_position.updt_dt IS '수정일';


-- k_thirdeye.cetus_user_login_hist definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_user_login_hist;

CREATE TABLE k_thirdeye.cetus_user_login_hist (
	user_uid int8 NOT NULL, -- 유저UID
	login_dt timestamp(6) DEFAULT now() NOT NULL, -- 로그인일시
	login_ip varchar(50) NOT NULL, -- 로그인접속IP
	login_browser varchar(300) NULL, -- 로그인 브라우저
	login_access_url varchar(300) NULL, -- 로그인접속URL
	session_id varchar(50) NOT NULL, -- 세션 ID
	login_region varchar(100) NULL, -- 로그인 지역
	CONSTRAINT PK_cetus_user_login_hist PRIMARY KEY (user_uid, login_dt),
	CONSTRAINT FK_cetus_user_TO_cetus_user_login_hist FOREIGN KEY (user_uid) REFERENCES k_thirdeye.cetus_user(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_user_login_hist IS '유저 로그인 이력';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.user_uid IS '유저UID';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.login_dt IS '로그인일시';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.login_ip IS '로그인접속IP';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.login_browser IS '로그인 브라우저';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.login_access_url IS '로그인접속URL';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.session_id IS '세션 ID';
COMMENT ON COLUMN k_thirdeye.cetus_user_login_hist.login_region IS '로그인 지역';


-- k_thirdeye.cetus_column_options definition

-- Drop table

-- DROP TABLE k_thirdeye.cetus_column_options;

CREATE TABLE k_thirdeye.cetus_column_options (
	uid int8 DEFAULT nextval('k_thirdeye.cetus_columns_options_uid_seq'::regclass) NOT NULL, -- UID
	columns_uid int8 NOT NULL, -- 컬럼 UID
	label varchar(64) NULL, -- 라벨
	name varchar(64) NULL, -- 이름
	sort_num int4 NULL, -- 정렬순서
	reg_uid int8 NOT NULL, -- 등록자 UID
	reg_dt timestamp DEFAULT now() NOT NULL, -- 등록일
	updt_uid int8 NULL, -- 수정자 UID
	updt_dt timestamp NULL, -- 수정일
	CONSTRAINT cetus_columns_options_pkey PRIMARY KEY (uid),
	CONSTRAINT cetus_column_options___fk FOREIGN KEY (columns_uid) REFERENCES k_thirdeye.cetus_form_columns(uid)
);
COMMENT ON TABLE k_thirdeye.cetus_column_options IS '메타폼 옵션 컬럼';

-- Column comments

COMMENT ON COLUMN k_thirdeye.cetus_column_options.uid IS 'UID';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.columns_uid IS '컬럼 UID';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.label IS '라벨';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.name IS '이름';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.sort_num IS '정렬순서';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.reg_uid IS '등록자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.reg_dt IS '등록일';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.updt_uid IS '수정자 UID';
COMMENT ON COLUMN k_thirdeye.cetus_column_options.updt_dt IS '수정일';