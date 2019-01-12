const expect = require('chai').expect;
const LinkedList = require('../linked_list');

describe('LinkedList', () => {
  it('should add elements', () => {
    const list = new LinkedList();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(toArray(list)).to.be.deep.equal([1, 2, 3]);
  });

  it('should add elements in first position', () => {
    const list = new LinkedList();
    list.add(3);
    list.addFirst(2);
    list.addFirst(1);
    expect(toArray(list)).to.be.deep.equal([1, 2, 3]);
  });

  it('should add elements at index', () => {
    const list = new LinkedList();
    list.add(1);
    list.add(3);
    list.addAtIndex(1, 2);
    expect(toArray(list)).to.be.deep.equal([1, 2, 3]);
  });

  it('should remove elements', () => {
    const list = new LinkedList();
    list.add(1);
    list.add(2);
    list.add(3);
    list.remove(1);
    expect(toArray(list)).to.be.deep.equal([1, 3]);
  });

  it('should sort elements', () => {
    const list = new LinkedList();
    list.add(3);
    list.add(2);
    list.add(1);
    list.sort();
    expect(toArray(list)).to.be.deep.equal([3, 2, 1]);
  });

  it('should reverse elements', () => {
    const list = new LinkedList();
    list.add(3);
    list.add(2);
    list.add(1);
    list.reverse();
    expect(toArray(list)).to.be.deep.equal([1, 2, 3]);
  });

  function toArray(linkedList) {
    const array = [];
    linkedList.traverse((next) => array.push(next));
    return array;
  }
});
