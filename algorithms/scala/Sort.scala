import scala.collection.mutable.ListBuffer

/**
  * Helper for sorting [[List]]s.
  */
object Sort {
  /**
   * Perform a bubble sort on a [[List]].
   *
   * @param list the [[List]] to sort.
   * @return the sorted array.
   */
  def bubbleSort(list: List[Int]): List[Int] = {
    val buffer = list.to[ListBuffer]

    for (i <- buffer.indices) {
      for (j <- buffer.indices) {
        if (buffer(i) < buffer(j)) {
          swap(buffer, i, j)
        }
      }
    }

    buffer.toList
  }

  /**
    * Perform an insertion sort on a [[List]].
    *
    * @param list the [[List]] to sort.
    * @return the sorted array.
    */
  def insertionSort(list: List[Int]): List[Int] = {
    val buffer = list.to[ListBuffer]

    for (i <- buffer.indices) {
      var j = 0

      while(j < i) {
        if (buffer(j) > buffer(i)) {
          reposition(buffer, i, j)
          j = i
        }
        j = j + 1
      }
    }

    buffer.toList
  }

  /**
    * Perform a select sort on a [[List]].
    *
    * @param list the [[List]] to sort.
    * @return the sorted array.
    */
  def selectionSort(list: List[Int]): List[Int] = {
    val buffer = list.to[ListBuffer]

    for (i <- buffer.indices) {
      var least = Int.MaxValue
      var leastIndex = i

      for (j <- i until buffer.length) {
        val nextVal = buffer(j)
        if (nextVal < least) {
          least = nextVal
          leastIndex = j
        }
      }

      if (leastIndex != i) {
        swap(buffer, i, leastIndex)
      }
    }

    buffer.toList
  }

  /**
    * Reposition an element in an array.
    *
    * @param list the [[ListBuffer]] in which to swap the elements.
    * @param from the index of the element to move.
    * @param to the index to move the element to.
    */
  private def reposition(list: ListBuffer[Int], from: Int, to: Int): Unit = {
    list.insert(to, list.remove(from))
  }

  /**
    * Swap two elements in a [[ListBuffer]].
    *
    * @param list the [[ListBuffer]] in which to swap the elements.
    * @param i the index of the first element to swap.
    * @param j the index of the second element to swap.
    */
  private def swap(list: ListBuffer[Int], i: Int, j: Int): Unit = {
    val tmp = list(i)
    list.update(i, list(j))
    list(j) = tmp
  }

  /**
   * The main entry point to the program.
   */
  def main(args: Array[String]): Unit = {
    val values: List[Int] = List(84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58, 99, 51, 19, 67, 34)

    println(bubbleSort(values))
    println(selectionSort(values))
    println(insertionSort(values))
  }
}