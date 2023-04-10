import {greatestCommonDenominator} from '../src/greatestCommonDenominator';

describe('GreatestCommonDenominator', () => {
  it('should calculate greatest common denominator', () => {
    expect(greatestCommonDenominator(10, 15)).toBe(5);
    expect(greatestCommonDenominator(40, 60)).toBe(20);
  });

  it('should return 1 for no GCD', () => {
    expect(greatestCommonDenominator(13, 45)).toBe(1);
  });
});
