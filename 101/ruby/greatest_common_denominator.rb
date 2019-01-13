# frozen_string_literal: true

# Utility to find the greatest common denominator of two numbers.
module GreatestCommonDenominator
  # Find the GCD of two numbers.
  def greatest_common_denominator(number1, number2)
    num1 = number1
    num2 = number2

    if num2 > num1
      tmp = num1
      num1 = num2
      num2 = tmp
    end

    remainder = num1 % num2

    return num2 if remainder.zero?

    greatest_common_denominator num2, remainder
  end
end
