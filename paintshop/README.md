# Paint Shop

A script to determine the colours and type of paint to prepare based on some pre-specified rules.

## Setup

You will need to install [SBT](https://www.scala-sbt.org/1.0/docs/Setup.html). Once done, you can check is working by running:

```
sbt about # This project was last tested on SBT 1.1.6 and Scala 2.12
```

## Instructions

Execute program using `sbt "run <path_to_input_file>"`. There are some sample input files in the `input` directory.

## Running Linter

Run scalafmt via SBT: `sbt scalafmt`