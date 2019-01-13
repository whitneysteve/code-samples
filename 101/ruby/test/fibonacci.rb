# frozen_string_literal: true

# magic_ball.rb
require_relative '../fibonacci.rb'
require 'minitest/autorun'

class FibonacciTest < Minitest::Test
  include Fibonacci

  def test_fibonacci # rubocop:disable Metrics/AbcSize
    assert fibonacci(0) == 1
    assert fibonacci(1) == 1
    assert fibonacci(2) == 2
    assert fibonacci(3) == 3
    assert fibonacci(4) == 5
    assert fibonacci(5) == 8
  end
end
