FROM openjdk:14-alpine
COPY build/libs/test-plaintext-*-all.jar test-plaintext.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "test-plaintext.jar"]