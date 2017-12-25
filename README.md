# merapar-assessment

Project assessment from Merapar

### Prerequisites

To run the distributable Docker container you need Docker to be installed.

* [Install Docker](https://docs.docker.com/engine/installation/) - Installation guide


### Running the app

#### Option 1 - Using Dockerhub image

Assuming Docker is installed and running, we need first to pull the image 
```
$ docker pull jherrador/merapar-assessment
```
Once we pull the image, we just have to run it 
```
$ docker run -p 8080:8080 -t jherrador/merapar-assessment
```


#### Option 2 - Building image in local
Assuming Docker is installed and running and you have cloned the repository where this README.md file is, first you need to build the image using maven (already included in the repository)
```
$ ./mvnw install dockerfile:build
```
Once the image is created, we just have to run it 
```
$ docker run -p 8080:8080 -t jherrador/merapar-assessment
```

### Running the app
Assuming the app is up and running, we need to know the IP where it is running. If running in Linux it will be localhost, 
but if running in Mac with boot2docker or similar, execute this command to know the IP 
```
$ echo $DOCKER_HOST
> tcp://192.168.99.100:2376 
```
This command will print the IP where docker is reachable (ignore the port). The we can call  the app
```
$ curl -i -X POST \
  -H "Content-Type:application/json" \
  -d \
  '{
  "url":
  "https://s3-eu-west-1.amazonaws.com/merapar-assessment/arabic-posts.xml"
  }' \
  'http://192.168.99.100:8080/analyze/'
```

## Decisions made

* The assessment said to create the Dockerfile based on the standard Java docker image (https://hub.docker.com/_/java/). 
However this image is deprecated, so I've decided to use the one recommended there (https://hub.docker.com/_/openjdk/).

*

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - Framework to build applications
* [Maven](https://maven.apache.org/) - Software Project Management
* [Docker](https://www.docker.com/) - Platform to build, ship and run distributed applications

## Author

 **Juan Herrador** 