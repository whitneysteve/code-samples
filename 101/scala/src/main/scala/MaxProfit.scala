import scala.collection.mutable.ListBuffer

object MaxProfit {
  def apply(stocks: List[Int]): Int = {
    var i = 0
    var j = i + 5
    var maxProfit = Int.MinValue
    var minBuy = Int.MaxValue
    while (j < stocks.length) {
      val buyPrice = stocks(i)
      if (buyPrice < minBuy) {
        minBuy = buyPrice
      }

      var profit = stocks(j) - minBuy

      if (profit > maxProfit) {
        maxProfit = profit
      }

      i += 1
      j += 1
    }

    if (maxProfit > 0) maxProfit
    else -1
  }
}

object Solution extends App {
  new MaxProfitTest().execute()
}
