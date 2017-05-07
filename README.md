# Simple Metric Demo

This is a simple demonstration of creating a metric api using a Spring Web/Spring Boot package.
This particular design requires linear insertion of data with no chunking (batch inserting a lot of data points at once)
in exchange for having on-demand metrics that are always computed.
This approach is best suited for streaming data, rather than data from a log file.

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

That package will then be a runnable jar (available using "java -jar [jarname]" with a jarname define by the pattern 
  /target/apiDemo-[VERSION]-SNAPSHOT.jar

Which will include everything needed apart from Java itself, and will run on port 8080 by default.


# The API

- / (Get)  - The root path is an array of the names of all metrics that have been added.

Other than that path, all of the other paths return the current mean, median, min, and max of the metric that they return.

- /{metricname} (Get) - gets the mean, median, min, and max of the metric at metricname.  If this metric does not exist, it will throw an exception.  
- /{metricname} (Post) - creates a new metric named metricname.  It's stored in an unordered set, so worst case will be O(N) to store a new metric, but average case is O(1), so in practice, it's worth it.  If the metric already exists, it is simply returned.
- /{metricname}/add/{value} (Put) - puts a new value into a metric.  The value is stored in a double, so has that resolution.  This operation will take O(log2(N)) time for each metric, and will store the data in a tree (so should take O(N) storage space). 
All of these take O(1) time:
- /{metricname}/mean (Get) - Returns the mean in decimal form.
- /{metricname}/median (Get) - Returns the median in decimal form.
- /{metricname}/min (Get) - Returns the min in decimal form.
- /{metricname}/max (Get) - Returns the max in decimal form.
