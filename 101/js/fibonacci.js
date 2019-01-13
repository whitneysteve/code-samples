/**
 * Calculate the number at a given index if the fibonacci sequence.
 *
 * @param {number} index to calculate for.
 * @returns {number} the number at the given index in the fibonacci sequence.
 */
const fibonacci = (index) => {
  if(index < 0) {
    return 0;
  }

  if(index < 1) {
    return 1;
  } else {
    return fibonacci(index - 1) + fibonacci(index - 2);
  }
};

module.exports = fibonacci;
