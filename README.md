# Social-Media-API
The backend REST API for a social media application

## Database Schema ##
- The _User_ entity keeps the details of users along with their posts and follows
- The _Post_ entity keeps the data about all posts in the system
- The _Comment_ entity keeps the details of comments along with their related posts
- The _PostLike_ entity has the data of all the likes for a particular post
- The _CommentLike_ entity keeps the details of all the likes for a particular comment
- The _Followers_ entity keep the information about all the users who follow a user
- The _Following_ entity keep the information about all the users whom a user follows

## Technology ##
Following tools and libraries were used during the development of API :
- **Java 17** - Java version
- **Spring Boot** - Server side framework
- **Build Tool: Maven**
- **PostgreSQL** - Relational Database
- **Swagger** - API documentation [here](http://localhost:8081/swagger-ui/index.html#/)
- **JWT** - Authentication mechanism for REST APIs
- **Docker** - Containerizing framework


## Overview of the Architecture ##
I have organized the project into layers, such as Controller, Service, and Repository layers, to follow the separation of concerns (MVC) pattern.

**_Entities & DTOs_**
The various entities or model of the application are organized under the **_entities_** package. BaseEntity class, which all other entities extend from, holds common data and behaviour. 
Their DTOs are present under the **_dtos_** package.  
I have used a custom mapper class to map entities to their corresponding DTOs. 
You can find the mapper under the **_utils package_**.

**_DAOs_**
The data access objects (DAOs) are present in the **_repositories_** package. 
They are all extensions of the JpaRepository Interface helping the service layer to persist and retrieve the data from MySQL database.
The service Interfaces are defined in the **_services_** package and their implementations can be found under the **_services/impl_** sub-package. 
Considering the current case study it made sense to create six services - UserService, PostService, CommentService, CommentLikeService, PostLikeService, FollowService, 
to satisfy the different business operations.

**_Authentication & Authorization_**
The security setting are present under the **_configs_** package and the JWT token based authentication mechanism are present in the **_security_** package.

**_Controllers_**
Last, but the most important part is the controller layer. It binds everything together right from the moment a request is intercepted till the response is prepared and sent back. 
The controller layer is present in the **_controllers_** package. 
The corresponding request classes are located under the **_dtos/requests_** package. 

## Response and Exception Handling ##
The classes for handling the entire application's exceptions are present in the **_exceptions_** package.
Last, the API response are all being sent in a uniform manner using the **_Response_** class present in the dto/response sub-package. 
This class allows us to create uniform objects which result in a response as shown below :

```
{
    "message": "Request successful",
    "status": true,
    "payload": {
        "body": "1st comment ",
        "userId": "UUakBFkkmY",
        "postId": 1,
        "username": "John",
        "commentLikeResponses": [
            {
                "id": 1,
                "liked": false,
                "likesCount": 0,
                "postId": null,
                "userId": "UUakBFkkmY"
            }
        ]
    }
}
```

And when there is an exception, the following responses are sent back (result of "/api/v1/users/UUakFkkmY/posts/1/comments" POST request):

```
{
    "timeStamp": "2023-08-06T09:10:40.910+00:00",
    "message": "User is not found",
    "debugMessage": "User not found"
}
```

## Running the server locally ##
To run this Spring Boot app you need to first build it. To build and package the Spring Boot app into a single executable Jar file with Maven, 
You will need to run it from the project folder which contains the pom.xml file.
Using the following commands:

```
maven clean compile
```
This will compile the project and reveal any error or bug it might have.
```
maven install
```
This will package the application into an executable Jar file that resides in a target folder created by maven.
Run the Spring Boot app from the command line using the java -jar followed by the name of the jar file.
Note: You can copy this jar file into a different folder and run it. Just as you could take it and deploy it in a remote server
```
java -jar target/ProphiusLimitedAssessment-0.0.1-SNAPSHOT.jar
```

Alternatively, run the app using Maven plugin:
```
mvn spring-boot:run
```

## Running the server in a Docker Container ##
Make sure docker desktop is up and running in your machine before building the image from the docker file :
run the following command :
```
docker build -t spring-boot-social-api . 
```

Command to run the container :

```
  docker run -p 8081:8081 spring-boot-social-api

```

Please note when you build the container image and if mysql is running locally on your system, you will need to specify the network name and container name in the command 
and configure the application.properties to use the mySQL container name as the host name file to be able to connect to the database from within the container.

```
docker run --network springboot-mysql-net --name springboot-mysql-container -p 8081:8081 spring-boot-social-api
```

## Docker Compose ##
Another alternative to run the application is to use the docker-compose.yml file, which is used to run multiple services in multiple containers.
And to run the application, please execute the following command :

```
docker-compose up -d --build
```

## API Documentation ##
It's as important to document (as is the development) and make your APIs available in a readable manner for frontend teams or external consumers.
The tool for API documentation used in this project is Swagger, you can open the same inside a browser at the following url - [here](http://localhost:8081/swagger-ui/index.html#/)

Some important api endpoints are as follows :

- http://localhost:8081/api/v1/users/signup (HTTP:POST)
- http://localhost:8081/api/v1/users/login (HTTP:POST)
- http://localhost:8081/api/v1/users/userId/posts (HTTP:POST)
- http://localhost:8081/api/v1/users/userId/posts/postId (HTTP:GET)
- http://localhost:8081/api/v1/users/userId/posts/postId/comments (HTTP:POST)
- http://localhost:8081/api/v1/users/userId/posts/postId/post_like (HTTP:POST)


## Development Challenges. ##
During development, I faced challenges related to handling exceptions, validating user input, and managing database transactions. 
I encountered issues with data inconsistency, especially when dealing with complex relationships between entities. 
Additionally, implementing searching, and filtering functionalities presented some challenges.
Documentation and testing was challenging, as writing clear and comprehensive documentation and creating effective testing was time-consuming.








