
/**
 * Find the greatest common denominator of two numbers, in this case, 13 and 45.
 */
object GreatestCommonDenominator extends Application {

  println( gcd( 13, 45 ) )

  /**
   * Find the GCD of two numbers.
   *
   * @param number1 the first number.
   * @param number2 the second number.
   * @return the GCD of the two specified numbers.
   */
  def gcd( number1: Long, number2: Long ): Long = {

    val remainder: Long = number1 % number2

    if ( remainder == 0 ) {

      number2

    } else {

      gcd( number2, remainder )

    }

  }

}
