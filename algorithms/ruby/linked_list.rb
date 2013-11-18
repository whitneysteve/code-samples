# Linked list implementation.
#
class LinkedList

  public

  # Add an object to the tail of the list.
  #
  def add( data )

    node = LinkedListNode.new( data )

    @head ||= node

    @tail.nextNode = node unless @tail.nil?
    @tail = node

  end

  # Add to the front of the list.
  #
  def add_first( data )

    node = LinkedListNode.new ( data )

    node.nextNode = @head
    @head = node

  end

  # Delete from the list.
  #
  def delete( index )

    cursor = find_cursor( index )

    unless cursor.nil? || cursor.nextNode.nil?

      if cursor.nextNode == @tail

        cursor.nextNode = nil
        @tail = cursor

      else

        node_to_remove = cursor.nextNode
        cursor.nextNode = node_to_remove.nextNode
        @tail = cursor if cursor.nextNode.nil?

      end

    end

  end

  # Find the first occurrence of a term within the linked list.
  #
  def find( term )

    node = @head

    until node.nil?

      return true if node.data == term
      node = node.nextNode

    end

    false

  end

  # Find all occurrences of a term in the linked list.
  #
  def collect

    arr = []
    node = @head

    until node.nil?

      arr << node.data if yield( node.data )
      node = node.nextNode

    end

    arr

  end

  # Sort the linked list.
  #
  def sort

    list = @head

    until list.nil? do

      pass = list.nextNode

      until pass.nil? do

        if list.data <=> pass.data

          tmp = list.data
          list.data = pass.data
          pass.data = tmp

        end

        pass = pass.nextNode

      end

      list = list.nextNode

    end

  end

  # Reverse the linked list.
  #
  def reverse

    current_node = @head
    prev_node = nil

    until current_node.nil?

      next_node = current_node.nextNode
      current_node.nextNode = prev_node
      prev_node = current_node
      current_node = next_node

    end

    @tail = @head
    @head = prev_node

  end

  # Traverse the linked list.
  #
  def traverse

    cursor = @head

    while cursor do

      yield cursor.data
      cursor = cursor.nextNode

    end

  end

  private

  # Position the cursor pointing to a desired index.
  #
  def find_cursor( index )

    cursor = @head
    index - 1.times { cursor = cursor.nil? ? nil : cursor.nextNode }
    cursor

  end

end

# Linked list node implementation.
#
class LinkedListNode

  attr_accessor :data
  attr_accessor :nextNode

  def initialize( data )

    @data = data

  end

end

if __FILE__ == $0

  #Create a linked list and perform some operations on it.

  list = LinkedList.new

  list.traverse { |data| puts data }

  list.add 1
  list.add 2
  list.add 3
  list.add_first 0
  list.add_first 0
  list.add 4
  list.delete 2
  list.delete 4
  list.add 5

  list.sort

  puts '----------------'

  list.traverse { |data| puts data }

  puts '----------------'

  list.reverse

  list.traverse { |data| puts data }

  puts '----------------'

  puts list.find 3
  puts list.find 89

  puts '----------------'
  puts list.collect { |data| data == 0 }

end
