# Stage 1: Build
FROM eclipse-temurin:11-jdk-focal AS build
ARG BUILD_PROFILE=kube
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
# 실행 가능한 bootWar 하나만 남긴다. war 플러그인이 함께 만드는 *-plain.war 가 있으면
# 아래 COPY 의 *.war 가 두 파일에 걸려 빌드가 실패한다.
# 빌드 프로파일은 런타임 SPRING_PROFILES_ACTIVE 와 맞춘다(기본 kube, docker-compose 는 prod 로 전달).
RUN ./gradlew bootWar -Pprofile=${BUILD_PROFILE} -x test --no-daemon \
    && rm -f build/libs/*-plain.war

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
