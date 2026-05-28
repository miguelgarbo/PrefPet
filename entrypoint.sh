#!/bin/sh
export CATALINA_OPTS="\
  -Djavax.net.ssl.trustStore=/usr/local/tomcat/conf/client_truststore.jks \
  -Djavax.net.ssl.trustStorePassword=${TRUSTSTORE_PASSWORD} \
  -Djavax.net.ssl.trustStoreType=JKS"

exec catalina.sh run