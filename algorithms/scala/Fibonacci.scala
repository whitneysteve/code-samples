import scala.annotation.tailrec

/**
 * Find a number from the fibonacci sequence, in this cases the 1st through to the 7th.
 */
object Fibonacci {

  def main(args: Array[String]) = {
    println(fib(0))
    println(fib(1))
    println(fib(2))
    println(fib(3))
    println(fib(4))
    println(fib(5))
    println(fib(6))
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
