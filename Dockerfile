FROM amazoncorretto:25-alpine AS builder
WORKDIR /build

RUN apk add --no-cache findutils bash

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew clean bootJar -x test --no-daemon

RUN java -Djarmode=layertools -jar build/libs/*.jar extract


FROM amazoncorretto:25-alpine
WORKDIR /application

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]