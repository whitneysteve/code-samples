# frozen_string_literal: true

# magic_ball.rb
require_relative '../tree.rb'
require 'minitest/autorun'

class TreeTest < Minitest::Test
  VALUES = [84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72,
            100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66,
            61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25,
            33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93,
            33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55,
            18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84,
            58, 99, 51, 19, 67, 34].freeze

  def test_should_tell_if_the_tree_contains_a_value
    tree = build_tree
    assert tree.contains?(55)
    assert !tree.contains?(-1)
  end

  def test_should_remove_a_value
    tree = build_tree
    tree.remove(55)
    assert !tree.contains?(55)
  end

  def test_should_traverse_the_tree # rubocop:disable Metrics/AbcSize
    tree = build_tree
    dfs_elements = []
    bfs_elements = []

    tree.depth_first_traversal { |value| dfs_elements << value }
    tree.breadth_first_traversal { |value| bfs_elements << value }

    assert dfs_elements.length == 58
    assert bfs_elements.length == 58

    assert dfs_elements.slice(0, 3) == [7, 9, 10]
    assert bfs_elements.slice(0, 3) == [84, 44, 94]
  end

  private

  def build_tree
    tree = BinarySearchTree.new
    VALUES.each { |value| tree.add(value) }
    tree
  end
end
