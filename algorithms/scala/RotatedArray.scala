
/**
 * Find the rotation point in some rotated arrays.
 */
object RotatedArray {

  def main(args: Array[String]) = {
    println(findRotation(1, 2, 3, 4, 5, 6, 7, 8, 9))
    println(findRotation(9, 1, 2, 3, 4, 5, 6, 7, 8))
    println(findRotation(5, 6, 7, 8, 9, 1, 2, 3, 4))
    println(findRotation(5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 1, 1, 2, 2, 3, 3, 4, 4))
    println(findRotation(5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 2, 2, 3, 3, 4, 4))
  }

  /**
   * Find the rotation point of a rotated seq.
   *
   * @param arr the rotated seq.
   * @return the rotation point in the seq.
   */
  def findRotation(arr: Int*): Int = {
    val lastIndex = arr.length - 1
    var lo = 0
    var hi = arr.length - 1

    while(lo <= hi) {
      val mid: Int = (lo + hi) / 2

      if(mid == lastIndex)
        return arr(mid)

      val term = arr(mid)
      val nextTerm = arr(mid + 1)

      if(term > nextTerm)
        return nextTerm

      if(term > arr(lastIndex))
        lo = mid + 1
      else
        hi = mid - 1
    }

    arr(0)
  }
}
