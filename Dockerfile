FROM tomcat:11-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/backendprefpet.war /usr/local/tomcat/webapps/
COPY certificado.p12 /usr/local/tomcat/conf/certificado.p12
COPY client_truststore.jks /usr/local/tomcat/conf/client_truststore.jks
COPY server.xml /usr/local/tomcat/conf/server.xml

EXPOSE 8443

COPY entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]