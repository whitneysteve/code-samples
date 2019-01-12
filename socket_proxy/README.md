# Socket Proxy 

A socket proxy that forwards requests to a destination and sends the response back to the client. Written in Java, built using a Maven archetype.

## Instructions

You will need maven installed to run the project. See [ https://maven.apache.org/install.html | the Apache install instructions ] to accomplish that).

There are 2 Maven projects - the destination socket server that performs a simple operation and a proxy server that proxies the destination.

Compile each project by cd’ing into the directory and running ‘mvm clean compile package’.

Start the proxy by executing ‘java -jar destination-1.0-SNAPSHOT.jsr’ and the destination by executing ‘java -jar proxy-1.0-SNAPSHOT.jar’.

Unit test cases exist for both projects and independent integration tests (AppTest) execute against a running proxy and destination. In order to execute the integration test for the proxy project a destination server must be running.
