/**
 * Sorted binary tree.
 * @constructor
 */
function SortedBinaryTree() {
  /**
   * Represents a node in the tree.
   *
   * @param {Number} providedValue the value for the node.
   * @param {Node} providedLeft initial value for the left child node of the tree.
   * @param {Node} providedRight initial value for the right child node of the tree.
   */
  function Node(providedValue, providedLeft, providedRight) {
    this.value = providedValue;
    this.left = providedLeft;
    this.right = providedRight;

    this.isLeaf = function() {
      return !this.left && !this.right;
    }
  }

  this.root = undefined;

  /**
   * Add an element to the binary tree.
   *
   * @param {Number} value value to add to the binary tree.
   */
  this.add = function(value) {
    this.root = this._add(this.root, value);
  };

  /**
   * Traverse the tree in a breadth first manner.
   *
   * @param {Function} callback method to call with each value as it is traversed.
   */
  this.breadthFirstTraverse = function(callback) {
    this._bfTraverse(this.root, callback);
  };

  /**
   * Perform a depth-first search for a value. As the tree is sorted there is
   * not much reason for any other type of search.
   *
   */
  this.contains = function(value) {
    return this._dfs(this.root, value);
  };

  /**
   * Traverse the tree in a breadth first manner.
   *
   * @param {Function} callback method to call with each value as it is traversed.
   */
  this.depthFirstTraverse = function(callback) {
    this._dfTraverse(this.root, callback);
  };

  /**
   * Remove an element from the tree.
   *
   * @param {Number} value the value to remove from the tree.
   */
  this.remove = function(value) {
    return this._delete(this.root, value);
  };

  /**
   * Recursively find the element where the value should be added.
   *
   * @param {Node} node the current node in the traversal.
   * @param {Number} value value to add to the binary tree.
   */
  this._add = function(node, value) {
    if (!node) {
      return new Node(value);
    }

    if (value < node.value) {
      node.left = this._add(node.left, value);
    } else if (value > node.value) {
      node.right = this._add(node.right, value);
    }

    return node;
  };

  /**
   * Recursively traverse a node in a breadth-first manner.
   *
   * @param {Node} node the node to traverse.
   * @param {Function} callback the function to call with each value as
   *                            we traverse the node.
   */
  this._bfTraverse = function(node, callback) {
    if (node) {
      const queue = [];
      queue.push(node);

      while(queue.length > 0) {
        const current = queue.shift();
        callback(current.value);

        if (current.left) {
          queue.push(current.left);
        }
        if (current.right) {
          queue.push(current.right);
        }
      }
    }
  };

  /**
   * Traverse the tree recursively and delete the item specified, if found.
   *
   * @param {Node} node the node to start the search from.
   * @param {Number} value the value to remove.
   */
  this._delete = function(node, value) {
    if (!node) {
      return undefined;
    }

    if (node.value === value) {
      if (node.isLeaf()) {
        return undefined;
      } else if (!node.right) {
        return node.left;
      } else if (!node.left) {
        return node.right;
      } else {
        const smallest = findSmallest(node.right);
        node.value = smallest;
        node.right = this._delete(node.right, smallest);
        return node;
      }
    }

    if (node.value > value) {
      return this._delete(node.left, value);
    } else {
      return this._delete(node.right, value);
    }

    /**
     * Find the smallest value in a branch.
     *
     * @param {Node} node the node to traverse.
     */
    function findSmallest(node) {
      return node.left ? findSmallest(node.left) : node.value;
    }
  };

  /**
   * Perform a breadth-first search for a value.
   *
   * @param {Node} node the node to search.
   * @param {Number} value the value to search for.
   */
  this._dfs = function(node, value) {
    if (!node) {
      return false;
    }

    if (node.value === value) {
      return true;
    }

    if (node.value > value) {
      return this._dfs(node.left, value);
    } else {
      return this._dfs(node.right, value);
    }
  };

  /**
   * Recursively traverse a node in a depth-first manner.
   *
   * @param {Node} node the node to traverse.
   * @param {Function} callback the function to call with each value as
   *                            we traverse the node.
   */
  this._dfTraverse = function(node, callback) {
    if (node) {
      this._dfTraverse(node.left, callback);
      callback(node.value);
      this._dfTraverse(node.right, callback);
    }
  };
}

module.exports = SortedBinaryTree;