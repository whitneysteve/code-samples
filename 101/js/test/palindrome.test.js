const expect = require('chai').expect;
const PalindromeUtil = require('../palindrome');

describe('PalindromeUtil', () => {
  const util = new PalindromeUtil();

  it('should detect numeric palindrome', () => {
    expect(util.isPalindrome(12321)).to.be.true;
    expect(util.isPalindrome(12345)).to.be.false;
  });

  it('should detect string palindrome', () => {
    expect(util.isPalindromeStr("12321")).to.be.true;
    expect(util.isPalindromeStr("12345")).to.be.false;
  });

  it('should extract longest embedded palindrome', () => {
    expect(util.longestPalindrome("888123454321999")).to.be.equal('123454321');
  });

  it('should extract first char for no palindrome', () => {
    expect(util.longestPalindrome("1234567890")).to.be.equal('1');
  });
});