Sistema de Carga de Nómina (Prueba Técnica)

Sistema gestión y procesamiento de archivos planos de nómina bancaria. 

Java 21 (JDK 21)
Maven 
Spring Boot 3.4.1
H2 Database (En memoria)

#Ejecucion
Levantar el Backend (Puerto 8080)
mvn spring-boot:run 
Consola H2:** http://localhost:8080/h2-console
 * JDBC URL: jdbc:h2:mem:nominadb
 * User: 'sa'
* Password: (vacio)

##FrontEnd 
ng serve -o
http://localhost:4200
