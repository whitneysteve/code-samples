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
  def breadthFirstTraverse(f: (Int) => Unit): Unit = {

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
      case None => false
      case Some(node) if value == node.value  => true
      case Some(node) if value > node.value => contains(value, node.right)
      case Some(node) => contains(value, node.left)
    }
  }

  /**
    * Traverse the tree in a depth first manner.
    *
    * @param f the function to invoke at each value during traversal.
    */
  def depthFirstTraverse(f: (Int) => Unit): Unit = {

  }

  /**
    * Remove a value from the tree, if it exists.
    *
    * @param value the value to remove from the tree.
    * @param nodeOpt the root of the branch to search within for the value. Defaults to the root of the tree.
    */
//  def remove(value: Int, nodeOpt: Option[TreeNode] = root): Option[TreeNode] = {
//    val removed: Option[TreeNode] = nodeOpt match {
//      case None => None
//      case Some(node) if value == node.value =>
//        node match {
//          case (_, None, None) => None
//          case (_, None, Some(right: TreeNode)) => Some(right)
//          case (_, Some(left: TreeNode), None) => Some(left)
//          case (_, Some(left: TreeNode), Some(right: TreeNode)) =>
//            val smallest = findSmallest(right)
//            node.value = smallest
//            Some(node)
//        }
//      case Some(node) if value > node.value => remove(value, node.right)
//      case Some(node) => remove(value, node.left)
//    }
//    internalSize = internalSize - removed.size
//    removed
//  }

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
  private def addToNode(nodeOpt: Option[TreeNode], value: Int): Option[TreeNode] = {
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
//  private def findSmallest(node: TreeNode): Int = {
//    node match {
//      case (value, None, _) => value
//      case (_, Some(left: TreeNode), _) => findSmallest(left)
//    }
//  }
}

case class TreeNode(var value: Int, var left: Option[TreeNode] = None, var right: Option[TreeNode] = None)

object BinarySearchTreeMain {
  def main(args: Array[String]): Unit = {
    val tree = new BinarySearchTree()

    val values = Seq(84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58, 99, 51, 19, 67, 34)

    values.foreach(value => tree.add(value))

    assert(tree.contains(100))
    assert(tree.contains(60))
    assert(!tree.contains(-77))
    assert(tree.size == values.distinct.size)

//    tree.remove(100)
//    tree.remove(94)

    assert(!tree.contains(100))
    assert(!tree.contains(94))

    assert(tree.size == values.distinct.size - 2)

    val breadthFirstPath = ListBuffer.empty[Int]
    val depthFirstPath = ListBuffer.empty[Int]

    tree.breadthFirstTraverse { value => breadthFirstPath.append(value) }
    tree.depthFirstTraverse { value => depthFirstPath.append(value) }

    assert(breadthFirstPath.size == depthFirstPath.size)

    println(breadthFirstPath)
    println(depthFirstPath)

  }
}
