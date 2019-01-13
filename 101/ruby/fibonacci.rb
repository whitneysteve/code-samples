module Fibonacci
  public

  # Get the fibonacci number for a given index.
  def fibonacci(num)
    return 1 if num <= 1
    fibonacci(num - 1) + fibonacci(num - 2)
  end
end
