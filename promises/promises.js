/**
 * Create a simple Promise object that only supports a
 * then method.
 *
 * @param {Function} fn the function to perform.
 * @return Promise object.
 * @constructor
 */
function MyPromise(fn) {
  return {
    /**
     * Complete the function, optionally execute a function once
     * the promise is complete.
     *
     * @param {Function} Optional. onSuccess function to be executed
     * following completion of promise function. If not provided the
     * promise function is still executed.
     */
    then: function(onSuccess) {
      if (typeof onSuccess !== 'undefined') {
        return MyPromise(function() {
          onSuccess(fn());
        });
      } else {
        fn();
      }
    },
    /**
     * Resolve the promise.
     */
    resolve: function() {
      this.then();
    }
  };
}

module.exports = MyPromise;
