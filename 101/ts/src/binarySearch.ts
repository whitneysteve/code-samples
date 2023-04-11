/**
 * Perform a binary search for an Int.
 *
 * @param {Array} arr the array to search.
 * @param {Number} term the term to search for.
 * @return {Boolean} true if found, false if not.
 */
export const binarySearch = (arr: Array<number>, term: number): boolean => {
  let lo = 0;
  let hi = arr.length - 1;

  while (hi > lo) {
    const mid = (lo + hi) / 2;
    const value = arr[mid];

    if (value === term) {
      return true;
    } else if (value > term) {
      hi = mid - 1;
    } else {
      lo = mid + 1;
    }
  }

  return false;
};
