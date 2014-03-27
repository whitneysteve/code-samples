# Samples

A collection of code samples written for various reasons. These are usually written in a very short space of time (from 5 minutes to 1 or 2 hours) for a specific purpose.

## Algorithms

Various algorithms written in Scala, Javascript and Ruby.

### Instructions

Javascript algorithms have an associated index.html file that can be opened in a browser and output results to a console. Mostly developed using Firefox & Firebug.

## Bank

A simple Java banking web application used by a teller, using an in-memory data access layer. Written in Java, Spring MVC, Twitter Bootstrap, jQuery, built and run using a Maven archetype. 

### Instructions

Run using ‘mvm jetty:run’ and hit http://localhost:8080/

## Paint Shop

A script to determine the colours and type of paint to prepare based on some pre-specified rules. Written in Scala.

### Instructions

Execute program using ‘scala paintshop [path to input file]’

## Postfix Calculator

A script to perform postfix calculations (addition, subtraction, division and multiplication) on the command line arguments. Written in Scala.

### Instructions

Execute program using ‘scala PostFixCalculator [expression]’. For example: ’1 7 3 - 2 * + 3’

## Socket Proxy

A socket proxy that forwards requests to a destination and sends the response back to the client. Written in Java, built using a Maven archetype.

### Instructions

There are 2 Maven projects - the destination socket server that performs a simple operation and a proxy server that proxies the destination.

Compile each project by cd’ing into the directory and running ‘mvm clean compile package’.

Start the proxy by executing ‘java -jar destination-1.0-SNAPSHOT.jsr’ and the destination by executing ‘java -jar proxy-1.0-SNAPSHOT.jar’.

Unit test cases exist for both projects and independent integration tests (AppTest) execute against a running proxy and destination. In order to execute the integration test for the proxy project a destination server must be running.

