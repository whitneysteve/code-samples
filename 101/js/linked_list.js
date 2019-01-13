/**
 * A linked list.
 * @constructor
 */
function LinkedList() {
}

/**
 * A node in a linked list.
 *
 * @param obj the object the node in the list represents.
 * @constructor
 */
function LinkedListNode(obj) {
  this.data = obj;
}

/**
 * Add an object to the tail of the list.
 *
 * @param {*} obj the object to add.
 */
LinkedList.prototype.add = function(obj) {
  var newNode = new LinkedListNode(obj);

  if(!this.head) {
    this.head = newNode;
    this.tail = newNode;
  } else {
    this.tail.next = newNode;
    this.tail = newNode;
  }
};

/**
 * Add an object to the list at a specified index.
 *
 * @param {number} index the index to insert the new node.
 * @param {*} obj the object to add.
 */
LinkedList.prototype.addAtIndex = function(index, obj) {
  var cursor = this.findCursor(index - 1);

  var newNode = new LinkedListNode(obj);
  var displacedElement = cursor.next;
  cursor.next = newNode;
  newNode.next = displacedElement;

  if(cursor == this.tail) {
    this.tail = newNode;
  }
};

/**
 * Add an object to the front of the list.
 *
 * @param {*} obj the object to add.
 */
LinkedList.prototype.addFirst = function(obj) {
  var newNode = new LinkedListNode(obj);
  newNode.next = this.head;
  this.head = newNode;

  if(!this.tail) {
    this.tail = newNode;
  }
};

/**
 * Remove an item from the linked list.
 *
 * @param index the index to remove.
 */
LinkedList.prototype.remove = function(index) {
  var cursor = this.findCursor(index - 1);
  if(!cursor.next) {
    throw 'IndexOutOfBounds: ' + index;
  } else {
    var removedElement = cursor.next;
    cursor.next = cursor.next.next;

    if(removedElement === this.tail) {
      this.tail = cursor;
    }
  }
};

/**
 * Traverse the linked list.
 *
 * @param {function} traversalFunction called with each item in the linked list.
 */
LinkedList.prototype.traverse = function(traversalFunction) {
  var cursor = this.head;

  while(cursor) {
    traversalFunction(cursor.data);
    cursor = cursor.next;
  }
};

/**
 * Find the cursor pointing at a node in the linked list.
 *
 * @param {number} index the index of the node to find the cursor for.
 * @returns {*} the cursor, if the index is valid.
 */
LinkedList.prototype.findCursor = function(index) {
  var cursor = this.head;
  var i = 0;

  while(cursor && i < index) {
    cursor = cursor.next;
    i++;
  }

  if(!cursor) {
    throw 'IndexOutOfBounds: ' + index;
  }

  return cursor;
};

/**
 * Sort the linked list.
 */
LinkedList.prototype.sort = function() {
  var list = this.head;

  while(list) {
    var pass = list.next;

    while(pass) {
      if(list.data < pass.data) {
        var tmp = list.data;
        list.data = pass.data;
        pass.data = tmp;
      }

      pass = pass.next;
    }

    list = list.next;
  }
};

/**
 * Search for the first occurrence of a term in the linked list.
 *
 * @param {*} term the term to find
 * @returns {*} the term, if found, null if not.
 */
LinkedList.prototype.find = function(term) {
  var cursor = this.findCursor(0);

  while(cursor && cursor.data != term) {
    cursor = cursor.next;
  }

  return cursor ? cursor.data : null;
};

/**
 * Search for all occurrences of a term in the linked list.
 *
 * @param {*} term the term to search for.
 * @returns {Array} an array containing all occurrences of the term, or an empty list if not found.
 */
LinkedList.prototype.findAll = function(term) {
  var list = [];
  var cursor = this.findCursor(0);

  while(cursor) {
    if(cursor.data == term) {
      list.push(cursor.data);
    }

    cursor = cursor.next;
  }

  return list;
};

/**
 * Reverse the linked list.
 */
LinkedList.prototype.reverse = function() {
  var currentNode = this.head;
  var previousNode, nextNode;

  while(currentNode) {
    nextNode = currentNode.next;
    currentNode.next = previousNode;
    previousNode = currentNode;
    currentNode = nextNode;
  }

  this.tail = this.head;
  this.head = previousNode;
};

module.exports = LinkedList;
