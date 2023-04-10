import {binarySearch} from '../src/binarySearch';

describe('BinarySearch', () => {
  const array = [5, 10, 15, 20, 25, 30, 35];

  it('should find item in array', () => {
    expect(binarySearch(array, 10)).toBeTruthy();
    expect(binarySearch(array, 30)).toBeTruthy();
  });

  it('should not find item in array', () => {
    expect(binarySearch(array, 11)).toBeFalsy();
  });
});
