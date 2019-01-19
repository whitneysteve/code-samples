# Coding 101

Various computer science-y algorithms and data structures written in Ruby.

## Setup

If you are using RVM, it should select the correct ruby version and create a gemset for you. If not, it might be a good idea to create a new gemset for yourself, as per your environment.

Run `bundle install` to install the test and linting frameworks. If the `bundle` command is not found you may also need to install bundler (`gem install bundler`) if it's not set up globally on your system.

## Running Tests

Execute the following command to run all tests:

```
ruby -Ilib -e 'ARGV.each { |f| require f }' ./test/*.rb
```

## Running Linter

Execute `rubocop -a` to lint all ruby files.
