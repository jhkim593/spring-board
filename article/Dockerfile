# =========================================================
# 1단계: 빌드(Build) 환경
# =========================================================
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

# 빌드 속도 최적화: 의존성 관련 파일만 먼저 복사
COPY gradlew /app/
COPY gradle /app/gradle/
COPY build.gradle /app/
COPY settings.gradle /app/

# 의존성 다운로드 (소스 코드 변경 시 이 레이어는 캐시됨)
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true

# 소스 코드 복사
COPY common /app/common/
COPY article /app/article/

# 빌드 실행
RUN ./gradlew :article:bootJar -x test --no-daemon

# =========================================================
# 2단계: 실행(Runtime) 환경
# =========================================================
FROM azul/zulu-openjdk:17

WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일만 복사
COPY --from=builder /app/article/build/libs/*.jar app.jar

EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]