/**
 * Find the rotation point of a rotated array.
 *
 * @param {Array} arr the rotated array.
 * @return {number} the rotation point in the array.
 */
const findRotationPoint = (arr) => {
  var lastIndex = arr.length - 1;
  var lo = 0;
  var hi = lastIndex;

  while(lo <= hi) {
    var mid = Math.floor(( lo + hi ) / 2);
    var term = arr[ mid ];

    if(mid == lastIndex) {
      return term;
    }

    var nextTerm = arr[ mid + 1 ];

    if(term > nextTerm) {
      return nextTerm;
    }

    if(term > arr[ lastIndex ]) {
      lo = mid + 1;
    } else {
      hi = mid - 1;
    }
  }

  return arr[ 0 ];
};

module.exports = findRotationPoint;
