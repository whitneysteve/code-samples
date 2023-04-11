class Node {
  value: number;
  left?: Node;
  right?: Node;

  /**
   * Represents a node in the tree.
   *
   * @param {Number} providedValue the value for the node.
   * @param {Node} providedLeft initial value for the left child node of the tree.
   * @param {Node} providedRight initial value for the right child node of the tree.
   * @constructor
   */
  constructor(value: number, left?: Node, right?: Node) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  isLeaf = () => !this.left && !this.right;
}

export class SortedBinaryTree {
  root?: Node;

  /**
   * Add an element to the binary tree.
   *
   * @param {Number} value value to add to the binary tree.
   */
  add = (value: number): void => {
    this.root = this._add(value, this.root);
  };

  /**
   * Perform a depth-first search for a value. As the tree is sorted there is
   * not much reason for any other type of search.
   */
  contains = (value: number): boolean => this.search(value, this.root);

  /**
   * Traverse the tree in a breadth first manner.
   *
   * @param {Function} callback method to call with each value as it is traversed.
   */
  breadthFirstTraverse = (callback: (value: number) => void): void =>
    this.bf(callback, this.root);

  /**
   * Traverse the tree in a breadth first manner.
   *
   * @param {Function} callback method to call with each value as it is traversed.
   */
  depthFirstTraverse = (callback: (value: number) => void): void =>
    this.df(callback, this.root);

  /**
   * Remove an element from the tree.
   *
   * @param {Number} value the value to remove from the tree.
   */
  remove = (value: number): boolean => !!this.delete(value, this.root);

  /**
   * Recursively find the element where the value should be added.
   *
   * @param {Node} to the current node in the traversal.
   * @param {Number} value value to add to the binary tree.
   */
  private _add = (value: number, to?: Node): Node => {
    if (!to) {
      return new Node(value);
    }

    if (value < to.value) {
      to.left = this._add(value, to.left);
    } else if (value > to.value) {
      to.right = this._add(value, to.right);
    }

    return to;
  };

  /**
   * Recursively traverse a node in a breadth-first manner.
   *
   * @param {Node} node the node to traverse.
   * @param {Function} callback the function to call with each value as
   *                            we traverse the node.
   */
  bf = (callback: (value: number) => void, node?: Node): void => {
    if (node) {
      const queue = new Array<Node>();
      queue.push(node);

      while (queue.length > 0) {
        const current = queue.shift();
        if (current) {
          callback(current.value);

          if (current.left) {
            queue.push(current.left);
          }
          if (current.right) {
            queue.push(current.right);
          }
        }
      }
    }
  };

  /**
   * Traverse the tree recursively and delete the item specified, if found.
   *
   * @param {Node} from the node to start the search from.
   * @param {Number} value the value to remove.
   */
  private delete = (value: number, from?: Node): Node | undefined => {
    if (!from) {
      return undefined;
    }

    if (from.value === value) {
      if (from.isLeaf()) {
        return undefined;
      } else if (!from.right) {
        return from.left;
      } else if (!from.left) {
        return from.right;
      } else {
        const smallestValue = this.findSmallest(from.right);
        from.value = smallestValue;
        from.right = this.delete(smallestValue, from.right);
        return from;
      }
    }

    if (value < from.value) {
      return this.delete(value, from.left);
    } else {
      return this.delete(value, from.right);
    }
  };

  /**
   * Recursively traverse a node in a depth-first manner.
   *
   * @param {Node} node the node to traverse.
   * @param {Function} callback the function to call with each value as
   *                            we traverse the node.
   */
  private df = (callback: (value: number) => void, node?: Node) => {
    if (node) {
      this.df(callback, node.left);
      callback(node.value);
      this.df(callback, node.right);
    }
  };

  /**
   * Perform a depth-first search for a value.
   *
   * @param {Node} node the node to search.
   * @param {Number} value the value to search for.
   */
  private search = (value: number, node?: Node): boolean => {
    if (!node) {
      return false;
    }

    if (node.value === value) {
      return true;
    }

    if (value < node.value) {
      return this.search(value, node.left);
    } else {
      return this.search(value, node.right);
    }
  };

  /**
   * Find the smallest value in a branch.
   *
   * @param {Node} from the node to traverse.
   */
  private findSmallest = (from: Node): number =>
    from.left ? this.findSmallest(from.left) : from.value;
}
