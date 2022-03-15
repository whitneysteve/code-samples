import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class BinarySearchTest extends AnyFunSuite with Matchers {
  private val TestArray = Seq(5, 10, 15, 20, 25)

  test("Binary search finds values") {
    BinarySearch(TestArray, 10) should be(true)
  }

  test("Binary search does not find value when not present") {
    BinarySearch(TestArray, 11) should be(false)
  }
}
