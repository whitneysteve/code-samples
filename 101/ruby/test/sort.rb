# magic_ball.rb
require_relative '../sort.rb'
require 'minitest/autorun'

class SortTest < Minitest::Test
  include Sort

  VALUES = [84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56,  9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58, 99, 51, 19, 67, 34]

  SORTED = [7, 9, 10, 12, 16, 16, 17, 17, 18, 18, 18, 18, 18, 19, 21, 22, 25, 27, 29, 30, 33, 33, 33, 33, 33, 34, 35, 35, 36, 36, 38, 38, 40, 41, 42, 42, 44, 48, 50, 51, 51, 54, 54, 54, 54, 55, 55, 55, 56, 56, 56, 57, 57, 58, 60, 61, 61, 61, 63, 65, 65, 65, 66, 66, 66, 67, 68, 72, 72, 75, 76, 77, 77, 77, 81, 82, 83, 84, 84, 84, 85, 85, 90, 91, 91, 91, 92, 93, 93, 94, 94, 95, 95, 96, 97, 98, 99, 100, 100, 100]

  def test_should_sort_with_bubble_sort
    bubble_sort = VALUES.dup
    bubble_sort(bubble_sort)
    assert bubble_sort == SORTED
  end

  def test_should_sort_with_selection_sort
    selection_sort = VALUES.dup
    selection_sort(selection_sort)
    assert selection_sort == SORTED
  end

  def test_should_sort_with_insertion_sort
    insertion_sort = VALUES.dup
    insertion_sort(insertion_sort)
    assert insertion_sort == SORTED
  end

  def test_should_sort_with_merge_sort
    assert merge_sort(VALUES) == SORTED
  end

  def test_should_sort_with_quick_sort
    quick_sort = VALUES.dup
    quick_sort(quick_sort)
    assert quick_sort == SORTED
  end
end