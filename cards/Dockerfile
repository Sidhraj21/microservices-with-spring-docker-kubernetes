#Use the base image to run the application. If this image is not available locally ,then it is downloaded from the Dockerhub store 
# In the Dockerhub we can check which java file is available to download.
FROM openjdk:11-slim as build

#Information around who maintans the application
MAINTAINER sidhraj

#Copy commands tells where is the fat jar of the applicaton located. Mostly it is in the target folder. THis jar is copied to the file #system of the Docker and is used to create the docker image of the application. As Docker run in isolated enviornments, they have their own #filesystem, memory and network,etc.
COPY target/cards-0.0.1-SNAPSHOT.jar cards-0.0.1-SNAPSHOT.jar

#This command is run when the docker starts. It is run to execute the application.
ENTRYPOINT ["java","-jar","/cards-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
