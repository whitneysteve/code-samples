# Socket Proxy 

A socket proxy that forwards requests to a destination and sends the response back to the client. Written in Java, built
using a Maven archetype.

There are two projects - `destination` and `proxy`. The testing and lint instructions below apply to both projects
individually. Running the the project spans both projects.

# Running the Tests

From the project directory (`destination` or `proxy`) run `mvn test`.

# Running the Linter

Checkstyle is used to lint the code. To run, execute `mvn checkstyle:check`.

## Instructions

You will need maven installed to run the project. See [the Apache install instructions](https://maven.apache.org/install.html) to accomplish that.

There are 2 Maven projects - the destination socket server that performs a simple operation and a proxy server that proxies the destination.

Compile each project by `cd`’ing into the directory and running `mvn clean compile package`.

Start the proxy by executing `java -jar destination-1.0-SNAPSHOT.jar` and the destination by executing `java -jar proxy-1.0-SNAPSHOT.jar`.



Unit test cases exist for both projects and independent integration tests (AppTest) execute against a running proxy and destination.

In order to execute the integration test for the proxy project a destination server must be running.
