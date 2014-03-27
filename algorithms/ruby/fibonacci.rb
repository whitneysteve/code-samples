module Sequences

  public

  # Get the fibonacci number for a given index.
  #
  def fibonacci(num)
    return 1 if num <= 1
    fibonacci(num - 1) + fibonacci(num - 2)
  end

  private

end

if __FILE__ == $0

  include Sequences

  # Find a number from the fibonacci sequence, in this cases the 1st through to the 8th.

  puts fibonacci(0)
  puts fibonacci(1)
  puts fibonacci(2)
  puts fibonacci(3)
  puts fibonacci(4)
  puts fibonacci(5)
  puts fibonacci(6)
  puts fibonacci(7)

end