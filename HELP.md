# Read Me First
The following was discovered as part of building this project:

* CRUD operations for Book resource.
* Added global exception handler too.
* Added liquibase for database versioning.
* Using H2 database for persistence layer.
* Unable to add own runtime exceptions. But have added todo for the same. 
  
Below dummy data is added
* INSERT INTO book(name,description,author,type,price,ISBN) VALUES('TestName1','TestDescr1', 'TestAuthor1', 'FICTION', '10', 'TestISBN');
* INSERT INTO book(name,description,author,type,price,ISBN) VALUES('TestName2','TestDescr1', 'TestAuthor2', 'FICTION', '10', 'TestISBN');
* INSERT INTO book(name,description,author,type,price,ISBN) VALUES('TestName2','TestDescr1', 'TestAuthor3', 'COMIC', '10', 'TestISBN');

# Getting Started

### How to build 
For building application:
* cd eShop
* ./gradlew build

### How to run

For running application:
* cd eShop
* ./gradlew bootRun

### How to run in container
For running application within container:
* cd eShop
* ./gradlew build
* docker build -t eshop:latest
* docker run -p8080:8080 eshop:latest  

Swagger document can be access over
* http://localhost:8080/swagger-ui.html