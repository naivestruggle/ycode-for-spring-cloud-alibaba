FROM openjdk:8-jre

RUN wget http://192.168.31.10:40001/static-resources/dockerize-linux-amd64-v0.6.1.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz \
    && rm dockerize-linux-amd64-v0.6.1.tar.gz

RUN wget http://192.168.31.10:40001/static-resources/apache-skywalking-apm-6.6.0.tar.gz \
    && tar -C /usr/local -zxvf apache-skywalking-apm-6.6.0.tar.gz \
    && rm apache-skywalking-apm-6.6.0.tar.gz

RUN mkdir /app

COPY ycode-service-sso-admin-1.0.0-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["dockerize", \
"-timeout", \
"5m", \
"-wait", \
"http://192.168.31.13:8848/nacos/", \
"java", \
"-javaagent:/usr/local/apache-skywalking-apm-bin/agent/skywalking-agent.jar", \
"-Dskywalking.agent.service_name=ycode-service-sso-admin", \
"-Dskywalking.collector.backend_service=192.168.31.13:11800", \
"-Djava.security.egd=file:/dev/./urandom", \
"-jar", \
"/app/app.jar", \
"--spring.profiles.active=prod"]

EXPOSE 42003