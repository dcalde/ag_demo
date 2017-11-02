### Auto & General Test Implementation

Developer: Daniel Caldeweyher (dcalde@gmail.com)

This Spring Boot application has been developed as per the specification on http://join.autogeneral.com.au/

# Todo

The application is feature complete as far as the API specifications is concerned, but internally
the application would require the following enhancements:

* More logging
* (optional) Docker - A Dockerfile has been created but it is not finished due to time constraints.
* Better secret management. Currently the database password needs to be provided as an environment variable
however it would be preferable to template the application.properties and substitute the password from a secure secret 
store such as credstash, vault, KMS. If Docker is chosen for deployment, it would be recommended to use Docker secrets.
* The Deployment/Installation is currently a list of bash commands, this however could/should be done via Ansible or other configuration tools.
* The flywaydb database migration script current grants ownership of the database schema to the application user (ag_demo role), 
however, this role should only be granted data modification and usage right, not the permission to execute any DDL.
* Use development / production Spring profiles

# Comments about the test specifications

* The validateBrackets endpoint http://join.autogeneral.com.au/swagger-ui/?url=/swagger.json#/tasks/get_tasks_validateBrackets 
states that the input string has a max length of 50, but this isn't actually validated. My implementation does validate it.
* The structure of the error response objects is inconsistent
* If you are using a global error response object ensure that it is named appropriately. The API specifies that the tasks/validateBrackets endpoint
returns an instance of `ToDoItemValidationError` for 400 errors
* I find using different response error object hard to use from a API client point of view as it requires testing the response 
code first to then appropriately deserialise the error response. 
I recommend no response body for non 2xx response and return an any errors via the response headers, see my implementation 
for `join.ag.demo.controller.ToDoController.retrieveItemById` and `join.ag.demo.controller.TaskController.handleConstraintViolationException`
* Overall this test is aimed at Software developers and not TechLead / Architect roles, as there are no requirements regarding
 security, deployment, architecture etc.

# Installation

```bash
git clone https://github.com/dcalde/ag_demo.git

export DB_HOST=localhost

export DB_PASSWORD=`openssl rand -base64 20`
psql -h $DB_HOST -U postgres ag_demo -c "CREATE ROLE ag_demo LOGIN PASSWORD '$DB_PASSWORD'"

chmod +x mvnw
./mvnw flyway:migrate \
    -Dflyway.url=jdbc:postgresql://${DB_HOST}:5432/ag_demo \
    -Dflyway.user=postgres \
    -Dflyway.password=$DB_ADMIN_PASSWORD

./mvnw package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```