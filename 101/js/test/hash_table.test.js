const expect = require('chai').expect;
const HashTable = require('../hash_table');

describe('Hashtable', () => {
  const key1 = "key1";
  const key2 = "key2";
  const value1 = "value1";
  const value2 = "value2";

  const static = new HashTable(10);
  static.put(key1, value1);
  static.put(key2, value2);

  it('should add elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key2, value2);
    expect(hash.get(key1)).to.be.equal(value1);
    expect(hash.get(key2)).to.be.equal(value2);
    expect(hash.get("key")).to.be.undefined;
  });

  it('should overwrite elements', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    hash.put(key1, value2);
    expect(hash.get(key1)).to.be.equal(value2);
  });

  it('should calculate size', () => {
    expect(static.size()).to.be.equals(2);
  });

  it('should get keys', () => {
    expect(static.keys()).to.be.deep.equals([key1, key2]);
  });

  it('should get values', () => {
    expect(static.values()).to.be.deep.equals([value1, value2]);
  });

  it('should remove key', () => {
    const hash = new HashTable(10);
    hash.put(key1, value1);
    expect(hash.get(key1)).to.be.equal(value1);
    hash.remove(key1);
    expect(hash.get(key1)).to.be.undefined;
  });
});

const t = new HashTable(10);
        const key1 = "key1";
        const key2 = "key2";

        t.put(key1, "value1");
        t.put(key2, "value2");
        t.put("key3", "value3");

        console.log("Size: %s", t.size());
        console.log("Value 1: %s", t.get(key1));
        console.log("Value 2: %s", t.get(key2));
        console.log("Keys: %s", t.keys().join(","));
        console.log("Values: %s", t.values().join(","));

        t.remove(key1);
        console.log("Value 1: %s", t.get(key1));

        t.put(key2, "new value");
        console.log("Value 2: %s", t.get(key2));