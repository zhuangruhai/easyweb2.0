#!/bin/sh
if [ -n "$JAVA_HOME" ];then
echo "JAVA_HOME�Ѿ�����"
echo "$JAVA_HOME"
else
echo "����JAVA_HOME"
export JAVA_HOME="/opt/aspire/product/prm/jboss/jdk"
fi
