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

  def test_it_should_get_elements
    list = LinkedList.new
    list.add(1)
    list.add(2)
    list.add(3)
    assert list.get(0) == 1
    assert list.get(1) == 2
    assert list.get(2) == 3
  end

  def test_it_should_get_nodes
    list = LinkedList.new
    list.add(1)
    list.add(2)
    list.add(3)
    assert list.get_node(0).data == 1
    assert list.get_node(1).data == 2
    assert list.get_node(2).data == 3
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

  def test_detect_cycles
    list = new_list
    refute list.cycles?

    list.head.next_node = list.head
    assert list.cycles?

    list = new_list
    list.head.next_node.next_node = list.head
    assert list.cycles?
  end

  def test_detect_cycles_at_each_node
    i = 1
    while i < 10
      list = new_list
      list.get_node(i).next_node = list.head.next_node
      assert list.cycles?

      i += 1
    end
  end

  private

  def new_list
    list = LinkedList.new
    10.times { |x| list.add(x + 1) }
    list
  end

  def to_array(list)
    array = []
    list.traverse { |data| array << data }
    array
  end
end
