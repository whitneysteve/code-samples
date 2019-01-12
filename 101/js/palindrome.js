/**
 * A utility for helping with pesky palindromes.
 * @constructor
 */
function PalindromeUtil() {
  /**
   * Check if a number is a palindrome.
   *
   * @param {number} num the number to check.
   * @returns {boolean} true if the number is a palindrome, false if not.
   */
  this.isPalindrome = function(num) {
    var reverse = this.reverse(num);
    return reverse == num;
  };

  /**
   * Reverse a number.
   *
   * @param {number} num the number to reverse.
   * @returns {number} the reversed number.
   */
  this.reverse = function(num) {
    var reverse = 0;
    var op = num;

    while(op > 0) {
      reverse = ( reverse * 10 ) + op % 10;
      op = Math.floor(op / 10);
    }

    return reverse;
  };

  /**
   * Check if a string is a palindrome.
   *
   * @param {string} str the string to check.
   * @returns {boolean} true if the string is a palindrome, false if not.
   */
  this.isPalindromeStr = function(str) {
    var mid = Math.floor(str.length / 2);
    var i = 0;

    while(i < mid) {
      if(str[ i ] != str[ str.length - i - 1 ])
        return false;

      i++;
    }

    return true;
  };

  /**
   * Find the longest palindrome in a string.
   *
   * @param {string} str the string to check.
   * @returns {string } the logest palindrome in the string.
   */
  this.longestPalindrome = function(str) {
    var len = str.length;
    var substrLen = len;

    while(substrLen > 1) {
      var substrIndex = 0;
      var maxSubstrIndex = len - ( substrLen + 1 );

      while(substrIndex <= maxSubstrIndex) {
        var substr = str.substring(substrIndex, substrIndex + substrLen + 1);

        if(this.isPalindromeStr(substr)) {
          return substr;
        }

        substrIndex++;
      }

      substrLen--;
    }

    return str[ 0 ];
  }
}

module.exports = PalindromeUtil;
