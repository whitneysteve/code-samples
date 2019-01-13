module Sort
  MAX_FIXNUM = (2**(0.size * 8 -2) -1)

  public

  # Perform a bubble sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def bubble_sort(arr)
    arr.each_with_index do |_, i|
      arr.each_with_index do |_, j|
        swap(arr, i, j) if arr[i] < arr[j]
      end
    end
  end

  # Perform an insertion sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def insertion_sort(arr)
    arr.each_with_index do |_, i|
      j = 0
      while j < i do
        if arr[i] < arr[j]
          reposition(arr, i, j)
          break
        end
        j += 1
      end
    end
  end

  # Perform a merge sort on an array. Sort is performed in another array
  # and returned. In and order conflict where items are equal the original
  # ordering remains.
  def merge_sort(arr)
    if arr.size < 2 || (arr.size == 2 && arr[0] < arr[1])
      arr
    elsif arr.size == 2
      [arr[1], arr[0]]
    else
      mid = arr.size / 2
      first = arr.slice(0, mid)
      second = arr.slice(mid, arr.size)
      merge(merge_sort(first), merge_sort(second))
    end
  end

  # Perform a quick sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def quick_sort(arr, low=0, high=arr.size - 1)
    if low < high
      partition_index = partition(arr, low, high)
      quick_sort(arr, low, partition_index - 1)
      quick_sort(arr, partition_index + 1, high)
    end
  end

  # Perform a selection sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def selection_sort(arr)
    arr.each_with_index do |_, i|
      least = MAX_FIXNUM
      least_index = i
      for j in i .. (arr.length - 1) do
        next_val = arr[j]
        if next_val < least
          least = next_val
          least_index = j
        end
      end
      if least_index != i
        swap(arr, i, least_index)
      end
    end
  end

  private
  # Merge two arrays in ascending order.
  def merge(first, second)
    i = 0
    j = 0
    result = []
    while (i < first.size && j < second.size) do
      first_val = first[i]
      second_val = second[j]
      if first_val < second_val
        result.push(first_val)
        i += 1
      else
        result.push(second_val)
        j += 1
      end
    end

    result.concat(first.slice(i, first.size))
    result.concat(second.slice(j, second.size))

    result
  end

  # Partition a sub array or arr, denoted by the bounding indexes low and high.
  def partition(arr, low, high)
    pivot = arr[high]
    pivot_index = low

    for j in pivot_index .. (high) do
      if arr[j] < pivot
        swap(arr, j, pivot_index)
        pivot_index += 1
      end
    end

    swap(arr, pivot_index, high)

    pivot_index
  end

  # Reposition an element from index `from` to index `to`.
  def reposition(arr, from, to)
    arr.insert(to, arr.delete_at(from))
  end

  # Swap two elements in an array.
  def swap(arr, i, j)
    tmp = arr[i]
    arr[i] = arr[j]
    arr[j] = tmp
  end
end
