class Node<T> {
  data: T;
  next?: Node<T>;

  /**
   * A node in a linked list.
   *
   * @param obj the object the node in the list represents.
   * @constructor
   */
  constructor(data: T, next?: Node<T>) {
    this.data = data;
    this.next = next;
  }
}

/**
 * A linked list.
 */
export class LinkedList<T> {
  head?: Node<T>;
  tail?: Node<T>;

  /**
   * Add an object to the tail of the list.
   *
   * @param {T} value the object to add.
   */
  add = (value: T): void => {
    const node = new Node(value);

    if (!this.head) {
      this.head = node;
      this.tail = node;
    } else if (this.tail) {
      this.tail.next = node;
      this.tail = node;
    }
  };

  /**
   * Add an object to the list at a specified index.
   *
   * @param {number} idx the index to insert the new node.
   * @param {T} value the object to add.
   */
  addAtIndex = (idx: number, value: T): void => {
    const insertAt = this.getNode(idx - 1);

    if (insertAt) {
      const newNode = new Node(value, insertAt.next);
      insertAt.next = newNode;

      if (insertAt === this.tail) {
        this.tail = newNode;
      }
    }
  };

  /**
   * Add an object to the front of the list.
   *
   * @param {T} value the object to add.
   */
  addFirst = (value: T): void => {
    this.head = new Node(value, this.head);
  };

  /**
   * Get an item from the linked list.
   *
   * @param index the index to get.
   */
  get = (idx: number): T | undefined => this.getNode(idx)?.data;

  /**
   * Find the cursor pointing at a node in the linked list.
   *
   * @param {number} idx the index of the node to find the cursor for.
   * @returns {*} the cursor, if the index is valid.
   */
  getNode = (idx: number): Node<T> | undefined => {
    let i = 0;
    let curr = this.head;

    while (i <= idx && curr) {
      if (i === idx) return curr;
      i += 1;
      curr = curr.next;
    }

    return undefined;
  };

  /**
   * Check if the linked list has cycles, or infinite traversal loops.
   *
   * @returns {Boolean}
   */
  hasCycles = (): boolean => {
    let slow = this.head;
    let fast = this.head;
    let collision = false;

    while (slow && fast && fast?.next) {
      slow = slow?.next;
      fast = fast?.next?.next;
      if (slow === fast) {
        collision = true;
        break;
      }
    }

    return collision;
  };

  /**
   * Remove an item from the linked list.
   *
   * @param idx the index to remove.
   */
  remove = (idx: number): boolean => {
    const prev = this.getNode(idx - 1);

    if (prev) {
      prev.next = prev.next?.next;
      return true;
    }

    return false;
  };

  /**
   * Reverse the linked list.
   */
  reverse = (): void => {
    let currentNode = this.head;
    let previousNode, nextNode;

    while (currentNode) {
      nextNode = currentNode.next;
      currentNode.next = previousNode;
      previousNode = currentNode;
      currentNode = nextNode;
    }

    this.tail = this.head;
    this.head = previousNode;
  };

  /**
   * Sort the linked list.
   */
  sort = (): void => {
    let list = this.head;

    while (list) {
      let pass = list.next;

      while (pass) {
        if (list.data < pass.data) {
          const tmp = list.data;
          list.data = pass.data;
          pass.data = tmp;
        }

        pass = pass.next;
      }

      list = list.next;
    }
  };

  /**
   * Traverse the linked list.
   *
   * @param {function} callback called with each item in the linked list.
   */
  traverse = (callback: (data: T) => void): void => {
    let curr = this.head;
    while (curr) {
      callback(curr.data);
      curr = curr.next;
    }
  };
}
