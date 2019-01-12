import scala.annotation.tailrec

/**
 * Find a number from the fibonacci sequence, in this cases the 1st through to the 7th.
 */
object Fibonacci {

  def main(args: Array[String]) = {
    0 to 6 foreach fib
  }

  /**
   * Get the fibonacci number for a given index.
   *
   * @param index the index in the fibonacci sequence to generate the fibonacci number for.
   * @return the generated number.
   */
  def fib(index: Long) = {
    @tailrec
    def fibRecursion(current: Long, prev: Long, prev2: Long): Long =
      if(current > 0) fibRecursion(current - 1, prev2, prev + prev2) else prev

    fibRecursion(index, 0, 1)
  }
}
