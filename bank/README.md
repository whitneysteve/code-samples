# Bank

A simple Java banking web application used by a teller, using an in-memory data access layer. Written in Java, Spring MVC, Twitter Bootstrap, jQuery, built and run using a Maven archetype.

### Setup

You will need maven installed to run the project. See [the Apache install instructions](https://maven.apache.org/install.html) to accomplish that.

The project uses JDK 1.8 and appropriate Jetty version for that release. Older Java versions may not be compatible with this Jetty version.

# Running the Application

Once installed, run using ‘mvn jetty:run’ and hit [the local webserver](http://localhost:8080/).

# Run the Linter

Checkstyle is used to lint the code. To run, execute `mvn checkstyle:check`