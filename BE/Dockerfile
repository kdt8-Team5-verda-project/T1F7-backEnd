FROM openjdk:17
LABEL maintainer="qhdid3734@naver.com"
VOLUME /tmp
EXPOSE 8085
ARG JAR_FILE=build/libs/BE-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} BE.jar
ENTRYPOINT ["java","-jar","/BE.jar"]