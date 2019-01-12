module Sequences
  public

  # Get the fibonacci number for a given index.
  def fibonacci(num)
    return 1 if num <= 1
    fibonacci(num - 1) + fibonacci(num - 2)
  end
end

if __FILE__ == $0
  include Sequences

  # Find a number from the fibonacci sequence, in this cases the 1st through to the 8th.
  (0..7).each {|idx| puts fibonacci idx }
end