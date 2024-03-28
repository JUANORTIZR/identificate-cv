FROM amazoncorretto:17

LABEL autor=onek.code

WORKDIR /app

COPY /target/*.jar /app/app.jar

EXPOSE 9812

ENV ACCESS_KEY=AKIA4MTWKCBYRROPE6F2
ENV CLIENT_ID=1e7d3gb8be6ivdt3u38pn79a1v
ENV CLIENT_SECRET=1le7tckkaorpsma7135s3b3s84nf2eb75bfbctakb75fmqs4ct4d
ENV SECRET_KEY=PDTuBM7aeDiJtysXoNoDPpLir2NRfYSuqB6bxHUV

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

