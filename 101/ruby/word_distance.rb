# frozen_string_literal: true

# Implementation for a word distance calculator
class WordDistance
  def initialize(sentence)
    @words = sentence.split(/\W+/)
    @indices = {}
    @words.each.with_index do |word, idx|
      if @indices.has_key? word
        @indices[word].push(idx)
      else
        @indices[word] = [idx]
      end
    end
  end

  def distance(w_1, w_2)
    word_1 = w_1&.strip
    word_2 = w_2&.strip
    return -1 if word_1.nil? || word_1.empty? || word_2.nil? || word_2.empty?

    word_1_indices = @indices[word_1]
    return -1 if word_1_indices.to_a.empty?
    word_2_indices = @indices[word_2]
    return -1 if word_2_indices.to_a.empty?

    min_distance = @words.size + 1
    i = 0
    j = 0

    while i < word_1_indices.length && j < word_2_indices.length do
      first_val = word_1_indices[i]
      second_val = word_2_indices[j]

      distance = (first_val - second_val).abs

      if distance < min_distance
        min_distance = distance
      end

      if first_val < second_val
        i += 1
      else
        j += 1
      end
    end

    min_distance
  end
end
