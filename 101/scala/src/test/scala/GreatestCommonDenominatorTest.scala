import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class GreatestCommonDenominatorTest extends AnyFunSuite with Matchers {
  test("Computes greatest common denominator") {
    GreatestCommonDenominator(5, 10) should be(5)
    GreatestCommonDenominator(5, 20) should be(5)
    GreatestCommonDenominator(15, 120) should be(15)
    GreatestCommonDenominator(14, 21) should be(7)
    GreatestCommonDenominator(45, 13) should be(1)
  }
}
