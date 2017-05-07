# Simple Metric Demo

This is a simple demonstration of creating a metric api using a Spring Web/Spring Boot package.

Dependencies:
 - Java 8
 - Maven

Generally, everything can be handled through typical maven goals.

## Running Springs maven plugin.

General guidelines:
https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html

## Running the application

As this app is a spring boot application, it configured instance of Tomcat, and can be run like this:
```mvn spring-boot:run```

This project was developed with Eclipse, so the Eclipse .project file is also included.

More specific directions are available here:  http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html

## Testing

``` mvn test ```

## Generating a deployment

```mvn package```

That package will then be a runnable jar under target/
