
/**
 * Find the factorial (n!) of a number (n), in this case 5.
 */
object Factorial extends Application {

  println( factorial( 5 ) )

  /**
   * Recursively compute the factorial of a number.
   *
   * @param number the number to compute for.
   * @return the computed factorial of specified number.
   */
  def factorial( number: Long ): Long = {

    number match {

      case 1 => 1
      case _ => number * factorial( number - 1 )

    }

  }

}
