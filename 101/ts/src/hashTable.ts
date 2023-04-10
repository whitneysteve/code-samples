class KeyValue<T> {
  key: string;
  value: T;

  constructor(key: string, value: T) {
    this.key = key;
    this.value = value;
  }
}

/**
 * A bucket in a HashTable. Performs the storage of items in the HashTable.
 *
 * This bucket uses an array to store the items internally.
 *
 * @constructor
 */
class Bucket<T> {
  arr: Array<KeyValue<T>>;

  constructor() {
    this.arr = [];
  }

  /**
   * Get an item from the bucket if it exists.
   *
   * @param {Object} key the key for the item in the bucket.
   * @return the item for the key, if found.
   */
  get = (key: string): KeyValue<T> | undefined => this.find(key);

  /**
   * Get the keys for all items in the bucket.
   *
   * @return array of the keys, in no particular order, for the items stored in the
   *         bucket.
   */
  keys = (): Array<string> => this.arr.map(keyValue => keyValue.key);

  /**
   * Store an item in the bucket.
   *
   * @param {Object} key the key to retrieve the stored value.
   * @param {Object} value the value to store.
   */
  put = (key: string, value: T) => {
    const existing = this.find(key);
    if (existing) {
      existing.value = value;
    } else {
      this.arr.unshift(new KeyValue(key, value));
    }
  };

  /**
   * Remove an item from the bucket.
   *
   * @param {Object} key the key for the item to remove in the bucket.
   */
  remove = (key: string): boolean => {
    const idx = this.findIndex(key);
    if (idx > -1) {
      this.arr.splice(idx, 1);
      return true;
    }
    return false;
  };

  /**
   * Get the number of items in the bucket.
   *
   * @return the number of items in the bucket
   */
  size = () => this.arr.length;

  /**
   * Get the values in the bucket.
   *
   * These could be stored in an array separate to the keys to remove the iterations here,
   * and the key array could maintain a pointer to the values in their array but choosing
   * this for now to optimise put, get and remove.
   *
   * @return the values in the bucket.
   */
  values = (): Array<T> => this.arr.map(keyValue => keyValue.value);

  /**
   * Get an item from the bucket.
   *
   * @param {Object} the key that the item is stored under.
   */
  private find = (key: string): KeyValue<T> | undefined => {
    const idx = this.findIndex(key);
    if (idx > -1) {
      return this.arr[idx];
    }
    return undefined;
  };

  private findIndex = (keyToFind: string): number => {
    return this.arr.findIndex(({key}) => key === keyToFind);
  };
}

/**
 * Construct a HashTable object with a fixed amount of buckets.
 *
 * @param {Number} buckets the number of buckets to initialise for the HashTable.
 * @constructor
 */
export class HashTable<T> {
  buckets: Array<Bucket<T>>;

  constructor(numBuckets: number) {
    this.buckets = new Array(numBuckets);
  }

  /**
   * Get an item from the hash table if it exists.
   *
   * @param {Object} key the key for the item in the HashTable.
   * @return the item for the key, if found.
   */
  get = (key: string): T | undefined => this.targetBucket(key).get(key)?.value;

  /**
   * Get the keys for all items in the HashTable.
   *
   * The keys are computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than iteration.
   *
   * @return array of the keys, in no particular order, for the items stored in the
   *         HashTable.
   */
  keys = (): Array<string> =>
    this.buckets
      .map(bucket => bucket.keys())
      .reduce((acc: Array<string>, keys: Array<string>) => acc.concat(keys));

  /**
   * Put an item in the HashTable.
   *
   * @param {Object} key the key to retrieve the stored value. The key is also used
   *                     to determine what bucket we store the item in.
   * @param {Object} value the value to store.
   */
  put = (key: string, value: T): void => this.targetBucket(key).put(key, value);

  /**
   * Remove an item from the HashTable.
   *
   * @param {Object} key the key for the item to remove in the HashTable.
   */
  remove = (key: string): boolean => this.targetBucket(key).remove(key);

  /**
   * Get the number of items stored in the HashTable.
   *
   * The value is computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than size.
   *
   * @return the number of items stored in the HashTable.
   */
  size = (): number =>
    this.buckets
      .map(bucket => bucket.size())
      .reduce((acc: number, size: number) => acc + size);

  /**
   * Get all the values stored in the HashTable.
   *
   * The values are computed each time by iterating through the entries. These could
   * be maintained on update but optimising for puts and gets, rather than iteration.
   *
   * @return array of the values, in no particular order, stored in the HashTable.
   */
  values = (): Array<T> =>
    this.buckets
      .map(bucket => bucket.values())
      .reduce((acc: Array<T>, values: Array<T>) => acc.concat(values));

  /**
   * Get the bucket that a key would be stored under.
   *
   * @param {Object} key the key of for which to find the bucket.
   * @return the bucket the key would be stored under, will always return a bucket.
   */
  private targetBucket = (key: string): Bucket<T> => {
    const idx = hashCode(key) % this.buckets.length;
    if (!this.buckets[idx]) {
      this.buckets[idx] = new Bucket();
    }
    return this.buckets[idx];
  };
}

const hashCode = (str: string): number => {
  let hash = 0;
  if (str.length > 0) {
    for (let i = 0; i < str.length; i++) {
      const chr = str.charCodeAt(i);
      hash = (hash << 5) - hash + chr;
      hash |= 0;
    }
  }
  return hash;
};
