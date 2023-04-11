import {SortedBinaryTree} from '../src/tree';

describe('SortedBinaryTree', () => {
  const values = [
    84, 94, 44, 55, 91, 56, 54, 33, 77, 56, 66, 95, 12, 72, 100, 57, 65, 18, 51,
    35, 16, 60, 18, 50, 56, 9, 93, 30, 54, 66, 61, 33, 61, 97, 65, 18, 42, 38,
    85, 41, 90, 22, 42, 72, 10, 25, 33, 54, 63, 76, 7, 38, 18, 68, 29, 66, 35,
    83, 82, 98, 61, 93, 33, 84, 91, 36, 33, 40, 95, 17, 16, 81, 36, 100, 92, 94,
    85, 55, 18, 75, 17, 96, 77, 65, 57, 21, 54, 27, 77, 55, 48, 91, 100, 84, 58,
    99, 51, 19, 67, 34,
  ];

  test('should tell if the tree contains a value', () => {
    const tree = buildTree();
    expect(tree.contains(55)).toBeTruthy();
    expect(tree.contains(-1)).toBeFalsy();
  });

  test('should remove a value', () => {
    const tree = buildTree();
    tree.remove(55);
    expect(tree.contains(55)).toBeFalsy();
  });

  test('should traverse the tree', () => {
    const tree = buildTree();
    const dfsElements = new Array<number>();
    const bfsElements = new Array<number>();

    tree.depthFirstTraverse(value => dfsElements.push(value));
    tree.breadthFirstTraverse(value => bfsElements.push(value));

    const expectedSize = 58;
    expect(dfsElements.length).toEqual(expectedSize);
    expect(bfsElements.length).toEqual(expectedSize);

    expect(dfsElements.slice(0, 3)).toEqual([7, 9, 10]);
    expect(bfsElements.slice(0, 3)).toEqual([84, 44, 94]);
  });

  function buildTree() {
    const tree = new SortedBinaryTree();
    values.forEach(value => tree.add(value));
    return tree;
  }
});
