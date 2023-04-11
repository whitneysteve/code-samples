/**
 * Check if a number is a palindrome.
 *
 * @param {number} num the number to check.
 * @returns {boolean} true if the number is a palindrome, false if not.
 */
export const isPalindrome = (num: number): boolean => num === reverse(num);

/**
 * Check if a string is a palindrome.
 *
 * @param {string} str the string to check.
 * @returns {boolean} true if the string is a palindrome, false if not.
 */
export const isPalindromeStr = (str: string): boolean => {
  const mid = Math.floor(str.length / 2);
  const len = str.length;
  let i = 0;

  while (i < mid) {
    if (str.charAt(i) !== str.charAt(len - i - 1)) {
      return false;
    }
    i += 1;
  }

  return true;
};

/**
 * Find the longest palindrome in a string.
 *
 * @param {string} str the string to check.
 * @returns {string } the longest palindrome in the string.
 */
export const longestPalindrome = (str: string): string => {
  const len = str.length;
  let substrLen = len;

  while (substrLen > 0) {
    let idx = 0;

    while (idx < len - substrLen) {
      const subStr = str.substring(idx, idx + substrLen);
      if (isPalindromeStr(subStr)) {
        return subStr;
      }
      idx += 1;
    }
    substrLen -= 1;
  }

  return str.charAt(0).toString();
};

/**
 * Reverse a number.
 *
 * @param {number} num the number to reverse.
 * @returns {number} the reversed number.
 */
const reverse = (num: number): number => {
  let reverse = 0;
  let op = num;

  while (op > 0) {
    reverse = reverse * 10 + (op % 10);
    op = Math.floor(op / 10);
  }

  return reverse;
};
