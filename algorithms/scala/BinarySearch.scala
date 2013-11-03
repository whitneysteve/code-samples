
/**
 * Perform a binary search, in this case find 10 in [5, 10, 15, 20, 25].
 */
object BinarySearch extends Application {

  println( binarySearch( Array[Int](5, 10, 15, 20, 25), 10 ))

  /**
   * Perform a binary search for an Int.
   *
   * @param array the array to search.
   * @param term the term to search for.
   * @return true if found, false if not.
   */
  def binarySearch( array: Array[Int], term: Int ): Boolean = {

    var low: Int = 0
    var hi: Int = array.length - 1

    while( low <= hi ) {

      val mid: Int = ( low + hi ) / 2

      if ( term > array( mid ) ) {

        low = mid + 1

      } else if ( term < array( mid ) ) {

        hi = mid - 1

      } else {

        return true

      }

    }

    false

  }

}
