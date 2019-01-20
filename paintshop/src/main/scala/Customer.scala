import scala.util.matching.Regex

/**
  * Represents a customer whose orders we are processing.
  */
object Customer {

  /**
    * Pattern to check if a customer is happy with a gloss version of a colour.
    */
  private[this] val HappyWithGlossPattern: Regex = "G".r

  /**
    * Internal tracking of customer IDs, solution is single threaded, so no need
    * for concurrency here.
    */
  private[this] var nextId = 0

  /**
    * Create a new customer from their order line.
    *
    * @param line the line from the input file.
    * @return the constructed [[Customer]] object.
    */
  def apply(line: String): Customer = {
    nextId += 1
    new Customer(nextId, computeInitialHappiness(line))
  }

  /**
    * Computing the customers initial happiness is checking how many colours the customer has ordered in gloss.
    *
    * @param line the input line from the order file.
    * @return the happiness of the customer.
    */
  private[this] def computeInitialHappiness(line: String): Int = {
    HappyWithGlossPattern.findAllIn(line).size
  }
}

/**
  * Represents a customer whose orders we are processing.
  */
class Customer(private val id: Int, private var happyCount: Int) {

  /**
    * Get the customer's current happy count. This is the number of orders they are satisfied with.
    *
    * @return the current happy count.
    */
  def getHappyCount: Int = happyCount

  /**
    * Check if the customer is satisfied or not.
    *
    * @return true if the customer is satisfied, false if not.
    */
  def satisfied: Boolean = {
    happyCount > 0
  }
}
