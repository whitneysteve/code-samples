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

  it('should get elements', () => {
    const list = new LinkedList();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(list.get(0)).to.be.equal(1);
    expect(list.get(1)).to.be.equal(2);
    expect(list.get(2)).to.be.equal(3);
  });

  it('should get nodes', () => {
    const list = new LinkedList();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(list.getNode(0).data).to.be.equal(1);
    expect(list.getNode(1).data).to.be.equal(2);
    expect(list.getNode(2).data).to.be.equal(3);
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

  it('should detect cycles', () => {
    let list = newList();
    expect(list.hasCycles()).to.be.false;

    list.head.next = list.head;
    expect(list.hasCycles()).to.be.true;

    list = newList();
    list.head.next.next = list.head;
    expect(list.hasCycles()).to.be.true;
  });

  it('should detect cycles at each node', () => {
    var i = 1;
    while (i < 10) {
      const list = newList();
      list.getNode(i).next = list.head.next;
      expect(list.hasCycles()).to.be.true;
      i ++;
    }
  });

  function newList() {
    const list = new LinkedList();
    var times = 10;

    for(var i = 1; i <= times; i++){
      list.add(i);
    }

    return list;
  }

  function toArray(linkedList) {
    const array = [];
    linkedList.traverse((next) => array.push(next));
    return array;
  }
});
