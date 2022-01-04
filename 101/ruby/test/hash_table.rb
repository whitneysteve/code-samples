# frozen_string_literal: true

require_relative '../hash_table'
require 'minitest/autorun'

class HashTableTest < Minitest::Test
  KEY1 = 'KEY1'
  KEY2 = 'KEY2'
  VALUE1 = 'VALUE1'
  VALUE2 = 'VALUE2'

  def test_it_should_add_elements
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    hash.put(KEY2, VALUE2)
    assert hash.get(KEY1) == VALUE1
    assert hash.get(KEY2) == VALUE2
    assert hash.get('key').nil?
  end

  def test_it_should_overwrite_elements
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    hash.put(KEY1, VALUE2)
    assert hash.get(KEY1) == VALUE2
  end

  def test_it_should_calculate_size
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    hash.put(KEY2, VALUE2)
    assert hash.size == 2
  end

  def test_it_should_get_keys
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    hash.put(KEY2, VALUE2)
    assert hash.keys.sort == [KEY1, KEY2]
  end

  def test_it_should_get_values
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    hash.put(KEY2, VALUE2)
    assert hash.values.sort == [VALUE1, VALUE2]
  end

  def test_it_should_remove_key
    hash = HashTable.new(10)
    hash.put(KEY1, VALUE1)
    assert hash.get(KEY1) == VALUE1
    hash.remove(KEY1)
    assert hash.get(KEY1).nil?
  end
end
