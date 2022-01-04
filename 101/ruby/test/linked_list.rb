# frozen_string_literal: true

require_relative '../linked_list'
require 'minitest/autorun'

class LinkedListTest < Minitest::Test
  def test_it_should_add_elements
    list = LinkedList.new
    list.add(1)
    list.add(2)
    list.add(3)
    assert to_array(list) == [1, 2, 3]
  end

  def test_it_should_add_elements_in_first_position
    list = LinkedList.new
    list.add(3)
    list.add_first(2)
    list.add_first(1)
    assert to_array(list) == [1, 2, 3]
  end

  def test_it_should_remove_elements
    list = LinkedList.new
    list.add(1)
    list.add(2)
    list.add(3)
    list.delete(1)
    assert to_array(list) == [1, 3]
  end

  def test_it_should_sort_elements
    list = LinkedList.new
    list.add(1)
    list.add(2)
    list.add(3)
    list.sort
    assert to_array(list) == [3, 2, 1]
  end

  def test_it_should_reverse_elements
    list = LinkedList.new
    list.add(3)
    list.add(2)
    list.add(1)
    list.reverse
    assert to_array(list) == [1, 2, 3]
  end

  private

  def to_array(list)
    array = []
    list.traverse { |data| array << data }
    array
  end
end
