import {factorial} from '../src/factorial';

describe('Factorial', () => {
  it('should calculate factorial', () => {
    expect(factorial(5)).toBe(120);
  });
});
