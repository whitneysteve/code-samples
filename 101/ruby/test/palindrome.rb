# frozen_string_literal: true

require_relative '../palindrome'
require 'minitest/autorun'

class PalindromeTest < Minitest::Test
  include Palindrome

  def test_should_detect_numeric_palindrome
    assert palindrome?(12_321)
    refute palindrome?(12_345)
  end

  def test_should_detect_string_palindrome
    assert palindrome?('12321')
    refute palindrome?('12345')
  end

  def test_should_extract_longest_embedded_palindrome
    assert_equal '123454321', find_longest_palindrome('888123454321999')
  end

  def test_should_extract_first_char_for_no_palindrome
    assert '1', find_longest_palindrome('1234567890')
  end
end
