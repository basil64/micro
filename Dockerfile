FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY book-store/src src
#
#RUN ./mvnw clean install -DskipTests
COPY book-store/target/book-store-1.0-SNAPSHOT.jar .
RUN mkdir -p book-store/target/dependency
RUN cd book-store/target/dependency
RUN pwd
RUN ls -l
RUN jar -xf /workspace/app/book-store-1.0-SNAPSHOT.jar

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/book-store/target/dependency
RUN ls -l /workspace/app/book-store/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.infinitum.BlueFireApplication"]