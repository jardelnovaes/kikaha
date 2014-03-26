#!/bin/sh
cd "`dirname $0`/.."

# CONFIGURABLE VARIABLES
LIBDIR="./lib"
APPDIR="./webapp"
WEBLIB="${APPDIR}/WEB-INF/lib"
WEBCLASSES="${APPDIR}/WEB-INF/classes/"
HOST=localhost
PORT=9000

# RUNTIME VARIABLES
JAVA=java
JAVA_OPTS=
MAIN_CLASS=com.texoit.undertow.standalone.Main

# READ CUSTOM CONFIGURATIONS
if [ -e bin/undertow.conf ]; then
	. bin/undertow.conf
fi

# MAIN
JAVA_OPTS="-Dundertow.standalone.libdir=${LIBDIR} \
	-Dundertow.standalone.appdir=${APPDIR} \
	-Dundertow.standalone.host=${HOST} \
	-Dundertow.standalone.port=${PORT} ${JAVA_OPTS}"

LIB_CLASSPATH=`ls ${LIBDIR}/*.jar | tr '\n' ' ' | sed 's/  */ /g' | tr ' ' ':'`
WEBLIB_CLASSPATH=`ls ${WEBLIB}/*.jar | tr '\n' ' ' | sed 's/  */ /g' | tr ' ' ':'`
CLASSPATH=${LIB_CLASSPATH}:${WEBLIB_CLASSPATH}:${WEBCLASSES}

echo "${JAVA} ${JAVA_OPTS} -classpath "${CLASSPATH}" ${MAIN_CLASS}"
${JAVA} ${JAVA_OPTS} -classpath "${CLASSPATH}" ${MAIN_CLASS}
