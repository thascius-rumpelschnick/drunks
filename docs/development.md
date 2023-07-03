# Development

## HowTo

### RESTful API

      ########################################################################################################################
      
      ### Deprecated! Send POST request with body as parameters
      POST http://localhost:8080/api/v1/user/register
      Content-Type: application/x-www-form-urlencoded
      Accept: application/json
      
      username=foo&password=bar
      
      
      ### Send POST request with body as parameters
      POST http://localhost:8080/api/v1/user/register
      Content-Type: application/json
      Accept: application/json
      
      {
      "username": "foo",
      "password": "foobar"
      }
      
      
      ### Basic Zm9vOmJhcg== -> foo:bar
      DELETE http://localhost:8080/api/v1/user/delete
      Authorization: Basic Zm9vOmJhcg==
      Accept: application/json
      
      
      ### GET api/v1/user-data
      GET http://localhost:8080/api/v1/user-data?username=foo
      Authorization: Basic Zm9vOmJhcg==
      Accept: application/json
      
      
      ### PATCH api/v1/user-data
      PATCH http://localhost:8080/api/v1/user-data
      Authorization: Basic Zm9vOmJhcg==
      Accept: application/json
      Content-Type: application/json
      
      {
      "username": "foo",
      "highScore": 1000,
      "level": 0
      }
      
      ########################################################################################################################

### Style Guide

1. [Google Styleguide HowTo](https://medium.com/swlh/configuring-google-style-guide-for-java-for-intellij-c727af4ef248)

### Git

1. Commit Message

    ```text
      [Drunks-Client] Setting everything up
    
       - Add server folder (Don't touch yet)
    ```

2. Pull Requests

   TBA.

## Let's Build A Knowledge Base

- [A Guide to Java 9 Modularity](https://www.baeldung.com/java-9-modularity)sdkman
- [Multi-Module Maven Application with Java Modules](https://www.baeldung.com/maven-multi-module-project-java-jpms)

## Useful Links

### Java

- [SDKMAN!](https://sdkman.io/)

### JavaFX

- [Create a new JavaFX project](https://www.jetbrains.com/help/idea/javafx.html)
- [Getting Started with JavaFX - JavaFX and IntelliJ IDEA](https://openjfx.io/openjfx-docs/#install-javafx)
- [Download Scenebuilder](https://gluonhq.com/products/scene-builder/x)
- [How to Make a 2D Game in Java](https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
- [JavaFX GUI Full Course](https://youtu.be/9XJicRt_FaI)

---

[Back](../README.md)
