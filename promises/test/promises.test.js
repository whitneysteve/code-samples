const expect = require('chai').expect;
const MyPromise = require('../promises');

describe('MyPromise', () => {
  it('should execute a promise object', () => {
    let result = 0;
    const functionToExecute = () => {
      result = 1;
    };
    MyPromise(functionToExecute).then();
    expect(result).to.be.equal(1);
  });

  it('should chain results from promises', () => {
    const functionToExecute = () => {
      return 1;
    };
    MyPromise(functionToExecute).then((result) => {
      expect(result).to.be.equal(1);
    }).resolve();
  });
});
