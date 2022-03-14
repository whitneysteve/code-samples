# frozen_string_literal: true

require_relative '../max_profit'
require 'minitest/autorun'

class MaxProfitTest < Minitest::Test
  def test_should_find_max_profit
    stocks1 = [3, 4, 5, 10, 11, 7, 5, 4, 4, 10]
    stocks2 = [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
    stocks3 = [10, 7, 6, 7, 4, 7, 6, 7, 8, 8, 12, 9]
    assert MaxProfit.calculate(stocks1) == 8
    assert MaxProfit.calculate(stocks2).zero?
    assert MaxProfit.calculate(stocks3) == 8
  end

  def test_should_handle_empty_array
    assert MaxProfit.calculate([]) == -1
  end

  def test_should_handle_array_with_nil
    assert MaxProfit.calculate([3, 4, 5, 10, 11, 7, nil, 5, 4, 4, 10]) == 8
  end

  def test_should_handle_nil_array
    assert MaxProfit.calculate(nil) == -1
  end
end
