#!/bin/bash
set -e
# any runtime args. depending upon env.
JAVA_OPTS=""
JAVA_OPTS="-Dfile.encoding=UTF-8 $JAVA_OPTS"
exec java $JVM_OPTS $JAVA_OPTS -jar /opt/app/server.jar