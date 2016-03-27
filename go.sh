#!/bin/bash

if [ ! -x "$(which mvn)" ]; then
    echo "Please install maven to continue: https://maven.apache.org"
fi

if [ ! -x "$(which redis-server)" ]; then
    echo "Please install redis to continue: http://redis.io"
fi

case "$1" in
    run)
        export REDISTOGO_URL="redis://:@localhost:6379"
        mvn clean package
        java -jar target/Coffee-Service-1.0.0-SNAPSHOT.jar
        ;;
    *)
        echo "Usage: ./go.sh run"
        ;;
esac
