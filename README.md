# Pokemon Service
Application to generate Pokemon details based on a given name by invoking pokeApi and fun translation Api

### How to install and run
```
docker build -t springio/gs-spring-boot-docker .
docker run -p 8080:8080 springio/gs-spring-boot-docker
```

### How to access the endpoints
 -  Application is OpenApi enabled. Please visit http://localhost:8080/v3/api-docs
 -  If you want to skip above, access the endpoints directly from below
    - http://localhost:8080/pokemon/mewtwo
    - http://localhost:8080/pokemon/translated/ditto
    
### Design considerations
 - Spring boot with reactor for reactive endpoints
 - Reactive Webclient to access PokeApi and Fun translation API
 - Invoke PokeApi's Graphql endpoint to retrieve only what the service needs
 - Simplified GraphQL query configuration in file
 - Use of configuration properties file

### Test coverage
 - Created unit tests for PokemonService

### Non functional requirements 
 - Reactive endpoints helps in increased scalability
 - Micrometer to expose application and interface metrics (for observability)
   - http://localhost:8080/actuator/metrics/
   - http://localhost:8080/actuator/metrics/http.server.requests (or use any other metric listed above) 
 - Slf4j for structured logs
 - Dockerized

### Areas of improvements for Prod
 - Use of a circuit breaker for external endpoints to gracefully degrade
 - Add more unit test cases, integration tests and contract tests
 - Logback to JSONify the logs 
 - Use API key for fun-translation API
 - Reconsider the Graphql integration with regard to General Availability
 - Integrate with a configuration server for audit and dynamic change in properties
 - More metrics 
    - Count of outgoing requests for each endpoint
    - Timers 
 
 


