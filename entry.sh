#!/bin/bash

mkdir temp

cd docker-files/jdk && docker build -t online-compiler-jdk .
cd ../../
cd docker-files/golang12 && docker build -t online-compiler-golang12 .
cd ../../
cd docker-files/python3 && docker build -t online-compiler-python3 .
cd ../../
cd docker-files/node20 && docker build -t online-compiler-node20 .
cd ../../
cd docker-files/gcc11 && docker build -t online-compiler-gcc11 .
cd /opt/app
java -jar /opt/app/*.jar
