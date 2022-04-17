FROM openjdk:11-jre
COPY target/favorito-*SNAPSHOT.jar /opt/favorito.jar
ENTRYPOINT ["java","-jar","/opt/favorito.jar"]