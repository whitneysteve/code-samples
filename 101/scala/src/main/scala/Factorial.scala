import scala.annotation.tailrec

/**
  * Find the factorial (n!) of a number (n), in this case 5.
  */
object Factorial {

  /**
    * Recursively compute the factorial of a number.
    *
    * @param number the number to compute for.
    * @return the computed factorial of specified number.
    */
  def apply(number: Long): Long = {
    @tailrec
    def factorialRecursion(number: Long, accumulator: Long = 1): Long = {
      if (number > 1) factorialRecursion(number - 1, number * accumulator)
      else accumulator
    }

    factorialRecursion(number)
  }
}
