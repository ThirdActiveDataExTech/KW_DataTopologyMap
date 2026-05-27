#!/bin/sh
set -e

# 설정 디렉토리 생성
mkdir -p /app/config

# 1. 기본 application.yml 파일 체크
if [ ! -f /app/config/application.yml ]; then
    echo "Config: application.yml not found in /app/config. Copying from defaults..."
    cp /app/defaults/application.yml /app/config/application.yml
fi

# 2. 활성화된 프로파일에 따른 설정 파일 체크
if [ ! -z "$SPRING_PROFILES_ACTIVE" ]; then
    echo "Active Profile: $SPRING_PROFILES_ACTIVE"
    # 프로파일 폴더 경로 (예: resources-env/prod/application.yml)
    # 프로젝트 구조에 따라 파일명이 application.yml로 동일하고 폴더만 다를 수 있음
    DEFAULT_PROFILE_PATH="/app/defaults/resources-env/$SPRING_PROFILES_ACTIVE"
    
    # 외부 볼륨의 프로파일별 설정을 저장할 파일명 (중복 방지를 위해 application-{profile}.yml 권장)
    EXTERNAL_PROFILE_YML="application-$SPRING_PROFILES_ACTIVE.yml"
    
    if [ ! -f "/app/config/$EXTERNAL_PROFILE_YML" ]; then
        if [ -d "$DEFAULT_PROFILE_PATH" ]; then
            echo "Config: $EXTERNAL_PROFILE_YML not found. Copying from $DEFAULT_PROFILE_PATH..."
            # 해당 폴더 내의 모든 설정을 복사하거나, 특정 파일만 복사
            cp -r "$DEFAULT_PROFILE_PATH"/* /app/config/
            # 만약 파일명이 application.yml이라면 구분을 위해 이름을 바꿀 수도 있지만, 
            # spring.config.location을 쓸 때는 순서가 중요함
        else
            echo "Warning: No default config found for profile $SPRING_PROFILES_ACTIVE in /app/defaults/resources-env/"
        fi
    fi
fi

# 로그 디렉토리 준비
mkdir -p /app/logs

echo "Starting Application with profile: $SPRING_PROFILES_ACTIVE"

# Spring Boot 실행
# 외부 설정 위치를 지정하여 /app/config 내의 파일들이 내부 설정을 오버라이드하도록 함
exec java -Dspring.config.location=file:/app/config/,classpath:/application.yml -jar app.war
