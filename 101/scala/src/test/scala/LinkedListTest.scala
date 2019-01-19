import org.scalatest.{FunSuite, Matchers}

class LinkedListTest extends FunSuite with Matchers {
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
}
