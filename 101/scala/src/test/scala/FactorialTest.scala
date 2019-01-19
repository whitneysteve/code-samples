import org.scalatest.{FunSuite, Matchers}

class FactorialTest extends FunSuite with Matchers {
  test("Computes factorial") {
    Factorial(5) should be(120)
  }
}
