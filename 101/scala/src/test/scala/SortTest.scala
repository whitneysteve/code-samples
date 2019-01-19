import org.scalatest.{FunSuite, Matchers}

class SortTest extends FunSuite with Matchers {
  import Sort._

  private val Values = List(
    84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18,
    51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18,
    42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68,
    29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81,
    36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55,
    48, 91, 100, 84, 58, 99, 51, 19, 67, 34
  )

  private val Sorted = List(
    7, 9, 10, 12, 16, 16, 17, 17, 18, 18, 18, 18, 18, 19, 21, 22, 25, 27, 29,
    30, 33, 33, 33, 33, 33, 34, 35, 35, 36, 36, 38, 38, 40, 41, 42, 42, 44,
    48, 50, 51, 51, 54, 54, 54, 54, 55, 55, 55, 56, 56, 56, 57, 57, 58, 60,
    61, 61, 61, 63, 65, 65, 65, 66, 66, 66, 67, 68, 72, 72, 75, 76, 77, 77,
    77, 81, 82, 83, 84, 84, 84, 85, 85, 90, 91, 91, 91, 92, 93, 93, 94, 94,
    95, 95, 96, 97, 98, 99, 100, 100, 100
  )

  test("should_sort_with_bubble_sort") {
    bubbleSort(Values) should be(Sorted)
  }

  test("should_sort_with_selection_sort") {
    selectionSort(Values) should be(Sorted)
  }

  test("should_sort_with_insertion_sort") {
    insertionSort(Values) should be(Sorted)
  }

  test("should_sort_with_merge_sort") {
    mergeSort(Values) should be(Sorted)
  }

  test("should_sort_with_quick_sort") {
    quickSort(Values) should be(Sorted)
  }
}
