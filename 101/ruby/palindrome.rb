# frozen_string_literal: true

# Module for detecting palindromes.
module Palindrome
  # Find the longest palindrome in a String.
  def find_longest_palindrome(chars) # rubocop:disable Metrics/MethodLength
    return chars if palindrome? chars

    len = chars.length
    substring_len = len - 1

    while substring_len.positive?
      substring_idx = 0

      while substring_idx < len - substring_idx
        substring = chars[substring_idx...substring_idx + substring_len]
        return substring if palindrome? substring

        substring_idx += 1
      end

      substring_len -= 1
    end

    chars[0]
  end

  # Check if a palindrome.
  def palindrome?(chars)
    if chars.is_a? Integer
      chars == reverse(chars)
    else
      len = chars.length

      ((len / 2) + 1).times do |i|
        return false unless chars[i] == chars[len - i - 1]
      end

      true
    end
  end

  private

  # Reverse a number.
  def reverse(num)
    reverse = 0

    until num <= 0
      rem = num % 10
      reverse = (reverse * 10) + rem
      num /= 10
    end

    reverse
  end
end
