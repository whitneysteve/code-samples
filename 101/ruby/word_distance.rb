# frozen_string_literal: true

# Implementation for a word distance calculator
class WordDistance
  def initialize(sentence)
    @sentence_arr = sentence.split(/\W+/)
    @indices = {}
    @sentence_arr.each_with_index do |raw_word, idx|
      word = raw_word.downcase.strip
      if @indices.key?(word)
        @indices[word] << idx
      else
        @indices[word] = [idx]
      end
    end
  end

  # rubocop:disable Metrics/AbcSize
  # rubocop:disable Metrics/CyclomaticComplexity
  # rubocop:disable Metrics/MethodLength
  # rubocop:disable Metrics/PerceivedComplexity
  def distance(word1, word2)
    indices1 = @indices[word1&.strip&.downcase]
    indices2 = @indices[word2&.strip&.downcase]

    return -1 if indices1.to_a.empty? || indices2.to_a.empty?

    min_distance = @sentence_arr.size + 1

    i = 0
    j = 0

    while i < indices1.length && j < indices2.length
      index1 = indices1[i]
      index2 = indices2[j]

      distance = (index1 - index2).abs

      min_distance = distance if distance < min_distance

      if index1 < index2
        i += 1
      else
        j += 1
      end
    end

    min_distance
  end
  # rubocop:enable Metrics/AbcSize
  # rubocop:enable Metrics/CyclomaticComplexity
  # rubocop:enable Metrics/MethodLength
  # rubocop:enable Metrics/PerceivedComplexity
end
