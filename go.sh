#!/bin/bash

mvn clean package
java -jar target/Coffee-Service-1.0.0-SNAPSHOT.jar
