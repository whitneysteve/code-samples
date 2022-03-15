import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MaxProfitTest extends AnyFunSuite with Matchers {

  test("should find max profit") {
    val stocks = List(3, 4, 5, 10, 11, 7, 5, 4, 4, 10)
    val stocks2 = List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val stocks3 = List(10, 7, 6, 7, 4, 7, 6, 7, 8, 8, 12, 9)
    MaxProfit(stocks) should be(7)
    MaxProfit(stocks2) should be(-1)
    MaxProfit(stocks3) should be(8)
  }
}