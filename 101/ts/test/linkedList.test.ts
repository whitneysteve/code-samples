import {LinkedList} from '../src/linkedList';

describe('LinkedList', () => {
  test('should add elements', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(toArray(list)).toStrictEqual([1, 2, 3]);
  });

  test('should add elements in first position', () => {
    const list = new LinkedList<number>();
    list.add(3);
    list.addFirst(2);
    list.addFirst(1);
    expect(toArray(list)).toStrictEqual([1, 2, 3]);
  });

  test('should add elements at index', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(3);
    list.addAtIndex(1, 2);
    expect(toArray(list)).toStrictEqual([1, 2, 3]);
  });

  test.only('should get elements', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(list.get(0)).toEqual(1);
    expect(list.get(1)).toEqual(2);
    expect(list.get(2)).toEqual(3);
  });

  test('should get nodes', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(2);
    list.add(3);
    expect(list.getNode(0)?.data).toEqual(1);
    expect(list.getNode(1)?.data).toBe(2);
    expect(list.getNode(2)?.data).toBe(3);
  });

  test('should remove elements', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.remove(1);
    expect(toArray(list)).toStrictEqual([1, 3]);
  });

  test('should sort elements', () => {
    const list = new LinkedList<number>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.sort();
    expect(toArray(list)).toStrictEqual([3, 2, 1]);
  });

  test('should reverse elements', () => {
    const list = new LinkedList<number>();
    list.add(3);
    list.add(2);
    list.add(1);
    list.reverse();
    expect(toArray(list)).toStrictEqual([1, 2, 3]);
  });

  test('should detect cycles', () => {
    let list = newList();
    expect(list.hasCycles()).toBeFalsy();

    if (list.head) list.head.next = list.head;
    expect(list.hasCycles()).toBeTruthy();

    list = newList();
    if (list.head && list.head.next) list.head.next.next = list.head;
    expect(list.hasCycles()).toBeTruthy();
  });

  test('should detect cycles at each node', () => {
    let i = 1;
    while (i < 10) {
      const list = newList();
      const node = list.getNode(i);
      if (node && list.head) node.next = list.head.next;
      expect(list.hasCycles()).toBeTruthy();
      i++;
    }
  });

  function newList() {
    const list = new LinkedList<number>();
    const times = 10;

    for (let i = 1; i <= times; i++) {
      list.add(i);
    }

    return list;
  }

  function toArray(linkedList: LinkedList<number>) {
    const array: Array<number> = [];
    linkedList.traverse((next: number) => array.push(next));
    return array;
  }
});
