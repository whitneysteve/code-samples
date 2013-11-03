import collection.mutable

/**
 * Create a linked list and perform some operations on it.
 */
object LinkedListSample extends Application {

  val list: LinkedList[String] = new LinkedList[String]()

  list.traverse( ( data: String ) => println( data ) )

  list.add( "One" )
  list.add( "Two" )
  list.add( "Three" )
  list.addFirst( "Zero" )
  list.addFirst( "Zero" )
  list.add( 3, "Two Point 5" )
  list.add( "Four Point 5" )
  list.delete( 2 )
  list.delete( 4 )
  list.add( "Five" )

  list.sort()

  list.traverse( ( data: String ) => println( data ) )

  list.reverse()

  list.traverse( ( data: String ) => println( data ) )

  println( list.find( "One" ) )
  println( list.findAll( "Zero" ) )

}

/**
 * Linked list implementation.
 */
class LinkedList[T >: Null <: Comparable[T]] {

  var head: LinkedListNode[T] = null
  var tail: LinkedListNode[T] = null

  /**
   * Reverse the linked list.
   */
  def reverse() {

    var currentNode: LinkedListNode[T] = head
    var prevNode: LinkedListNode[T] = null
    var nextNode: LinkedListNode[T] = null

    while( currentNode != null ) {

      nextNode = currentNode.next
      currentNode.next = prevNode
      prevNode = currentNode
      currentNode = nextNode

    }

    tail = head
    head = prevNode


  }

  /**
   * Find the first occurrence of a term within the linked list.
   *
   * @param term the term to find.
   * @return the term if found, null if not.
   */
  def find( term: T ): T = {

    var node = findCursor( 0 )

    while( node != null && node.data != term ) {

      node = node.next

    }

    node match {

      case null => null
      case _ => node.data

    }

  }

  /**
   * Find all occurrences of a term in the linked list.
   * @param term the term to find.
   * @return list containing all occurrences of the term found, or an empty list if not found.
   */
  def findAll( term: T ): mutable.MutableList[T] = {

    val list: mutable.MutableList[T] = mutable.MutableList()

    var node = findCursor( 0 )

    while( node != null ) {

      if ( node.data.compareTo( term ) == 0 ) {

        list += node.data

      }

      node = node.next

    }

    list

  }

  /**
   * Sort the linked list.
   */
  def sort() {

    var list: LinkedListNode[T] = head

    while( list != null ) {

      var pass: LinkedListNode[T] = list.next

      while( pass != null ) {

        if ( list.data.compareTo( pass.data ) > 0 ) {

          val tmp: T = list.data
          list.data = pass.data
          pass.data = tmp

        }

        pass = pass.next

      }

      list = list.next

    }

  }

  /**
   * Add tot he fron tof the list.
   *
   * @param obj the object to add to the list.
   */
  def addFirst( obj: T ) {

    val newNode: LinkedListNode[T] = new LinkedListNode[T]( obj )
    newNode.next = head
    head = newNode

  }

  /**
   * Delete from the list.
   *
   * @param index the index in the list to delete.
   */
  def delete( index: Int ) {

    val cursor: LinkedListNode[T] = findCursor( index )

    if ( cursor.next != null ) {

      if ( cursor.next == tail ) {

        cursor.next = null
        tail = cursor

      } else {

        val elementToRemove: LinkedListNode[T] = cursor.next
        cursor.next = elementToRemove.next

        if ( tail == elementToRemove ) {

          tail = cursor.next

        }

      }

    } else {

      throw new IndexOutOfBoundsException( "" + index )

    }

  }

  /**
   * Add an object to the tail of the list.
   *
   * @param obj the object to add.
   */
  def add( obj: T ) {

    val newNode: LinkedListNode[T] = new LinkedListNode[T]( obj )

    if ( tail == null ) {

      head = newNode
      tail = newNode

    } else {

      tail.next = newNode
      tail = newNode

    }

  }

  /**
   * Add an object to the list at a particular index.
   *
   * @param index the index at which to add the object.
   * @param obj the object to add.
   */
  def add( index: Int, obj: T ) {

    val cursor: LinkedListNode[T] = findCursor( index )

    val nextElement:LinkedListNode[T] = cursor.next
    val newNode: LinkedListNode[T] = new LinkedListNode[T]( obj )
    cursor.next = newNode
    newNode.next = nextElement

    if ( cursor == tail ) {

      tail = newNode

    }

  }

  /**
   * Traverse the linked list.
   *
   * @param traversalFunction function to execute on each element of the list.
   */
  def traverse( traversalFunction: T => Unit ) {

    var cursor: LinkedListNode[T] = head

    while( cursor != null ) {

      traversalFunction( cursor.data )
      cursor = cursor.next

    }

  }

  /**
   * Position the cursor pointing to a desired index.
   *
   * @param index the index to find.
   * @return the cursor, if found.
   */
  def findCursor( index: Int ): LinkedListNode[T] = {

    var cursor: LinkedListNode[T] = head
    var i: Int = 0

    while ( cursor != null && i < ( index - 1 ) ) {

      cursor = cursor.next
      i += 1

    }

    if ( cursor == null ) {

      throw new IndexOutOfBoundsException( "" + i )

    }

    cursor

  }

}

class LinkedListNode[T <: Comparable[T]]( nodeData: T ) {

  var data: T = nodeData
  var next: LinkedListNode[T] = null

}
