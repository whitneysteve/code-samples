import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class LinkedListTest extends AnyFunSuite with Matchers {
  test("it should add elements") {
    val list = new LinkedList[String]()
    list.add("1")
    list.add("2")
    list.add("3")
    list.toSeq should be(Seq("1", "2", "3"))
  }

  test("it should add elements in first position") {
    val list = new LinkedList[String]()
    list.add("3")
    list.addFirst("2")
    list.addFirst("1")
    list.toSeq should be(Seq("1", "2", "3"))
  }

  test("it should get elements") {
    val list = new LinkedList[String]()
    list.add("1")
    list.add("2")
    list.add("3")
    list.get(0) should be(Some("1"))
    list.get(1) should be(Some("2"))
    list.get(2) should be(Some("3"))
    list.get(3) should be(None)
  }

  test("it should get nodes") {
    val list = new LinkedList[String]()
    list.add("1")
    list.add("2")
    list.add("3")
    list.getNode(0).map(_.data) should be(Some("1"))
    list.getNode(1).map(_.data) should be(Some("2"))
    list.getNode(2).map(_.data) should be(Some("3"))
    list.getNode(3).map(_.data) should be(None)
  }

  test("it should remove elements") {
    val list = new LinkedList[String]()
    list.add("1")
    list.add("2")
    list.add("3")
    list.delete(1)
    list.toSeq should be(Seq("1", "3"))
  }

  test("it should sort elements") {
    val list = new LinkedList[String]()
    list.add("1")
    list.add("2")
    list.add("3")
    list.sort()
    list.toSeq should be(Seq("3", "2", "1"))
  }

  test("it should reverse elements") {
    val list = new LinkedList[String]()
    list.add("3")
    list.add("2")
    list.add("1")
    list.reverse()
    list.toSeq should be(Seq("1", "2", "3"))
  }

  test("it should detect cycles") {
    var list = newList
    list.hasCycles should be(false)

    list.head.get.next = list.head
    list.hasCycles should be(true)

    list = newList
    list.head.get.next.get.next = list.head
    list.hasCycles should be(true)
  }

  test("it should cycles at each node") {
    (1 to 9).foreach { i =>
      val list = newList
      list.getNode(i).get.next = list.head.get.next
      list.hasCycles should be(true)
    }
  }

  private def newList = {
    val list = new LinkedList[String]()
    (1 to 10).map(_.toString).foreach(list.add)
    list
  }
}
