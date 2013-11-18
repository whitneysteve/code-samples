module ExtraMath

  public

  # Find the GCD of two numbers.
  #
  def greatest_common_denominator( number1, number2 )

    num1 = number1
    num2 = number2

    if num2 > num1

      tmp = num1
      num1 = num2
      num2 = tmp

    end

    remainder = num1 % num2

    return num2 if remainder == 0
    greatest_common_denominator num2, remainder

  end

  private

end

if __FILE__ == $0

  include ExtraMath

  # Find the greatest common denominator of a few sets of numbers.
  puts greatest_common_denominator( 5, 10 )
  puts greatest_common_denominator( 5, 20 )
  puts greatest_common_denominator( 15, 120 )
  puts greatest_common_denominator( 14, 21 )
  puts greatest_common_denominator( 45, 13 )

end