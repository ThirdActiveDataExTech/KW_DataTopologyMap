#!/bin/bash
set -e

# 모든 설치가 완료된 후 실행되는 마무리 스크립트
# 관리자(postgres)가 만든 모든 테이블, 시퀀스 등의 소유권을 유저에게 이전합니다.

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
    -- 1. 스키마 자체의 소유권 이전
    ALTER SCHEMA k_thirdeye OWNER TO "$DB_USERNAME";

    -- 2. k_thirdeye 스키마 내의 모든 테이블 소유권 이전
    DO \$\$ 
    DECLARE 
        r RECORD;
    BEGIN
        FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'k_thirdeye') LOOP
            EXECUTE 'ALTER TABLE k_thirdeye.' || quote_ident(r.tablename) || ' OWNER TO "$DB_USERNAME"';
        END LOOP;
    END \$\$;

    -- 3. k_thirdeye 스키마 내의 모든 시퀀스 소유권 이전
    DO \$\$ 
    DECLARE 
        r RECORD;
    BEGIN
        FOR r IN (SELECT relname FROM pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace WHERE n.nspname = 'k_thirdeye' AND c.relkind = 'S') LOOP
            EXECUTE 'ALTER SEQUENCE k_thirdeye.' || quote_ident(r.relname) || ' OWNER TO "$DB_USERNAME"';
        END LOOP;
    END \$\$;

    -- 4. k_thirdeye 스키마 내의 모든 뷰 소유권 이전
    DO \$\$ 
    DECLARE 
        r RECORD;
    BEGIN
        FOR r IN (SELECT viewname FROM pg_views WHERE schemaname = 'k_thirdeye') LOOP
            EXECUTE 'ALTER VIEW k_thirdeye.' || quote_ident(r.viewname) || ' OWNER TO "$DB_USERNAME"';
        END LOOP;
    END \$\$;

    -- 향후 관리자가 만드는 객체에 대해서도 모든 권한을 갖도록 설정
    ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON TABLES TO "$DB_USERNAME";
    ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON SEQUENCES TO "$DB_USERNAME";
    ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON FUNCTIONS TO "$DB_USERNAME";
    ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON TYPES TO "$DB_USERNAME";
EOSQL
