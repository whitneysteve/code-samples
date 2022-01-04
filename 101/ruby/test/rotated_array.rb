# frozen_string_literal: true

require_relative '../rotated_array'
require 'minitest/autorun'

class RotatedArrayTest < Minitest::Test
  include RotatedArray

  def test_should_find_rotation_point
    assert find_rotation_value([1, 2, 3, 4, 5, 6, 7, 8, 9]) == 1
    assert find_rotation_value([9, 1, 2, 3, 4, 5, 6, 7, 8]) == 1
    assert find_rotation_value([5, 6, 7, 8, 9, 1, 2, 3, 4]) == 1
    assert find_rotation_value(
      [5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 1, 1, 2, 2, 3, 3, 4, 4]
    ) == 1
    assert find_rotation_value(
      [5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 2, 2, 3, 3, 4, 4]
    ) == 2
  end
end
