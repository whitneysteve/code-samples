const expect = require('chai').expect;
const factorial = require('../factorial');

describe('Factorial', () => {
  it('should calculate factorial', () => {
    expect(factorial(5)).to.be.equal(120);
  });
});
