# frozen_string_literal: true

require_relative '../factorial'
require 'minitest/autorun'

class FactorialTest < Minitest::Test
  include Factorial

  def test_factorial
    assert factorial(5) == 120
  end
end
