# Socket Proxy 

A socket proxy that forwards requests to a destination and sends the response back to the client. Written in Java, built
using a Maven archetype.

You will need maven installed to run the project. See [the Apache install instructions](https://maven.apache.org/install.html)
to accomplish that.

There are two projects - `destination` and `proxy`. The testing and lint instructions below apply to both projects
individually. Running the the project spans both projects.

There is some code that was copied between each project as they were set up as two maven projects and time, in this
instance, did not allow the creation of a shared repository.

# Running the Tests

From the project directory (`destination` or `proxy`) run `mvn test`.

# Running the Linter

Checkstyle is used to lint the code. To run, execute `mvn checkstyle:check`.

## Instructions

First build both projects by `cd`ing int the directory and running:

```
mvn clean compile package
```

Next start the destination project by executing:

```
java -jar target/destination-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Then, start the destination project by executing:

```
java -jar target/proxy-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Unit test cases exist for both projects and independent integration tests (AppTest) execute against a
running proxy and destination.

In order to execute the integration test for the proxy project a destination server must be running. It
is currently ignored to allow tests to pass. Remove the `@Ignored` annotation from `proxy/src/test/java/socketp/AppTest`
and run the integration test.
