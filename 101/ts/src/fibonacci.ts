/**
 * Calculate the number at a given index if the fibonacci sequence.
 *
 * @param {number} idx to calculate for.
 * @returns {number} the number at the given index in the fibonacci sequence.
 */
export const fibonacci = (idx: number): number => {
  if (idx < 0) {
    return 0;
  } else if (idx < 1) {
    return 1;
  } else {
    return fibonacci(idx - 1) + fibonacci(idx - 2);
  }
};
