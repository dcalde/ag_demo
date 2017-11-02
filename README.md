### Auto & General Test Implementation

Developer: Daniel Caldeweyher (dcalde@gmail.com)

This Spring Boot application has been developed as per the specification on http://join.autogeneral.com.au/

# Installation

```bash
git clone https://github.com/dcalde/ag_demo.git

export DB_HOST=localhost

DB_PASSWORD=`openssl rand -base64 20`
psql -h $DB_HOST -U postgres ag_demo -c "CREATE ROLE ag_demo LOGIN PASSWORD '$DB_PASSWORD'"

./mvnw flyway:migrate \
    -Dflyway.url=jdbc:postgresql://${DB_HOST}:5432/ag_demo \
    -Dflyway.user=postgres \
    -Dflyway.password=$DB_ADMIN_PASSWORD

./mvnw package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```