const expect = require('chai').expect;
const bind = require('../bind');

describe('bind', () => {
  it('should bind a function to any object', () => {
    const context1 = { result: -1 };
    const context2 = { result: -2 };

    const functionToExecute = function(x) {
      this.result = x;
    };

    bind(functionToExecute, context1)(123);
    bind(functionToExecute, context2)(456);

    expect(context1.result).to.be.equal(123);
    expect(context2.result).to.be.equal(456);
  });
});
