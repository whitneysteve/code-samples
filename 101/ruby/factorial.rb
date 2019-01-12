module Sequences
  public

  # Recursively compute the factorial of a number.
  def factorial(num)
    return 1 if num <= 1
    num * factorial(num - 1)
  end
end

if __FILE__ == $0
  include Sequences

  # Find the factorial (n!) of a number (n), in this case 5.
  (1..5).each {|number| puts factorial number}
end