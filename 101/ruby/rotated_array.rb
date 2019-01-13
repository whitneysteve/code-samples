# frozen_string_literal: true

# Module to determine the rotation point of an array.
module RotatedArray
  # Find the rotation value of a rotated array.
  # rubocop:disable Metrics/AbcSize
  # rubocop:disable Metrics/MethodLength
  def find_rotation_value(arr)
    len = arr.length
    last_index = len - 1
    lo = 0
    hi = last_index

    while lo <= hi
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
  # rubocop:enable Metrics/AbcSize
  # rubocop:enable Metrics/MethodLength
end
