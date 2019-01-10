# Spring GraphQL/REST Sample API

A basic Spring Boot REST/GraphQL API sample.

The idea of this sample is to present the same API in [REST](https://blog.philipphauer.de/restful-api-design-best-practices/) and [GraphQL](https://graphql.org/), comparing their different implementations.

The API itself is a simple store with users, products and orders.

A Swagger UI is also available to test the REST API.

## Requirements

- JDK 10+
- Docker

## Running the project

On the main project folder, execute:

```bash
./gradlew clean build dockerRun bootRun
```

It'll start any necessary Docker containers and also the API.

## Stopping the containers


On the main project folder, execute:

```bash
./gradlew dockerStop
```

## URLs

- Main app: http://localhost:8080/api/
- Swagger Docs: http://localhost:8080/v2/api-docs
- Swagger UI: http://localhost:8080/swagger-ui.html
- SonarQube: http://localhost:9000/

## Using Postman

There is a [Postman](https://www.getpostman.com/) export file with all the possible requests the API can handle. It's recommended to use it to test the API and also populate the database.

## Static code analysis

This sample also contains [SonarQube](https://www.sonarqube.org/) instance running as a Docker container.

### Preparing SonarQube

1. Access SonarQube instance at http://localhost:9000/;
2. Login with the credentials *admin*/*admin*;
3. Go to the Marketplace pane at http://localhost:9000/admin/marketplace;
4. Search for the *Java* plugin and install it.

### Performing a scan

On the main project folder, execute:

```bash
./gradlew sonarqube
```

## References

The article [Leverage HTTP Status Codes to Build a REST Service](https://dzone.com/articles/leverage-http-status-codes-to-build-a-rest-service) was very helpful on creating the base SpringBoot application.

## License

Licensed under the [The MIT License (MIT)](http://opensource.org/licenses/MIT). Please see [LICENSE](LICENSE) for more information.