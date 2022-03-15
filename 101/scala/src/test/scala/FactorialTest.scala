import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FactorialTest extends AnyFunSuite with Matchers {
  test("Computes factorial") {
    Factorial(5) should be(120)
  }
}
