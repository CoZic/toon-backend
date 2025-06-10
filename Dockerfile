# Stage 1: 빌드 스테이지
# Java 17 SDK가 포함된 이미지 사용 (빌드 도구 포함)
FROM openjdk:17-jdk-slim AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper 및 설정 파일 복사
# 프로젝트 루트에 있는 gradle 관련 파일들을 복사합니다.
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# 소스 코드 복사 (src 디렉토리 포함)
COPY src ./src

# Gradle (또는 Maven)을 사용하여 애플리케이션 빌드
# 여기서 .jar 파일이 생성됩니다.
# 정확한 빌드 명령어와 jar 파일 생성 경로를 확인하세요.
# Spring Boot 프로젝트의 경우 보통 build/libs/ 에 생성됩니다.
# 예: ./gradlew build -x test (테스트 스킵) 또는 ./gradlew bootJar (Spring Boot 전용)
RUN ./gradlew build --no-daemon

# Stage 2: 런타임 스테이지
# Java 17 JRE만 포함된 경량 이미지 사용 (최종 이미지 크기 감소)
FROM openjdk:17

# 작업 디렉토리 설정 (옵션)
WORKDIR /app

# 빌드 스테이지에서 생성된 .jar 파일을 최종 이미지로 복사
# /app/build/libs/ 아래의 모든 .jar 파일 중 하나를 app.jar로 복사
# 파일 이름이 확실하다면 prac_toon-0.0.1-SNAPSHOT.jar 대신 app.jar로 통일하는 것이 관리하기 좋습니다.
COPY --from=build /app/build/libs/prac_toon-0.0.1-SNAPSHOT.jar /app/app.jar

# 애플리케이션이 리스닝할 포트 노출 (선택 사항이지만 권장)
EXPOSE 8080

# 컨테이너 시작 시 실행될 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]