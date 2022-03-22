# frozen_string_literal: true

require_relative '../word_distance'
require 'minitest/autorun'

class WordDistanceTest < Minitest::Test
  def test_should_find_minimum_word_distance
    sentence = 'the quick brown dog jumped over the lazy dog'
    distance = WordDistance.new(sentence)
    assert_equal 1, distance.distance('the', 'lazy')
    assert_equal 1, distance.distance('the', 'over')
    assert_equal 6, distance.distance('quick', 'lazy')
    assert_equal 1, distance.distance('dog', 'lazy')
    assert_equal(-1, distance.distance('fox', 'lazy'))
    assert_equal(-1, distance.distance('fox', 'leaped'))
    assert distance.distance('dog', 'dog').zero?
  end

  def test_should_work_for_no_words
    sentence = ''
    distance = WordDistance.new(sentence)
    assert_equal(-1, distance.distance('the', 'lazy'))
  end

  def test_should_work_for_invalid_search_terms
    sentence = 'the quick brown dog jumped over the lazy dog'
    distance = WordDistance.new(sentence)
    assert_equal(-1, distance.distance(nil, 'lazy'))
    assert_equal(-1, distance.distance('the', nil))
    assert_equal(-1, distance.distance('', 'lazy'))
    assert_equal(-1, distance.distance('the', ''))
    assert_equal(-1, distance.distance(' ', 'lazy'))
    assert_equal(-1, distance.distance('the', ' '))
  end

  def test_should_work_for_equal_distance
    sentence = 'the bees knees bees the'
    distance = WordDistance.new(sentence)
    assert_equal 2, distance.distance('the', 'knees')
  end
end
