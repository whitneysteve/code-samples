import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * Linked list implementation.
  */
class LinkedList[T >: Null <: Comparable[T]] {
  var head: Option[LinkedListNode[T]] = None
  var tail: Option[LinkedListNode[T]] = None

  /**
    * Reverse the linked list.
    */
  def reverse(): Unit = {
    var currentNode: Option[LinkedListNode[T]] = head
    var prevNode: Option[LinkedListNode[T]] = None
    var nextNode: Option[LinkedListNode[T]] = None

    while (currentNode.isDefined) {
      nextNode = currentNode.get.next
      currentNode.get.next = prevNode
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
    * @return the term if found, None if not.
    */
  def find(term: T): Option[T] = {
    var cursor = findCursor(0)

    while (cursor.isDefined && cursor.get.data != term) {
      cursor = cursor.get.next
    }

    cursor match {
      case None       => None
      case Some(node) => Some(node.data)
    }
  }

  /**
    * Find all occurrences of a term in the linked list.
    * @param term the term to find.
    * @return list containing all occurrences of the term found, or an empty list if not found.
    */
  def findAll(term: T): Seq[T] = {
    var list = Seq.empty[T]
    var cursor = findCursor(0)

    while (cursor.isDefined) {
      if (cursor.get.data.compareTo(term) == 0) {
        list = list :+ cursor.get.data
      }
      cursor = cursor.get.next
    }

    list
  }

  /**
    * Sort the linked list.
    */
  def sort(): Unit = {
    var list: Option[LinkedListNode[T]] = head

    while (list.isDefined) {
      var pass: Option[LinkedListNode[T]] = list.get.next

      while (pass.isDefined) {
        if (list.get.data.compareTo(pass.get.data) < 0) {
          val tmp: T = list.get.data
          list.get.data = pass.get.data
          pass.get.data = tmp
        }

        pass = pass.get.next
      }

      list = list.get.next
    }
  }

  /**
    * Add to the front of the list.
    *
    * @param data the object to add to the list.
    */
  def addFirst(data: T): Unit = {
    val newNode: LinkedListNode[T] = new LinkedListNode[T](data)
    newNode.next = head
    head = Some(newNode)
  }

  /**
    * Delete from the list.
    *
    * @param index the index in the list to delete.
    */
  def delete(index: Int): Option[Unit] = findCursor(index - 1) map { cursor =>
    if (cursor.next == tail) {
      cursor.next = None
      tail = Some(cursor)
    } else {
      val elementToRemove = cursor.next

      elementToRemove match {
        case None =>
          cursor.next = None
        case Some(node) =>
          cursor.next = node.next
      }

      if (tail == elementToRemove) {
        tail = cursor.next
      }
    }
  }

  /**
    * Add an object to the tail of the list.
    *
    * @param data the object to add.
    */
  def add(data: T): Unit = {
    val newNode = Some(new LinkedListNode[T](data))

    tail match {
      case None =>
        head = newNode
      case Some(tailNode) =>
        tailNode.next = newNode
    }

    tail = newNode
  }

  /**
    * Add an object to the list at a particular index.
    *
    * @param index the index at which to add the object.
    * @param data the object to add.
    */
  def add(index: Int, data: T): Option[Unit] = findCursor(index - 1) map {
    cursor =>
      val newNode = new LinkedListNode[T](data)

      if (cursor.next == tail) {
        tail = Some(newNode)
      }

      val nextElement = cursor.next
      cursor.next = Some(newNode)
      newNode.next = nextElement
  }

  /**
    * Get the element at a specified index.
    *
    * @param index the index of the element to get.
    * @return the data at the specified index if found, None if not.
    */
  def get(index: Int): Option[T] = findCursor(index).map(_.data)

  /**
    * Get the node at a specified index.
    *
    * @param index the index of the node to get.
    * @return the node at the specified index if found, None if not.
    */
  def getNode(index: Int): Option[LinkedListNode[T]] = findCursor(index)

  /**
    * Traverse the linked list.
    *
    * @param traversalFunction function to execute on each element of the list.
    */
  def traverse(traversalFunction: T => Unit): Unit = {
    @tailrec
    def traverse(current: Option[LinkedListNode[T]])(
        traversalFunction: T => Unit): Unit = current match {
      case None => //nothing
      case Some(node) =>
        traversalFunction(node.data)
        traverse(node.next)(traversalFunction)
    }

    traverse(head)(traversalFunction)
  }

  def hasCycles: Boolean = {
    @tailrec
    def hasCycles(slowOpt: Option[LinkedListNode[T]],
                  fastOpt: Option[LinkedListNode[T]]): Boolean =
      (slowOpt, fastOpt) match {
        case (Some(slow), Some(fast)) if slow == fast =>
          true
        case (None, _) | (_, None) =>
          false
        case (Some(slow), Some(fast)) =>
          hasCycles(slow.next, fast.next.flatMap(_.next))
      }

    hasCycles(head.flatMap(_.next), head.flatMap(_.next).flatMap(_.next))
  }

  def toSeq: Seq[T] = {
    val buffer = ListBuffer.empty[T]
    traverse(value => buffer.append(value))
    buffer.toSeq
  }

  /**
    * Position a cursor pointing to a desired index.
    *
    * @param index the index to find.
    * @return the cursor, if found.
    */
  private[this] def findCursor(index: Int) = {
    @tailrec
    def next(current: Option[LinkedListNode[T]],
             currentIndex: Int): Option[LinkedListNode[T]] = {
      if (current.isEmpty || currentIndex == index) {
        current
      } else {
        next(current.get.next, currentIndex + 1)
      }
    }

    if (index < 1) {
      head
    } else {
      next(head, 0)
    }
  }
}

class LinkedListNode[T <: Comparable[T]](nodeData: T) {
  var data: T = nodeData
  var next: Option[LinkedListNode[T]] = None
}
