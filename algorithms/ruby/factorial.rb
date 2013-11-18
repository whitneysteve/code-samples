module Sequences

  public

  # Recursively compute the factorial of a number.
  #
  def factorial( num )

    return 1 if num <= 1
    num * factorial( num - 1 )

  end

  private

end

if __FILE__ == $0

  include Sequences

  # Find the factorial (n!) of a number (n), in this case 5.

  puts factorial 1
  puts factorial 2
  puts factorial 3
  puts factorial 4
  puts factorial 5

end