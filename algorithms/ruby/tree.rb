# Binary search tree implementation.
#
class BinarySearchTree

  public

  # Create a new, empty tree.
  def initialize()
    @root = nil
  end

  # Add a new value to the tree.
  def add(value)
    @root = add_to_node(@root, value)
  end

  # Traverse the tree in a breadth first manner.
  def breadth_first_traversal(node=@root, &block)
    queue = []
    queue.push(node)

    while !queue.empty? do
      current = queue.shift
      block.call(current.value)
      queue.push(current.left) unless current.left.nil?
      queue.push(current.right) unless current.right.nil?
    end
  end

  # Check if the tree contains a specified value.
  def contains?(value, node=@root)
    return false if node.nil?

    if (node.value == value)
      true
    elsif value > node.value
      contains?(value, node.right)
    else
      contains?(value, node.left)
    end
  end

  # Traverse the tre in a depth first manner.
  def depth_first_traversal(node=@root, &block)
    unless node.nil?
      depth_first_traversal(node.left) { |value| block.call(value) }
      block.call(node.value)
      depth_first_traversal(node.right) { |value| block.call(value) }
    end
  end

  # Remove a value from the tree, if it exists.
  def remove(value, node=@root)
    return node if node.nil?

    if (node.value == value)
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
    else
      if value > node.value
        node.right = remove(value, node.right)
      else
        node.left = remove(value, node.left)
      end
    end
  end

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

  attr_accessor :value
  attr_accessor :left
  attr_accessor :right

  public

  # Create a new tree node with a value and, optionally, left and right
  # child nodes.
  def initialize(value, left=nil, right=nil)
    @value = value
    @left = left
    @right = right
  end

  # Check if a node is a leaf node
  def leaf?
    @left.nil? && right.nil?
  end

end

if __FILE__ == $0

  tree = BinarySearchTree.new();

  values = [84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58, 99, 51, 19, 67, 34]

  values.each { |value| tree.add(value) }

  puts tree.contains?(100)
  puts !tree.contains?(-55)

  puts !tree.remove(100).nil?
  puts !tree.contains?(100)

  puts !tree.remove(97).nil?
  puts !tree.contains?(97)

  breadth_first_path = []
  depth_first_path = []
  tree.breadth_first_traversal do |value|
    breadth_first_path.push(value)
  end

  tree.depth_first_traversal do |value|
    depth_first_path.push(value)
  end

  puts breadth_first_path.size ==  depth_first_path.size
  puts breadth_first_path.join(",")
  puts depth_first_path.join(",")
end
