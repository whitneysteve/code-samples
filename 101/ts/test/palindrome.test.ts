import {
  isPalindrome,
  isPalindromeStr,
  longestPalindrome,
} from '../src/palindrome';

describe('palindrome', () => {
  test('should detect numeric palindrome', () => {
    expect(isPalindrome(12321)).toBeTruthy();
    expect(isPalindrome(12345)).toBeFalsy();
  });

  test('should detect string palindrome', () => {
    expect(isPalindromeStr('12321')).toBeTruthy();
    expect(isPalindromeStr('12345')).toBeFalsy();
  });

  test('should extract longest embedded palindrome', () => {
    expect(longestPalindrome('888123454321999')).toBe('123454321');
  });

  test('should extract first char for no palindrome', () => {
    expect(longestPalindrome('1234567890')).toBe('1');
  });
});
