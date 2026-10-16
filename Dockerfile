
FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY TamilLiteratureCharacterExplorer/ /app/
RUN javac --add-modules jdk.httpserver Main.java
EXPOSE 8080
CMD ["java", "--add-modules", "jdk.httpserver", "Main"]
