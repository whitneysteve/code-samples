/**
  * Perform a binary search.
  */
object BinarySearch {

  /**
    * Perform a binary search for an Int.
    *
    * @param seq the seq to search.
    * @param term the term to search for.
    * @return true if found, false if not.
    */
  def apply(seq: Seq[Int], term: Int): Boolean = {
    var low = 0
    var hi = seq.size - 1

    while (low <= hi) {
      val mid = (low + hi) / 2

      if (term > seq(mid)) {
        low = mid + 1
      } else if (term < seq(mid)) {
        hi = mid - 1
      } else {
        return true
      }
    }
    false
  }
}
