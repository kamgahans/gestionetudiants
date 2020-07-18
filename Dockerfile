FROM maven:3.5.2-jdk-8-alpine 
COPY . /usr/bin
EXPOSE 9090
WORKDIR /usr/bin
