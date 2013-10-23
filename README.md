# Samples

A collection of code samples for various reasons. These are usually written in a very short space of time for a specific purpose.

## Bank

A simple Java banking web application used by a teller, using an in-memory data access layer. 

Featuring: Java, Spring MVC, Twitter Bootstrap, jQuery.

Built and run using a Maven archetype. 

### Instructions

Run using ‘mvm jetty:run’ and hit http://localhost:8080/

## Socket Proxy

A socket proxy that forwards requests to a destination and sends the response back to the client. 

Featuring: Java.

Built using a Maven archetype.

### Instructions

There are 2 Maven projects - the destination socket server that performs a simple operation and a proxy server that proxies the destination.

Compile each project by cd’ing into the directory and running ‘mvm clean compile package’.

Start the proxy by executing ‘java -jar destination-1.0-SNAPSHOT.jsr’ and the destination by executing ‘java -jar proxy-1.0-SNAPSHOT.jar’.

Unit test cases exist for both projects and independent integration tests (AppTest) execute against a running proxy and destination. In order to execute the integration test for the proxy project a destination server must be running.

