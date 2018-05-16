# Web Food Counter

Web Application is a system of tracking calories.
Client can add consumed food from DB or add new types of food.
System counts a daily need of calories based on clients characteristics 
and send a message to him when he exceeded the norm.

Data stored in DB, with access provided using JDBC API and Connection Pool on MySQL.
Architecture based on MVC and other GoF design patterns.
Application uses POJOs, Servlets and JSPs, HTML5, JSTL and custom tags, filters.
Servlet Container used in project is Apache Tomcat 8.0. Apache Maven used for building.
Logging with Log4j.  Tests are written on JUnit and Mockito.
