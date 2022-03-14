# frozen_string_literal: true

# Class to calculate the max profit that could have been gained from buying and selling stocks
# over a given price fluctation period.
module MaxProfit
  # Find the maximum profit that could have been made.
  def self.calculate(prices)
    arr = prices&.compact
    return -1 if arr.to_a.empty?

    lowest_price = arr[0]
    max_profit = 0

    arr[1..].each do |price|
      lowest_price = price if price < lowest_price

      profit = price - lowest_price

      max_profit = profit if profit > max_profit
    end

    max_profit
  end
end
