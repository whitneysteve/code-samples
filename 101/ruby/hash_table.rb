# Hash table implementation.
class HashTable
  public

  def initialize(num_buckets)
    @buckets = []
    @num_buckets = num_buckets
  end

  # Get a value from the hash table by key, if it exists.
  def get(key)
    target_bucket(key).get(key)
  end

  # Put a value in the hash table and store it by key.
  def put(key, value)
    target_bucket(key).put(key, value)
  end

  # Get all the keys in the hash table, in no particular order.
  def keys
    @buckets.flat_map {|bucket| bucket.keys if bucket}.compact
  end

  # Remove an item from the hash table by key, if it exists.
  def remove(key)
    target_bucket(key).remove(key)
  end

  # Get the nymber of items stored in the hash table.
  def size
    @buckets.flat_map {|bucket| bucket.keys.size if bucket}.compact.sum
  end

  # Get all the values in the hash table, in no particular order.
  def values
    @buckets.flat_map {|bucket| bucket.values if bucket}.compact
  end

  private

  # Get the target bucket for a given key.
  def target_bucket(key)
    bucket_index = key.hash % @num_buckets
    bucket = @buckets[bucket_index]
    if bucket.nil?
      bucket = Bucket.new()
      @buckets[bucket_index] = bucket
      bucket
    else
      bucket
    end
  end
end

class Bucket
  public

  def initialize()
    @items = []
  end

  # Search for a value by the given key in the bucket.
  def get(key)
    item = get_item(key)
    if item.nil?
      nil
    else
      item.value
    end
  end

  # Get the keys stored in the bucket. This iterates over the item array and
  # we avoid creating a running index of keys to optimise CRUD operations.
  def keys
    @items.map {|item| item.key}
  end

  # Store or overwrite a value for a given key in the bucket.
  def put(key, value)
    existing = get_item(key)
    if existing.nil?
      @items.push(BucketItem.new(key, value))
    else
      existing.value = value
    end
  end

  # Remove an item from the bucket if it exists.
  def remove(key)
    existing = get_item(key)
    @items.delete(existing) unless existing.nil?
  end

  # Get the values for all items in the bucket.
  def values
    @items.map {|item| item.value}
  end

  private
  # Search for an item in the bucket by key. Item contains key and value, as opposed
  # to just value.
  def get_item(key)
    @items.find {|item| item.key == key}
  end
end

class BucketItem
  public

  attr_accessor :key
  attr_accessor :value

  def initialize(key, value)
    @key = key
    @value = value
  end
end
