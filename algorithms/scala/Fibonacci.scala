
/**
 * Find a number from the fibonacci sequence, in this cases the 1st through to the 7th.
 */
object Fibonacci extends Application {

  println( fib( 0 ) )
  println( fib( 1 ) )
  println( fib( 2 ) )
  println( fib( 3 ) )
  println( fib( 4 ) )
  println( fib( 5 ) )
  println( fib( 6 ) )

  /**
   * Get the fibonacci number for a given index.
   *
   * @param index the index in the fibonacci sequence to generate the fibonacci number for.
   * @return the generated number.
   */
  def fib( index: Long ): Long = {

    if ( index < 1 ) {

      return 0

    }

    if ( index <= 1 ) {

      1

    } else {

      fib( index - 1 ) + fib( index - 2 )

    }

  }

}
