import org.scalatest.{FunSuite, Matchers}

class BinarySearchTest extends FunSuite with Matchers {
  private val TestArray = Seq(5, 10, 15, 20, 25)

  test("Binary search finds values") {
    BinarySearch(TestArray, 10) should be(true)
  }

  test("Binary search does not find value when not present") {
    BinarySearch(TestArray, 11) should be(false)
  }
}
