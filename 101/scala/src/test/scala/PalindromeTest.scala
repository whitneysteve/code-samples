import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PalindromeTest extends AnyFunSuite with Matchers {
  import Palindrome._
  
  test("should_detect_numeric_palindrome") {
    isPalindromic(12321) should be(true)
    isPalindromic(12345) should be(false)
  }

  test("should_detect_string_palindrome") {
    isPalindromicStr("12321") should be(true)
    isPalindromicStr("12345") should be(false)
  }

  test("should_extract_longest_embedded_palindrome") {
    longestPalindrome("888123454321999") should be("123454321")
  }

  test("should_extract_first_char_for_no_palindrome") {
    longestPalindrome("1234567890") should be("1")
  }
}
