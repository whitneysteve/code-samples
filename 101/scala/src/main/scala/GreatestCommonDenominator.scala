import scala.annotation.tailrec

/**
 * Find the greatest common denominator of two numbers, in this case, 13 and 45.
 */
object GreatestCommonDenominator {
  /**
   * Find the GCD of two numbers.
   *
   * @param number1 the first number.
   * @param number2 the second number.
   * @return the GCD of the two specified numbers.
   */
  def apply(number1: Long, number2: Long): Long = {
    @tailrec
    def gcdRecursion(number1: Long, number2: Long): Long = {
      val remainder = number1 % number2
      if( remainder > 0 ){
        gcdRecursion(number2, remainder)
      } else {
        number2
      }
    }

    gcdRecursion(number1, number2)
  }
}
