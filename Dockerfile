FROM openjdk:8
COPY ./out/artifacts/TestTaskSPBU_jar/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar" ,"TestTaskSPBU.jar"]