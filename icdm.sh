#!/bin/sh

LIB_DIR=./lib

MAIN_CLASS="icdm_sim.Main"

LIBS="$LIB_DIR/commons-codec-1.6.jar:$LIB_DIR/commons-logging-1.1.3.jar:$LIB_DIR/fluent-hc-4.3.3.jar:$LIB_DIR/httpclient-4.3.3.jar:$LIB_DIR/httpclient-cache-4.3.3.jar:$LIB_DIR/httpcore-4.3.2.jar:$LIB_DIR/httpmime-4.3.3.jar:./build/jar/icdm.jar"

java -classpath "$LIBS" "$MAIN_CLASS" "$@"