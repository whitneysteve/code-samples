# frozen_string_literal: true

# magic_ball.rb
require_relative '../binary_search.rb'
require 'minitest/autorun'

class BinarySearchTest < Minitest::Test
  include BinarySearch

  def test_binary_search
    assert b_contains? [5, 10, 15, 20, 25, 30, 35], 10
    assert b_contains? [5, 10, 15, 20, 25, 30, 35], 30
    assert !b_contains?([5, 10, 15, 20, 25, 30, 35], 11)
  end
end
