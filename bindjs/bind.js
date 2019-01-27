/**
 * Create a new function that, when called, has its this keyword set
 * to the provided value, with a given sequence of arguments
 * preceding any provided when the new function is called.
 *
 * @param {Function} fn the function to be bound.
 * @param {Object} context the context to bind the function to.
 * @return Function.
 * @constructor
 */
function bind(func, context) {
  return function() {
    return func.apply(context, arguments);
  };
}

module.exports = bind;
