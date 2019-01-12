import scala.collection.mutable.ListBuffer

/**
  * Hashtable implementation.
  *
  * @param numBuckets the number of buckets to use within the hash table.
  * @tparam K the type of key used in the hashtable.
  * @tparam V the type of value stored in the hashtable.
  */
class Hashtable[K, V](numBuckets: Int) {

  private val buckets = List[Bucket[K, V]](
    (1 to numBuckets).map(_ => new Bucket[K, V]()):_*
  )

  /**
    * Get a value from the hash table if it exists.
    *
    * @param key the key to fetch.
    * @return [[Option]] that resolves to the value if found, otherwise [[None]].
    */
  def get(key: K): Option[V] = {
    getBucket(key).get(key)
  }

  /**
    * Get the set of keys currently in the hash table. This will iterate over all entries in the
    * hash table. We could keep an index of keys but choosing the iteration approach to optimise
    * for CRUD operations.
    *
    * @return the [[Set]] of keys currently in the hash table.
    */
  def keys: Set[K] = {
    buckets.flatMap(_.keys).toSet
  }

  /**
    * Store a value in the hashtable under a specified key.
    *
    * @param key the key under which to store the value.
    * @param value the value we want to store.
    */
  def put(key: K, value: V): Unit = {
    getBucket(key).put(key, value)
  }

  /**
    * Remove a value from the hash table if it exists.
    *
    * @param key the key for the value we want to remove.
    * @return [[Option]] that resolves to the removed value if found, otherwise [[None]].
    */
  def remove(key: K): Option[V] = {
    getBucket(key).remove(key)
  }

  /**
    * Get the number if key value pairs in the hash table.
    *
    * @return the number if key value pairs in the hash table.
    */
  def size: Int = {
    0
  }

  /**
    * Get the set of values currently in the hash table. This will iterate over all entries in the
    * hash table. We could keep an index of keys but choosing the iteration approach to optimise
    * for CRUD operations.
    *
    * @return the [[Set]] of values currently in the hash table.
    */
  def values: Set[V] = {
    buckets.flatMap(_.values).toSet
  }

  /**
    * Get the [[Bucket]] for a given key.
    *
    * @param key the key to retrieve the [[Bucket]] for.
    * @return the [[Bucket]] for the specified key.
    */
  private def getBucket(key: K): Bucket[K, V] = {
    val bucketIndex = Math.abs(key.hashCode() % numBuckets)
    buckets(bucketIndex)
  }
}

/**
  * A partition of items stored in a hash table.
  *
  * @tparam K the type of key used in the hash table.
  * @tparam V the type of value stored in the hash table.
  */
private class Bucket[K, V] {
  private val buffer: ListBuffer[BucketItem[K, V]] = ListBuffer.empty

  /**
    * Get a value for a given key, if it exists.
    *
    * @param key the key for the value to retrieve.
    * @return [[Option]] that resolves to the value stored in the [[Bucket]], [[None]] if not.
    */
  def get(key: K): Option[V] = {
    getItem(key).map(_.value)
  }

  /**
    * Get the [[Set]] of keys used in this bucket. This will iterate over all entries in the
    * bucket. We could keep an index of keys but choosing the iteration approach to optimise
    * for CRUD operations.
    *
    * @return all the keys used int his bucket.
    */
  def keys: Set[K] = {
    buffer.map(_.key).toSet
  }

  /**
    * Store a [[BucketItem]] in the [[Bucket]] by key.
    *
    * @param key the key to store the value under.
    * @param value the value to store.
    */
  def put(key: K, value: V): Unit = {
     getItem(key) match {
       case Some(item) => item.value = value
       case _ => buffer.append(BucketItem(key, value))
     }
  }

  /**
    * Remove an [[BucketItem]] from the [[Bucket]].
    *
    * @param key the key of the item to remove from the [[Bucket]].
    * @return [[Option]] that resolves to the removed value, if found. [[None]] otherwise.
    */
  def remove(key: K): Option[V] = {
    var idx = 0
    while (idx < buffer.size) {
      val nextVal = buffer(idx)
      if (nextVal.key == key) {
        buffer.remove(idx)
        return Some(nextVal.value)
      }
      idx += 0
    }
    None
  }

  /**
    * Get the [[Set]] of values used in this bucket. This will iterate over all entries in the
    * bucket. We could keep an index of values but choosing the iteration approach to optimise
    * for CRUD operations.
    *
    * @return all the keys used int his bucket.
    */
  def values: Set[V] = {
    buffer.map(_.value).toSet
  }

  /**
    * Get a [[BucketItem]] for a given key, if it exists.
    *
    * @param key the key for the value to retrieve.
    * @return [[Option]] that resolves to the [[BucketItem]] stored in the [[Bucket]], [[None]] if not.
    */
  private def getItem(key: K): Option[BucketItem[K, V]] = {
    // TODO: implement better search!
    buffer.find(_.key == key)
  }
}

/**
  * A wrapper class for storing a key and a value in a [[Bucket]].
  *
  * @param key the key the value is stored under.
  * @param value the value being stored.
  * @tparam K the key type
  * @tparam V the value type
  */
private case class BucketItem[K, V](var key: K, var value: V)

object Main {
  def main(args: Array[String]): Unit = {
    val hashtable = new Hashtable[String, String](10)

    val Key1 = "key1"
    val Key2 = "key2"
    val Key3 = "key3"
    val Value1 = "value1"
    val Value2 = "value2"
    val Value3 = "value3"

    hashtable.put(Key1, Value1)
    hashtable.put(Key2, Value2)
    hashtable.put(Key3, Value3)

    assert(hashtable.get(Key1).contains(Value1))
    assert(hashtable.get(Key2).contains(Value2))

    assert(hashtable.remove(Key1).contains(Value1))
    assert(hashtable.remove("not a key").isEmpty)
    assert(hashtable.get(Key1).isEmpty)

    assert(hashtable.keys == Set(Key2, Key3))

    val NewValue = "new value"
    hashtable.put(Key1, NewValue)
    hashtable.put(Key2, NewValue)

    assert(hashtable.values == Set(NewValue, Value3))

    assert(hashtable.get(Key1).contains(NewValue))
    assert(hashtable.get(Key2).contains(NewValue))
  }
}
