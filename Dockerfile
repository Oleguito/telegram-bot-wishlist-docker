FROM maven:3.9.6-eclipse-temurin-17-alpine as build

#ARG POSTGRES_HOST
#ENV POSTGRES_HOST=${POSTGRES_HOST}

COPY . /bin/app
WORKDIR /bin/app

# RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests

ENTRYPOINT mvn exec:java -Dexec.mainClass="Main"

#FROM maven:3.9.6-eclipse-temurin-17-alpine




#FROM openjdk:17-jdk-alpine
#RUN mkdir /bin/app
#WORKDIR /bin/app
#
## COPY ./pom.xml /bin/app/pom.xml
## COPY ./src /bin/app/src
## COPY ./META-INF/ /bin/app/META-INF/
## COPY ./META-INF/MANIFEST.MF /bin/app/META-INF/MANIFEST.MF
## COPY --from=build /bin/app/target /bin/app
#COPY --from=build /bin/app/target/*.jar /bin/app/app.jar
#
## RUN jar cmvf META-INF/MANIFEST.MF /bin/app/app.jar src/
#
#ENTRYPOINT java -jar /bin/app/app.jar