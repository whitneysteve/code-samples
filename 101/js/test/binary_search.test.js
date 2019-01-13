const expect = require('chai').expect;
const binarySearch = require('../binary_search');

describe('BinarySearch', () => {
  const array = [5, 10, 15, 20, 25, 30, 35];

  it('should find item in array', () => {
    expect(binarySearch(array, 10)).to.be.true;
  });

  it('should not find item in array', () => {
    expect(binarySearch(array, 11)).to.be.false;
  });
});
