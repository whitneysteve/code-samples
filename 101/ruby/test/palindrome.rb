# frozen_string_literal: true

require_relative '../palindrome'
require 'minitest/autorun'

class PalindromeTest < Minitest::Test
  include Palindrome

  def test_should_detect_numeric_palindrome
    assert palindrome?(12_321)
    assert !palindrome?(12_345)
  end

  def test_should_detect_string_palindrome
    assert palindrome?('12321')
    assert !palindrome?('12345')
  end

  def test_should_extract_longest_embedded_palindrome
    assert find_longest_palindrome('888123454321999') == '123454321'
  end

  def test_should_extract_first_char_for_no_palindrome
    assert find_longest_palindrome('1234567890') == '1'
  end
end
