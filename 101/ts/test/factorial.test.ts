import {factorial} from '../src/factorial';

describe('Factorial', () => {
  test('should calculate factorial', () => {
    expect(factorial(5)).toBe(120);
  });
});
