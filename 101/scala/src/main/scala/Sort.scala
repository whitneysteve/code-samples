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
    val buffer = list.to(ListBuffer)

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
    val buffer = list.to(ListBuffer)

    for (i <- buffer.indices) {
      var j = 0

      while (j < i) {
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
    * Perform a merge sort on a [[List]].
    *
    * @param list the [[List]] to sort.
    * @return the sorted array.
    */
  def mergeSort(list: List[Int]): List[Int] = {
    def merge(first: List[Int], second: List[Int]): List[Int] = {
      val buffer = ListBuffer[Int]()
      var firstIndex, secondIndex = 0

      while (firstIndex < first.length && secondIndex < second.length) {
        val firstVal = first(firstIndex)
        val secondVal = second(secondIndex)
        if (secondVal < firstVal) {
          buffer.append(secondVal)
          secondIndex += 1
        } else {
          buffer.append(firstVal)
          firstIndex += 1
        }
      }

      buffer.appendAll(first.slice(firstIndex, first.length))
      buffer.appendAll(second.slice(secondIndex, second.length))

      buffer.toList
    }

    if (list.length < 2 || (list.length == 2 && list.head <= list(1))) {
      list
    } else if (list.length == 2) {
      List(list(1), list.head)
    } else {
      val mid = Math.floor(list.length / 2).toInt
      merge(mergeSort(list.slice(0, mid)),
            mergeSort(list.slice(mid, list.length)))
    }
  }

  /**
    * Perform a quick sort on a [[List]].
    *
    * @return the sorted [[List]].
    */
  def quickSort(list: List[Int],
                lowOpt: Option[Int] = None,
                highOpt: Option[Int] = None): List[Int] = {
    def partition(buffer: ListBuffer[Int], low: Int, high: Int): Int = {
      val pivot = buffer(high)
      var pivotIndex = low

      val range = pivotIndex until high
      for (j <- range) {
        if (buffer(j) < pivot) {
          swap(buffer, j, pivotIndex)
          pivotIndex += 1
        }
      }

      swap(buffer, pivotIndex, high)

      pivotIndex
    }

    def quickSortBuffer(buffer: ListBuffer[Int],
                        low: Int,
                        high: Int): ListBuffer[Int] = {
      if (low < high) {
        val partitionIndex = partition(buffer, low, high)
        quickSortBuffer(buffer, low, partitionIndex - 1)
        quickSortBuffer(buffer, partitionIndex + 1, high)
      }

      buffer
    }

    quickSortBuffer(list.to(ListBuffer),
                    lowOpt.getOrElse(0),
                    highOpt.getOrElse(list.length - 1)).toList
  }

  /**
    * Perform a select sort on a [[List]].
    *
    * @param list the [[List]] to sort.
    * @return the sorted array.
    */
  def selectionSort(list: List[Int]): List[Int] = {
    val buffer = list.to(ListBuffer)

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
}
