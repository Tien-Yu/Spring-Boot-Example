
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template
# spring-boot-security2
FROM openjdk:11
RUN mkdir /app
COPY target/securityjpa-0.0.1-SNAPSHOT.jar /app/securityjpa.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/securityjpa.jar"]
EXPOSE 8443
