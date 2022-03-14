# frozen_string_literal: true

# Linked list implementation.
class LinkedList
  attr_reader :head

  # Add an object to the tail of the list.
  def add(data)
    node = LinkedListNode.new(data)

    @head ||= node

    @tail.next_node = node unless @tail.nil?
    @tail = node
  end

  # Add to the front of the list.
  def add_first(data)
    node = LinkedListNode.new data

    node.next_node = @head
    @head = node
  end

  # Get the element at a specific index, returning nil if there is no item at that index.
  def get(index)
    get_node(index)&.data
  end

  # Get the node holding the element at a specific index, returning nil if there is no item at that index.
  def get_node(index)
    find_cursor(index)
  end

  # Delete from the list.
  def delete(index)
    cursor = find_cursor(index - 1)
    return if cursor.nil? || cursor.next_node.nil?

    cursor.next_node = cursor.next_node&.next_node
    @tail = cursor.next_node if cursor == @tail
  end

  # Find the first occurrence of a term within the linked list.
  def find(term)
    node = @head

    until node.nil?
      return true if node.data == term

      node = node.next_node
    end

    false
  end

  # Find all occurrences of a term in the linked list.
  def collect
    arr = []
    node = @head

    until node.nil?
      arr << node.data if yield(node.data)
      node = node.next_node
    end

    arr
  end

  # Sort the linked list.
  def sort # rubocop:disable Metrics/MethodLength
    list = @head

    until list.nil?
      pass = list.next_node

      until pass.nil?
        if list.data <=> pass.data
          tmp = list.data
          list.data = pass.data
          pass.data = tmp
        end

        pass = pass.next_node
      end

      list = list.next_node
    end
  end

  # Reverse the linked list.
  def reverse
    current_node = @head
    prev_node = nil

    until current_node.nil?
      next_node = current_node.next_node
      current_node.next_node = prev_node
      prev_node = current_node
      current_node = next_node
    end

    @tail = @head
    @head = prev_node
  end

  # Traverse the linked list.
  def traverse
    cursor = @head

    while cursor
      yield cursor.data
      cursor = cursor.next_node
    end
  end

  # Check if there are cycles, or infinite loops, in this linked list.
  def cycles?
    check_for_cycles(@head, @head)
  end

  private

  # Position the cursor pointing to a desired index.
  def find_cursor(index)
    cursor = @head
    index.times { cursor = cursor&.next_node }
    cursor
  end

  # Check if there are cycles, or infinite loops, in this linked list.
  def check_for_cycles(slow, fast)
    collision = false
    while !slow.nil? && !fast.nil? && !fast.next_node.nil?
      slow = slow.next_node
      fast = fast.next_node.next_node
      if slow == fast
        collision = true
        break
      end
    end
    collision
  end
end

# Linked list node implementation.
class LinkedListNode
  attr_accessor :data, :next_node

  def initialize(data)
    @data = data
  end
end
