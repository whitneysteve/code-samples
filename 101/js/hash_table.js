String.prototype.hashCode = function() {
  var hash = 0, i, chr;
  if (this.length > 0 ) {
    for(i = 0; i < this.length; i ++) {
      chr = this.charCodeAt(i);
      hash = ((hash << 5) - hash) + chr;
      hash |= 0;
    }
  }
  return hash;
};

/**
 * Construct a HashTable object with a fixed amount of buckets.
 *
 * @param {Number} buckets the number of buckets to initialise for the HashTable.
 * @constructor
 */
function HashTable(buckets) {
  this.buckets = new Array(buckets);

  /**
   * Get an item from the hash table if it exists.
   *
   * @param {Object} key the key for the item in the HashTable.
   * @return the item for the key, if found.
   */
  this.get = function(key) {
    const bucket = this._targetBucket(key);
    return bucket.get(key);
  };

  /**
   * Get the keys for all items in the HashTable.
   *
   * The keys are computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than iteration.
   *
   * @return array of the keys, in no particular order, for the items stored in the
   *         HashTable.
   */
  this.keys = function() {
    return this.buckets.map(bucket => bucket.keys())
      .reduce((acc, keys) => acc.concat(keys));
  };

  /**
   * Put an item in the HashTable.
   *
   * @param {Object} key the key to retrieve the stored value. The key is also used
   *                     to determine what bucket we store the item in.
   * @param {Object} value the value to store.
   */
  this.put = function(key, value) {
    this._targetBucket(key).put(key, value);
  };

  /**
   * Remove an item from the HashTable.
   *
   * @param {Object} key the key for the item to remove in the HashTable.
   */
  this.remove = function(key) {
    this._targetBucket(key).remove(key);
  };

  /**
   * Get the number of items stored in the HashTable.
   *
   * The value is computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than size.
   *
   * @return the number of items stored in the HashTable.
   */
  this.size = function() {
    return this.buckets.map(bucket => bucket.size())
      .reduce((acc, size) => acc + size);
  };

  /**
   * Get all the values stored in the HashTable.
   *
   * The values are computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than iteration.
   *
   * @return array of the values, in no particular order, stored in the HashTable.
   */
  this.values = function() {
    return this.buckets.map(bucket => bucket.values())
      .reduce((acc, values) => acc.concat(values));
  };

  /**
   * Get the bucket that a key would be stored under.
   *
   * @param {Object} key the key of for which to find the bucket.
   * @return the bucket the key would be stored under, will always return a bucket.
   */
  this._targetBucket = function(key) {
    const bucketIndex = key.hashCode() % this.buckets.length;
    var bucket = this.buckets[bucketIndex];
    if (!bucket) {
      bucket = new Bucket();
      this.buckets[bucketIndex] = bucket;
      return bucket;
    } else {
      return bucket;
    }
  };
}

/**
 * A bucket in a HashTable. Performs the storage of items in the HashTable.
 *
 * This bucket uses an array to store the items internally.
 *
 * @constructor
 */
function Bucket() {
  this.items = [];

  /**
   * Store an item in the bucket.
   *
   * @param {Object} key the key to retrieve the stored value.
   * @param {Object} value the value to store.
   */
  this.put = function(key, value) {
    const existing = this._get(key);
    if (existing) {
      existing.value = value;
    } else {
      const item = new Item(key, value);
      this.items.push(item);
    }
  };

  /**
   * Get an item from the bucket if it exists.
   *
   * @param {Object} key the key for the item in the bucket.
   * @return the item for the key, if found.
   */
  this.get = function(key, value) {
    var found = this._get(key);
    if (found) {
      return found.value;
    }
  };

  /**
   * Get the keys for all items in the bucket.
   *
   * @return array of the keys, in no particular order, for the items stored in the
   *         bucket.
   */
  this.keys = function() {
    return this.items.map(item => item.key);
  };

  /**
   * Remove an item from the bucket.
   *
   * @param {Object} key the key for the item to remove in the bucket.
   */
  this.remove = function(key) {
    for(var i = 0; i < this.items.length; i ++) {
      if (this.items[i].key === key) {
        this.items.splice(i, 1);
        break;
      }
    }
  }

  /**
   * Get the number of items in the bucket.
   *
   * @return the number of items in the bucket
   */
  this.size = function() {
    return this.items.length;
  };

  /**
   * Get the values in the bucket.
   *
   * These could be stored in an array separate to the keys to remove the iterations here,
   * and the key array could maintain a pointer to the values in their array but choosing
   * this for now to optimise put, get and remove.
   *
   * @return the values in the bucket.
   */
  this.values = function() {
    return this.items.map(item => item.value);
  };

  /**
   * Get an item from the bucket.
   *
   * @param {Object} the key that the item is stored under.
   */
  this._get = function(key) {
    return this.items.find(item => {
      return item.key === key;
    });
  };
}

/**
 * Represents an item stored in the HashTable.
 *
 * @param {Object} providedKey the key the item is stored under.
 * @param {Object} providedValue the value being stored.
 * @constructor
 */
function Item(providedKey, providedValue) {
  this.key = providedKey;
  this.value = providedValue;
}

module.exports = HashTable;
