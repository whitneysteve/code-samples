
/**
 * Check if some numbers are palindromic, check if some Strings are palindromic, then find the longest palindrome
 * in some Strings.
 */
object Palindrome extends Application {

  println( isPalindromic( 12321 ) )
  println( isPalindromic( 12345 ) )
  println( isPalindromic( 1234567890987654321l ) )

  println( isPalindromicStr( "12321" ) )
  println( isPalindromicStr( "12345" ) )
  println( isPalindromicStr( "1234567890987654321" ) )

  println( longestPalindrome( "12321" ) )
  println( longestPalindrome( "999123521888" ) )

  /**
   * Find the longest palindrome in a String.
   *
   * @param str the String to search.
   * @return the longest palindrome in the String.
   */
  def longestPalindrome( str: String ): String = {

    if ( isPalindromicStr( str ) ) {

      return str

    }

    val len: Int = str.length
    var substrLen: Int = len

    while( substrLen > 1 ) {

      var substrIndex: Int = 0
      val maxSubstrIndex: Int = len - substrLen + 1

      while( substrIndex <= maxSubstrIndex ) {

        val substr: String = str.substring( substrIndex, substrLen + substrIndex - 1 )

        if ( isPalindromicStr( substr ) ) {

          return substr

        }

        substrIndex += 1

      }

      substrLen -= 1

    }

    "" + str.charAt( 0 )

  }

  /**
   * Check if a string is a palindrome.
   *
   * @param str the String to check.
   * @return true if the String is a plaindrom, false if not.
   */
  def isPalindromicStr( str: String ): Boolean = {

    val len: Int = str.length
    val mid: Int = len / 2
    var i: Int = 0

    while ( i < mid ) {

      if ( str.charAt( i ) != str.charAt( len - ( 1 + i ) ) ) {

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
  def isPalindromic( num: Long ): Boolean = {

    val rev: Long = reverse( num )
    rev == num

  }

  /**
   * Reverse a number.
   *
   * @param num the number to reverse.
   * @return the reversed number.
   */
  def reverse( num: Long ): Long = {

    var reverse: Long = 0
    var operation: Long = num

    while( operation > 0 ) {

      reverse = ( reverse * 10 ) + operation % 10
      operation /= 10

    }

    reverse

  }

}
