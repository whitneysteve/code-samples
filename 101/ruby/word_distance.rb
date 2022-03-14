# frozen_string_literal: true

# Implementation for a word distance calculator
class WordDistance
  def initialize(sentence)
    @words = sentence.split(/\W+/)
    @indices = {}
    @words.each.with_index do |word, idx|
      if @indices.key? word
        @indices[word].push(idx)
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
    word1 = word1&.strip
    word2 = word2&.strip
    return -1 if word1.nil? || word1.empty? || word2.nil? || word2.empty?

    word1_indices = @indices[word1]
    return -1 if word1_indices.to_a.empty?

    word2_indices = @indices[word2]
    return -1 if word2_indices.to_a.empty?

    min_distance = @words.size + 1
    i = 0
    j = 0

    while i < word1_indices.length && j < word2_indices.length
      first_val = word1_indices[i]
      second_val = word2_indices[j]

      distance = (first_val - second_val).abs

      min_distance = distance if distance < min_distance

      if first_val < second_val
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
