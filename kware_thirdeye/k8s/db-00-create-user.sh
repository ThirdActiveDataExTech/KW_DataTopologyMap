#!/bin/bash
set -e

# Secret에서 주입된 DB_USERNAME과 DB_PASSWORD를 사용
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
    -- 1. 애플리케이션 운영용 사용자 생성
    CREATE USER "$DB_USERNAME" WITH PASSWORD '$DB_PASSWORD';

    -- 2. 관리자 권한 대행 허용
    GRANT "$DB_USERNAME" TO postgres;

    -- 3. 권한 부여
    GRANT ALL PRIVILEGES ON DATABASE postgres TO "$DB_USERNAME";
    GRANT ALL ON SCHEMA public TO "$DB_USERNAME";

    -- 4. 탐색 경로 설정
    ALTER USER "$DB_USERNAME" SET search_path TO k_thirdeye, public;
EOSQL
