FROM emobilitybaseimages.azurecr.io/eclipse-temurin:17.0.3_7-jdk-alpine
VOLUME /tmp
COPY ./build/docker/*.jar ./build/docker/image/app.jar
ENTRYPOINT ["java","--enable-preview","-jar","./build/docker/image/app.jar"]
EXPOSE 8080
