# magic_ball.rb
require_relative '../greatest_common_denominator.rb'
require 'minitest/autorun'

class GreatestCommonDenominatorTest < Minitest::Test
  include GreatestCommonDenominator

  def test_fibonacci
    assert greatest_common_denominator(5, 10) == 5
    assert greatest_common_denominator(5, 20) == 5
    assert greatest_common_denominator(15, 120) == 15
    assert greatest_common_denominator(14, 21) == 7
    assert greatest_common_denominator(45, 13) == 1
  end
end