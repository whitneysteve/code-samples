/**
 * Check if some numbers are palindromic, check if some Strings are palindromic, then find the longest palindrome
 * in some Strings.
 */
object Palindrome {
  /**
   * Find the longest palindrome in a String.
   *
   * @param str the String to search.
   * @return the longest palindrome in the String.
   */
  def longestPalindrome(str: String): String = {
    if(isPalindromicStr(str)) {
      return str
    }

    val len = str.length
    var substrLen = len

    while(substrLen > 1) {
      var substrIndex = 0
      val maxSubstrIndex = len - substrLen + 1

      while(substrIndex <= maxSubstrIndex) {
        val substr: String = str.substring(substrIndex, substrLen + substrIndex - 1)

        if(isPalindromicStr(substr)) {
          return substr
        }

        substrIndex += 1
      }

      substrLen -= 1
    }

    str.charAt(0).toString
  }

  /**
   * Check if a string is a palindrome.
   *
   * @param str the String to check.
   * @return true if the String is a plaindrome, false if not.
   */
  def isPalindromicStr(str: String): Boolean = {
    val len: Int = str.length
    val mid: Int = len / 2
    var i: Int = 0

    while(i < mid) {
      if(str.charAt(i) != str.charAt(len - (1 + i))) {
        return false
      }

      i += 1
    }

    true
  }

  /**
   * Check if a number is palindromic.
   *
   * @param num the number to check.
   * @return true if the number is a palindrome, false if not.
   */
  def isPalindromic(num: Long): Boolean = {
    reverse(num) == num
  }

  /**
   * Reverse a number.
   *
   * @param num the number to reverse.
   * @return the reversed number.
   */
  def reverse(num: Long): Long = {
    var reverse: Long = 0
    var operation: Long = num

    while(operation > 0) {
      reverse = (reverse * 10) + operation % 10
      operation /= 10
    }

    reverse
  }
}
