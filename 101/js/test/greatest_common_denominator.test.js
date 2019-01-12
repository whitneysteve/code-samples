const expect = require('chai').expect;
const greatestCommonDenominator = require('../greatest_common_denominator');

describe('Greatest Common Denominator', () => {
  it('should calculate greatest common denominator', () => {
    expect(greatestCommonDenominator(10, 15)).to.be.equal(5);
    expect(greatestCommonDenominator(40, 60)).to.be.equal(20);
  });

  it('should return 1 for no GCD', () => {
    expect(greatestCommonDenominator(13, 45)).to.be.equal(1);
  });
});