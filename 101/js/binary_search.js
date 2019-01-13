/**
 * Perform a binary search for an Int.
 *
 * @param {Array} array the array to search.
 * @param {Number} term the term to search for.
 * @return {Boolean} true if found, false if not.
 */
const binarySearch = (array, term) => {
  var low = 0;
  var hi = array.length - 1;

  while(low <= hi) {
    var mid = ( low + hi ) / 2;
    var midTerm = array[ mid ];

    if(midTerm > term) {
      hi = mid - 1;
    } else if(midTerm < term) {
      low = mid + 1;
    } else {
      return true;
    }
  }

  return false;
};

module.exports = binarySearch;