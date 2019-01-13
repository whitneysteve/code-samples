module RotatedArray
  public

  # Find the rotation value of a rotated array.
  def find_rotation_value(arr)
    len = arr.length
    last_index = len - 1
    lo = 0
    hi = last_index

    while lo <= hi do
      mid = (lo + hi) / 2

      return arr[mid] if mid == last_index

      term = arr[mid]
      next_term = arr[mid + 1]

      return next_term if term > next_term

      if term > arr[last_index]
        lo = mid + 1
      else
        hi = mid - 1
      end
    end

    arr[0]
  end
end
