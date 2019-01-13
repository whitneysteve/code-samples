# magic_ball.rb
require_relative '../factorial.rb'
require 'minitest/autorun'

class FactorialTest < Minitest::Test
  include Factorial

  def test_factorial
    assert factorial(5) == 120
  end
end