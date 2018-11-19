FROM openjdk:12-alpine

ADD . /build

WORKDIR /build

RUN ./gradlew jar

COPY build/libs/tic-tac-toe-2.0.jar /opt/tic-tac-toe.jar
RUN rm /build -rf

ENTRYPOINT ["java", "-jar", "/opt/tic-tac-toe.jar"]