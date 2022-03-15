import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ListBuffer

class BinarySearchTreeTest extends AnyFunSuite with Matchers {
  private val Values = Seq(84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72,
    100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66,
    61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25,
    33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93,
    33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55,
    18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84,
    58, 99, 51, 19, 67, 34
  )

  test("should tell if the tree contains a value") {
    val tree = buildTree()
    tree.contains(55) should be(true)
    tree.contains(-1) should be(false)
  }

  test("should remove a value") {
    val tree = buildTree()
    tree.remove(55)
    tree.contains(55) should be(false)
  }

  test("should traverse the tree") {
    val tree = buildTree()
    val bfsElements = ListBuffer.empty[Int]
    val dfsElements = ListBuffer.empty[Int]

    tree.breadthFirstTraverse { value => bfsElements.append(value) }
    tree.depthFirstTraverse() { value => dfsElements.append(value) }

    bfsElements.size should be(58)
    dfsElements.size should be(58)

    bfsElements.take(3) should be(Seq(84, 44, 94))
    dfsElements.take(3) should be(Seq(7, 9, 10))
  }

  private def buildTree(): BinarySearchTree = {
    val tree = new BinarySearchTree()
    Values.foreach(tree.add)
    tree
  }
}
