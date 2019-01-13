const expect = require('chai').expect;
const fibonacci = require('../fibonacci');

describe('Fibonacci', () => {
  it('should calculate fibonacci number for index in sequence', () => {
    expect(fibonacci(0)).to.be.equal(1);
    expect(fibonacci(1)).to.be.equal(1);
    expect(fibonacci(2)).to.be.equal(2);
    expect(fibonacci(3)).to.be.equal(3);
    expect(fibonacci(4)).to.be.equal(5);
    expect(fibonacci(5)).to.be.equal(8);
  });
});
