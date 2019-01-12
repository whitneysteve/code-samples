const expect = require('chai').expect;
const findRotationPoint = require('../rotate_array');

describe('rotate array', () => {
  it('finds the rotation point in an array', () => {
    const a = findRotationPoint([ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]);
    const b = findRotationPoint([ 2, 3, 4, 5, 6, 7, 8, 9, 1 ]);
    const c = findRotationPoint([ 5, 6, 7, 8, 9, 1, 2, 3, 4 ]);
    const d = findRotationPoint([ 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 1, 1, 2, 2, 3, 3, 4, 4 ]);
    const e = findRotationPoint([ 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 2, 2, 3, 3, 4, 4 ]);
    expect(a).to.be.equal(1);
    expect(b).to.be.equal(1);
    expect(c).to.be.equal(1);
    expect(d).to.be.equal(1);
    expect(e).to.be.equal(2);
  });
});
