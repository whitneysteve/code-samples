import org.scalatest.{FunSuite, Matchers}

class GreatestCommonDenominatorTest extends FunSuite with Matchers {
  test("Computes greatest common denominator") {
    GreatestCommonDenominator(5, 10) should be(5)
    GreatestCommonDenominator(5, 20) should be(5)
    GreatestCommonDenominator(15, 120) should be(15)
    GreatestCommonDenominator(14, 21) should be(7)
    GreatestCommonDenominator(45, 13) should be(1)
  }
}
