module Sort

  MAX_FIXNUM = (2**(0.size * 8 -2) -1)

  public

  # Perform a bubble sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def bubble_sort(arr)
    arr.each_with_index { |_,i|
      arr.each_with_index { |_, j|
        swap(arr, i, j) if arr[i] < arr[j]
      }
    }
  end

  # Perform a merge sort on an array. Sort is performed on a new array
  # and leaves the passed array unmodified.
  def merge_sort(arr)
    if arr.size < 2
      arr
    elsif arr.size == 2
      bubble_sort(arr)
    else
      mid = (arr.size / 2.0).floor
      first = merge_sort(arr.slice(0, mid))
      second = merge_sort(arr.slice(mid, arr.size))
      merge(first, second)
    end
  end

  # Perform an insertion sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def insertion_sort(arr)
    arr.each_with_index { |_,i|
      j = 0

      while j < i do
        if arr[j] > arr[i]
          reposition(arr, i, j)
          break
        end
        j += 1;
      end
    }
  end

  # Perform a selection sort on an array. Sort is performed in place and
  # assumes conflict ordering is not important.
  def selection_sort(arr)
    arr.each_with_index { |_,i|
      j = i

      least = MAX_FIXNUM
      least_index = i
      for j in i .. (arr.length - 1) do
        next_val = arr[j]
        if next_val < least
          least = next_val
          least_index = j
        end
      end

      if i != least_index
        swap(arr, i, least_index)
      end

    }
  end

  private
  # Merge two arrays in ascending order.
  def merge(first, second)
    result = []
    i = 0
    j = 0

    while (i < first.size && j < second.size) do
      if first[i] > second[j]
        result.push(second[j])
        j += 1
      else
        result.push(first[i])
        i += 1
      end
    end

    result.concat(first.slice(i, first.size))
    result.concat(second.slice(j, second.size))
    result
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

if __FILE__ == $0

  include Sort

  values = [84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51, 35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38, 85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35, 83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94, 85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58, 99, 51, 19, 67, 34];

  bubble_sort_vals = Marshal.load(Marshal.dump(values))
  bubble_sort bubble_sort_vals
  puts bubble_sort_vals.join(",")

  selection_sort_vals = Marshal.load(Marshal.dump(values))
  selection_sort selection_sort_vals
  puts selection_sort_vals.join(",")

  insertion_sort_vals = Marshal.load(Marshal.dump(values))
  insertion_sort insertion_sort_vals
  puts insertion_sort_vals.join(",")

  puts(merge_sort(values).join(","))

end