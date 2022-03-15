import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class  FibonacciTest extends AnyFunSuite with Matchers {
  test("Computes fibonacci number") {
    Fibonacci(0) should be(0)
    Fibonacci(1) should be(1)
    Fibonacci(2) should be(1)
    Fibonacci(3) should be(2)
    Fibonacci(4) should be(3)
    Fibonacci(5) should be(5)
    Fibonacci(6) should be(8)
  }
}
