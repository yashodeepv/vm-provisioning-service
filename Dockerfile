FROM java:8
EXPOSE 9191
ADD /target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
