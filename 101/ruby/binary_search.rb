# frozen_string_literal: true

# Implementation of binary search algorithm.
module BinarySearch
  # Perform a binary search for an Int.
  # rubocop:disable Metrics/AbcSize
  # rubocop:disable Metrics/CyclomaticComplexity
  # rubocop:disable Metrics/MethodLength
  # rubocop:disable Metrics/PerceivedComplexity
  def b_contains?(arr, term)
    return false if arr.nil? || arr.empty?

    len = arr.length
    lo = 0
    hi = len - 1

    while hi > lo
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
  # rubocop:enable Metrics/AbcSize
  # rubocop:enable Metrics/CyclomaticComplexity
  # rubocop:enable Metrics/MethodLength
  # rubocop:enable Metrics/PerceivedComplexity
end
