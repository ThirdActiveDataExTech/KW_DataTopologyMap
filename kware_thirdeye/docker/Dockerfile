# Stage 1: Build
FROM eclipse-temurin:11-jdk-focal AS build
WORKDIR /app

# Copy gradle files for caching
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
COPY libs ./libs
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew build -x test --no-daemon || return 0

# Copy source code
COPY src ./src

# Build the final WAR file
# 기본적으로 prod 프로파일로 빌드하거나, 필요에 따라 수정 가능합니다.
RUN ./gradlew build -Pprofile=prod -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:11-jre-focal
WORKDIR /app

# 기본 설정 파일들을 백업해둘 디렉토리 생성
RUN mkdir -p /app/defaults /app/config /app/logs

# 빌드 결과물(WAR) 복사
COPY --from=build /app/build/libs/*.war app.war

# 기본 설정 파일들을 defaults 폴더에 복사 (Entrypoint에서 사용)
# 프로젝트 구조에 맞게 경로 조정
COPY src/main/resources/application.yml /app/defaults/
# resources-env 하위의 모든 설정 파일들도 복사해둠
COPY src/main/resources-env /app/defaults/resources-env

# 시작 스크립트 복사 및 권한 부여
COPY docker/entrypoint.sh ./
RUN chmod +x entrypoint.sh

EXPOSE 9094
ENTRYPOINT ["./entrypoint.sh"]
