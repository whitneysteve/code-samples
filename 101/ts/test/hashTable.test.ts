import {HashTable} from '../src/hashTable';

describe('HashTable', () => {
  const key1 = 'key1';
  const key2 = 'key2';
  const value1 = 'value1';
  const value2 = 'value2';

  const static_hash = new HashTable(10);
  static_hash.put(key1, value1);
  static_hash.put(key2, value2);

  test('should add elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key2, value2);
    expect(hash.get(key1)).toBe(value1);
    expect(hash.get(key2)).toBe(value2);
    expect(hash.get('key')).toBeUndefined();
  });

  test('should overwrite elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key1, value2);
    expect(hash.get(key1)).toBe(value2);
  });

  test('should calculate size', () => {
    expect(static_hash.size()).toBe(2);
  });

  test('should get keys', () => {
    expect(static_hash.keys()).toStrictEqual([key1, key2]);
  });

  test('should get values', () => {
    expect(static_hash.values()).toEqual([value1, value2]);
  });

  test('should remove key', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    expect(hash.get(key1)).toBe(value1);
    hash.remove(key1);
    expect(hash.get(key1)).toBeUndefined();
  });
});
