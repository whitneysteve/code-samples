# frozen_string_literal: true

# Binary search tree implementation.
class BinarySearchTree
  # Create a new, empty tree.
  def initialize
    @root = nil
  end

  # Add a new value to the tree.
  def add(value)
    @root = add_to_node(@root, value)
  end

  # Traverse the tree in a breadth first manner.
  def breadth_first_traversal(node = @root)
    queue = []
    queue.push(node)

    until queue.empty?
      current = queue.shift
      yield(current.value)
      queue.push(current.left) unless current.left.nil?
      queue.push(current.right) unless current.right.nil?
    end
  end

  # Check if the tree contains a specified value.
  def contains?(value, node = @root)
    return false if node.nil?

    if node.value == value
      true
    elsif value > node.value
      contains?(value, node.right)
    else
      contains?(value, node.left)
    end
  end

  # Traverse the tre in a depth first manner.
  def depth_first_traversal(node = @root, &block)
    return if node.nil?

    depth_first_traversal(node.left, &block)
    yield(node.value)
    depth_first_traversal(node.right, &block)
  end

  # Remove a value from the tree, if it exists.
  # rubocop:disable Metrics/AbcSize
  # rubocop:disable Metrics/MethodLength
  # rubocop:disable Metrics/PerceivedComplexity
  def remove(value, node = @root)
    return node if node.nil?

    if node.value == value
      if node.leaf?
        nil
      elsif node.left.nil?
        node.right
      elsif node.right.nil?
        node.left
      else
        smallest = find_smallest(node.right)
        node.value = smallest
        node.right = remove(smallest, node.right)
        node
      end
    elsif value > node.value
      node.right = remove(value, node.right)
    else
      node.left = remove(value, node.left)
    end
  end
  # rubocop:enable Metrics/AbcSize
  # rubocop:enable Metrics/MethodLength
  # rubocop:enable Metrics/PerceivedComplexity

  private

  # Add a value to a specified node. Recurses the tree to find the correct
  # position in the tree.
  def add_to_node(node, value)
    return TreeNode.new(value) if node.nil?
    return node if value == node.value

    if value < node.value
      node.left = add_to_node(node.left, value)
    else
      node.right = add_to_node(node.right, value)
    end

    node
  end

  # Find the smallest item amongst a branch.
  def find_smallest(node)
    if node.left.nil?
      node.value
    else
      find_smallest(node.right)
    end
  end
end

# Represents a node in a binary tree.
class TreeNode
  attr_accessor :value, :left, :right

  # Create a new tree node with a value and, optionally, left and right
  # child nodes.
  def initialize(value, left = nil, right = nil)
    @value = value
    @left = left
    @right = right
  end

  # Check if a node is a leaf node
  def leaf?
    @left.nil? && right.nil?
  end
end
