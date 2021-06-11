FROM chaitanyaubijawe/java-8-security-fix:latest
EXPOSE 8080

COPY build/libs/eShop-0.0.1-SNAPSHOT.jar /opt/app/server.jar
COPY run.sh /run.sh
RUN chmod 777 /run.sh
ENTRYPOINT ["/run.sh"]
