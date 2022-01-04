# frozen_string_literal: true

require_relative '../fibonacci'
require 'minitest/autorun'

class FibonacciTest < Minitest::Test
  include Fibonacci

  def test_fibonacci
    assert fibonacci(0) == 1
    assert fibonacci(1) == 1
    assert fibonacci(2) == 2
    assert fibonacci(3) == 3
    assert fibonacci(4) == 5
    assert fibonacci(5) == 8
  end
end
