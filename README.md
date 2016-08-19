## A simple example of springboot api and jwt authentication

[![CircleCI](https://circleci.com/gh/pinbar/springboot-jwt.svg?style=svg)](https://circleci.com/gh/pinbar/springboot-jwt) 

### tech stack
* **springboot** - an opinionated spring starter
* **java-jwt** - a jwt implementation for java
* **jacoco** - for instrumentation and test coverage
* **maven** - a build and dependency management tool

### getting started
* ensure you have java and maven installed: `mvn --version`
* clone repo and `mvn install`
* in the project directory, run `mvn spring-boot:run`
* launch the browser and point to the baseurl `localhost:8080` (port can be changed in `application.properties`)

### running tests
* tests are in `src/test/java` directory, which includes both `unit` and `integration` tests
* to run all the tests, run `mvn test` in the project directory
* to run a single test, run `mvn test -Dtest=<test file name>`
* **test coverage:** coverage reports are in `target/site/index.html` directory (can be changed in `pom.xml` in the jacoco plugin configuration)

### api and authentication scenarios
* access the unsecure api `GET /metacortex`
* all `/api/*` calls are secured with JWT authentication
* try accessing the secure api `GET /api/megacity` to see an auth error
* obtain a JWT token here `POST /authenticate.html`
    * enter program name:password (neo:keanu or morpheus:laurence)
    * the response contains a JWT token for that program
* use the token when calling any secure api (`/api/*`):
    * set the `Authorization` request header and add the jwt token, like so:
    * `Authorization: Bearer \<token\>`
* `GET /api/megacity` can be accessed with any token but `GET /api/levrai` can only be accessed with neo's token
* some information in the payload is encrypted for privacy

### encryption
* a part of the jwt claim is encrypted before signing and then decrypted after verification for privacy

### chaos
* a filter that introduces `500 - Internal Server Error` errors randomly for any `/oracle/*` api call
* this _chaos_ can be seen:
    * by repeatedly accessing `GET /oracle/choice` or 
    * by running this test: `/src/main/test/ChaosControllerTest.java`
