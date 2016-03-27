#!/bin/bash

export REDISTOGO_URL="redis://:@localhost:6379"

mvn clean package
java -jar target/Coffee-Service-1.0.0-SNAPSHOT.jar
