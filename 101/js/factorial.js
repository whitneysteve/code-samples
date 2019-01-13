/**
 * Calculate the factorial of a number.
 *
 * @param num the number to calculate the factorial of.
 * @returns {number} the factorial of specified number.
 */
const factorial = (num)  =>{
  if(num == 1) {
    return 1;
  } else {
    return num * factorial(num - 1);
  }
};

module.exports = factorial;