/**
 * Sort helper object.
 * @constructor
 */
function Sort() {
  /**
   * Perform a Bubble Sort on an array.
   *
   * Sort is performed in place and assumes conflict ordering is
   * not important.
   *
   * @param {Array} array the array to sort.
   */
  this.bubbleSort = (array) => {
    const self = this;
    array.forEach((_, i) => {
      array.forEach((_, j) => {
        if (array[i] < array[j]) {
          self.swap(array, i, j);
        }
      });
    });
  };

  /**
   * Perform an Insertion Sort on an array.
   *
   * Sort is performed in place and assumes conflict ordering is
   * not important.
   *
   * @param {Array} array the array to sort.
   */
  this.insertionSort = (array) => {
    const self = this;
    array.forEach((_, i) => {
      var j = 0;

      while(j < i) {
        if (array[j] > array[i]) {
          self.reposition(array, i, j);
          break;
        }
        j++;
      }
    });
  };

  /**
   * Perform a Merge Sort on an array.
   *
   * Sort is creates a new array in the return val and, where conflict arises,
   * original order is maintained.
   *
   * @param {Array} array the array to sort.
   * @return the sorted array.
   */
  this.mergeSort = (array) => {
    if (array.length < 2 || (array.length === 2 && array[1] >= array[0])) {
      return array;
    } else if (array.length === 2 && array[0] > array[1]) {
      return [array[1], array[0]];
    } else {
      const mid = Math.floor(array.length / 2);
      const firstHalf = this.mergeSort(array.slice(0, mid));
      const secondHalf = this.mergeSort(array.slice(mid));
      return merge(firstHalf, secondHalf);
    }

    function merge (first, second) {
      const result = [];
      var i = 0, j = 0;

      while(i < first.length && j < second.length) {
        if (first[i] > second[j]) {
          result.push(second[j]);
          j++;
        } else {
          result.push(first[i]);
          i++;
        }
      }

      return result.concat(first.slice(i), second.slice(j));
    }
  };

  /**
   * Perform a Quick Sort on an array. Sort is performed in place and assumes
   * conflict order is not important.
   */
  this.quickSort = (array, low, high) => {
    const self = this;

    if (!low) {
      low = 0;
    }
    if (!high) {
      high = array.length - 1;
    }

    if (low < high) {
      const partitionIndex = partition(low, high);
      this.quickSort(array, low, partitionIndex - 1);
      this.quickSort(array, partitionIndex + 1, high);
    }

    function partition(low, high) {
      const pivot = array[high];
      var pivotIndex = low;

      for (var j = pivotIndex; j < high; j ++) {
        if (array[j] <= pivot) {
          self.swap(array, pivotIndex, j);
          pivotIndex++;
        }
      }

      self.swap(array, pivotIndex, high);

      return pivotIndex;
    }
  };

  /**
   * Perform a Selection Sort on an array.
   *
   * Sort is performed in place and assumes conflict ordering is
   * not important.
   *
   * This method could use Math.min.apply and .slice to determine the
   * least value in an array but doesn't to display use of tracking multiple
   * positions.
   *
   * @param {Array} array the array to sort.
   */
  this.selectionSort = (array) => {
    const self = this;

    array.forEach((_, i) => {
      var least = Number.MAX_SAFE_INTEGER;
      var leastIndex = i;
      for (var j = i; j < array.length; j++) {
        const next = array[j];
        if (next < least) {
          least = next;
          leastIndex = j;
        }
      }
      if (leastIndex !== i) {
        self.swap(array, i, leastIndex);
      }
    });
  };

  /**
   * Reposition an element in array.
   *
   * @param {Array} array the array in which to move elements.
   * @param {Number} from the index of the element to move.
   * @param {Number} to the index to move the element to.
   */
  this.reposition = (array, from, to) => {
    array.splice(to, 0, array.splice(from, 1)[0]);
  };

  /**
   * Swap two elements in place, in an array.
   *
   * @param {Array} array they array in which to swap elements.
   * @param {Number} i the index of the first element to swap.
   * @param {Number} j the index of the second element to swap.
   */
  this.swap = (array, i ,j) => {
    const tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  };
}

module.exports = Sort
