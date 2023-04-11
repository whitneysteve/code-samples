/**
 * Perform a Bubble Sort on an array.
 *
 * Sort is performed in place and assumes conflict ordering is
 * not important.
 *
 * @param {Array} array the array to sort.
 */
export const bubbleSort = (arr: Array<number>): void => {
  arr.forEach((_, i) => {
    arr.forEach((_, j) => {
      if (arr[i] < arr[j]) {
        swap(arr, i, j);
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
export const insertionSort = (arr: Array<number>): void => {
  arr.forEach((_, i) => {
    let j = 0;

    while (j < i) {
      if (arr[j] > arr[i]) {
        reposition(arr, i, j);
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
export const mergeSort = (arr: Array<number>): Array<number> => {
  if (arr.length < 2 || (arr.length === 2 && arr[1] >= arr[0])) {
    return arr;
  } else if (arr.length === 2) {
    return [arr[1], arr[0]];
  } else {
    const mid = Math.floor(arr.length / 2);
    const first = mergeSort(arr.slice(0, mid));
    const second = mergeSort(arr.slice(mid));
    return merge(first, second);
  }

  function merge(first: Array<number>, second: Array<number>) {
    const result = new Array<number>();
    let i = 0;
    let j = 0;

    while (i < first.length && j < second.length) {
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
export const quickSort = (
  arr: Array<number>,
  low?: number,
  high?: number
): void => {
  const lo = low ?? 0;
  const hi = high ?? arr.length - 1;

  if (lo < hi) {
    const partitionIndex = partition(lo, hi);
    quickSort(arr, lo, partitionIndex - 1);
    quickSort(arr, partitionIndex + 1, hi);
  }

  function partition(low: number, high: number) {
    const pivot = arr[high];
    let pivotIndex = low;

    for (let j = pivotIndex; j < high; j++) {
      if (arr[j] <= pivot) {
        swap(arr, pivotIndex, j);
        pivotIndex++;
      }
    }

    swap(arr, pivotIndex, high);

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
export const selectionSort = (arr: Array<number>): void => {
  arr.forEach((_, i) => {
    let least = Number.MAX_SAFE_INTEGER;
    let leastIndex = i;

    for (let j = i; j < arr.length; j++) {
      const next = arr[j];
      if (next < least) {
        least = next;
        leastIndex = j;
      }
    }

    if (leastIndex !== i) {
      swap(arr, i, leastIndex);
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
const reposition = (array: Array<number>, from: number, to: number) => {
  array.splice(to, 0, array.splice(from, 1)[0]);
};

/**
 * Swap two elements in place, in an array.
 *
 * @param {Array} array they array in which to swap elements.
 * @param {Number} i the index of the first element to swap.
 * @param {Number} j the index of the second element to swap.
 */
const swap = (arr: Array<number>, i: number, j: number) => {
  const tmp = arr[i];
  arr[i] = arr[j];
  arr[j] = tmp;
};
