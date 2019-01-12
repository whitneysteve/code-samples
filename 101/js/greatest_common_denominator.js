/**
 * Find the GCD of two numbers.
 *
 * @param {number} num1 the first number.
 * @param {number} num2 the second number.
 * @return the GCD of the two specified numbers.
 */
const greatestCommonDenominator = function(num1, num2) {
  var remainder = num1 % num2;

  if(remainder == 0) {
    return num2;
  } else {
    return greatestCommonDenominator(num2, remainder);
  }
};

module.exports = greatestCommonDenominator;