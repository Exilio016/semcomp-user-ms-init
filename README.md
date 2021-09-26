# user-ms

User microservice for USP presentation

## Requirements
- [Java 8+](https://www.oracle.com/java/technologies/downloads/#java8)
- Java IDE
	- [Intellij](https://www.jetbrains.com/idea/download/#section=windows)
	- [Eclipse](https://www.eclipse.org/downloads/)
- [Gradle](https://gradle.org/install/)
- [Docker](https://www.docker.com/get-started)	
	- [PostgreSQL on docker](https://linuxiac.com/postgresql-docker/)  
	Command example: 
	```
	docker run --rm --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data postgres
	```
- REST Client
	- [Insomnia](https://insomnia.rest/download)
	- [Postman](https://www.postman.com/downloads/)
	- [SoapUI](https://www.soapui.org/downloads/soapui/)
- DB Client for PostgreSQL
	- [Dbeaver](https://dbeaver.io/download/)
	- [HeidiSQL](https://www.heidisql.com/download.php)
	- [PGAdmin](https://www.pgadmin.org/download/)
- [Git](https://git-scm.com/downloads)