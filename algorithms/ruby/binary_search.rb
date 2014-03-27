module BinarySearch

  public

  # Perform a binary search for an Int.
  #
  def b_contains?(arr, term)
    return false if arr == nil || arr.length < 1

    len = arr.length
    lo = 0
    hi = len - 1

    while hi > lo do
      mid = (lo + hi) / 2

      return true if arr[mid] == term

      if term > arr[mid]
        lo = mid + 1
      elsif term < arr[mid]
        hi = mid - 1
      end
    end

    false
  end

  private

end

if __FILE__ == $0

  include BinarySearch

  # Perform a binary search

  puts b_contains? [5, 10, 15, 20, 25, 30, 35], 10
  puts b_contains? [5, 10, 15, 20, 25, 30, 35], 11

end