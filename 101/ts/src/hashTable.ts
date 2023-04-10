class KeyValue<T> {
  key: string;
  value: T;

  constructor(key: string, value: T) {
    this.key = key;
    this.value = value;
  }
}

class Bucket<T> {
  arr: Array<KeyValue<T>>;

  constructor() {
    this.arr = [];
  }

  get = (key: string): KeyValue<T> | undefined => this.find(key);

  keys = (): Array<string> => this.arr.map(keyValue => keyValue.key);

  put = (key: string, value: T) => {
    const existing = this.find(key);
    if (existing) {
      existing.value = value;
    } else {
      this.arr.unshift(new KeyValue(key, value));
    }
  };

  remove = (key: string): boolean => {
    const idx = this.findIndex(key);
    if (idx > -1) {
      this.arr.splice(idx, 1);
      return true;
    }
    return false;
  };

  size = () => this.arr.length;

  values = (): Array<T> => this.arr.map(keyValue => keyValue.value);

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

export class HashTable<T> {
  buckets: Array<Bucket<T>>;

  constructor(numBuckets: number) {
    this.buckets = new Array(numBuckets);
  }

  get = (key: string): T | undefined => this.targetBucket(key).get(key)?.value;

  keys = (): Array<string> =>
    this.buckets
      .map(bucket => bucket.keys())
      .reduce((acc: Array<string>, keys: Array<string>) => acc.concat(keys));

  put = (key: string, value: T): void => this.targetBucket(key).put(key, value);

  remove = (key: string): boolean => this.targetBucket(key).remove(key);

  size = (): number =>
    this.buckets
      .map(bucket => bucket.size())
      .reduce((acc: number, size: number) => acc + size);

  private targetBucket = (key: string): Bucket<T> => {
    const idx = hashCode(key) % this.buckets.length;
    if (!this.buckets[idx]) {
      this.buckets[idx] = new Bucket();
    }
    return this.buckets[idx];
  };

  values = (): Array<T> =>
    this.buckets
      .map(bucket => bucket.values())
      .reduce((acc: Array<T>, values: Array<T>) => acc.concat(values));
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
