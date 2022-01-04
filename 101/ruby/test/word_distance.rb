# frozen_string_literal: true

require_relative '../word_distance'
require 'minitest/autorun'

class WordDistanceTest < Minitest::Test
  def test_should_find_minimum_word_distance
    sentence = 'the quick brown dog jumped over the lazy dog'
    distance = WordDistance.new(sentence)
    assert distance.distance('the', 'lazy') == 1
    assert distance.distance('quick', 'lazy') == 6
    assert distance.distance('dog', 'lazy') == 1
    assert distance.distance('fox', 'lazy')  == -1
    assert distance.distance('fox', 'leaped') == -1
    assert distance.distance('dog', 'dog').zero?
  end

  def test_should_work_for_no_words
    sentence = ''
    distance = WordDistance.new(sentence)
    assert distance.distance('the', 'lazy') == -1
  end

  def test_should_work_for_equal_distance
    sentence = 'the bees knees bees the'
    distance = WordDistance.new(sentence)
    assert distance.distance('the', 'knees') == 2
  end
end
