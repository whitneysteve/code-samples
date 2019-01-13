const expect = require('chai').expect;
const HashTable = require('../hash_table');

describe('Hashtable', () => {
  const key1 = 'key1';
  const key2 = 'key2';
  const value1 = 'value1';
  const value2 = 'value2';

  const static_hash = new HashTable(10);
  static_hash.put(key1, value1);
  static_hash.put(key2, value2);

  it('should add elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key2, value2);
    expect(hash.get(key1)).to.be.equal(value1);
    expect(hash.get(key2)).to.be.equal(value2);
    expect(hash.get('key')).to.be.undefined;
  });

  it('should overwrite elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key1, value2);
    expect(hash.get(key1)).to.be.equal(value2);
  });

  it('should calculate size', () => {
    expect(static_hash.size()).to.be.equals(2);
  });

  it('should get keys', () => {
    expect(static_hash.keys()).to.be.deep.equals([key1, key2]);
  });

  it('should get values', () => {
    expect(static_hash.values()).to.be.deep.equals([value1, value2]);
  });

  it('should remove key', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    expect(hash.get(key1)).to.be.equal(value1);
    hash.remove(key1);
    expect(hash.get(key1)).to.be.undefined;
  });
});
