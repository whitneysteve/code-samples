import scala.collection.mutable.ListBuffer

/**
  * Binary search tree implementation
  */
class BinarySearchTree {
  private var root: Option[TreeNode] = None
  private var internalSize: Int = 0

  /**
    * Add a value to the tree.
    *
    * @param value the value to add to the tree.
    */
  def add(value: Int): Unit = {
    root = addToNode(root, value)
  }

  /**
    * Traverse the tree in a breadth first manner.
    *
    * @param f the function to invoke at each value during traversal.
    */
  def breadthFirstTraverse(f: Int => Unit): Unit = {
    val queue = ListBuffer.empty[TreeNode]
    root.foreach(node => queue.append(node))

    while (queue.nonEmpty) {
      val current = queue.remove(0)
      f(current.value)
      current.left.foreach(left => queue.append(left))
      current.right.foreach(right => queue.append(right))
    }
  }

  /**
    * Check if a value is contained within the tree.
    *
    * @param value the value to search for.
    * @param nodeOpt the root of the branch to search within for the value. Defaults to the root of the tree.
    * @return {{true}} id the value is found or {{false}} if not.
    */
  def contains(value: Int, nodeOpt: Option[TreeNode] = root): Boolean = {
    nodeOpt match {
      case None                              => false
      case Some(node) if value == node.value => true
      case Some(node) if value > node.value  => contains(value, node.right)
      case Some(node)                        => contains(value, node.left)
    }
  }

  /**
    * Traverse the tree in a depth first manner.
    *
    * @param nodeOpt optional node from which to start depth first traversal, defaults to the root [[TreeNode]].
    * @param f the function to invoke at each value during traversal.
    */
  def depthFirstTraverse(nodeOpt: Option[TreeNode] = root)(
      f: Int => Unit): Unit = {
    if (nodeOpt.isEmpty) return

    nodeOpt.foreach { node =>
      depthFirstTraverse(node.left)(f)
      f(node.value)
      depthFirstTraverse(node.right)(f)
    }
  }

  /**
    * Remove a value from the tree, if it exists.
    *
    * @param value the value to remove from the tree.
    * @param nodeOpt the root of the branch to search within for the value. Defaults to the root of the tree.
    */
  def remove(value: Int, nodeOpt: Option[TreeNode] = root): Option[TreeNode] = {
    val removed: Option[TreeNode] = nodeOpt match {
      case None => None
      case Some(node) if value == node.value =>
        (node.value, node.left, node.right) match {
          case (_: Int, None, None)             => None
          case (_, None, Some(right: TreeNode)) => Some(right)
          case (_, Some(left: TreeNode), None)  => Some(left)
          case (_, Some(_: TreeNode), Some(right: TreeNode)) =>
            val smallest = findSmallest(right)
            node.value = smallest
            Some(node)
        }
      case Some(node) if value > node.value => remove(value, node.right)
      case Some(node)                       => remove(value, node.left)
    }
    internalSize = internalSize - removed.size
    removed
  }

  /**
    * Get the number of elements in the tree.
    *
    * @return the number of elements in the tree.
    */
  def size: Int = {
    internalSize
  }

  /**
    * Add a value to a specified tree branch.
    *
    * @param nodeOpt the root of the branch to add to/
    * @param value the value to add.
    * @return the [[TreeNode]] created for the value if it did not already exist, or the existing [[TreeNode]] if it
    *         did.
    */
  private def addToNode(nodeOpt: Option[TreeNode],
                        value: Int): Option[TreeNode] = {
    nodeOpt match {
      case None =>
        internalSize = size + 1
        Some(TreeNode(value))
      case Some(node) if value == node.value =>
        nodeOpt
      case Some(node) if value > node.value =>
        node.right = addToNode(node.right, value)
        nodeOpt
      case Some(node) =>
        node.left = addToNode(node.left, value)
        nodeOpt
    }
  }

  /**
    * Find the smallest value in a tree branch.
    *
    * @param node the root of the tree branch to search.
    * @return the smallest value found in the branch.
    */
  private def findSmallest(node: TreeNode): Int = {
    (node.value, node.left, node.right) match {
      case (value, None, _)             => value
      case (_, Some(left: TreeNode), _) => findSmallest(left)
    }
  }
}

case class TreeNode(var value: Int,
                    var left: Option[TreeNode] = None,
                    var right: Option[TreeNode] = None)
