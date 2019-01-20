/**
  * Represents the current state of all customers satisfaction with a given colour.
  */
object SatisfactionWithColour {

  /**
    * Create a satisfaction tracker for a colour.
    *
    * @param colour the colour to track satisfaction for.
    * @return the created [[SatisfactionWithColour]] tracker.
    */
  def apply(colour: Int): SatisfactionWithColour = {
    new SatisfactionWithColour(colour)
  }
}

/**
  * Represents the current state of all customers satisfaction with a given colour.
  */
class SatisfactionWithColour(private val colour: Int) {

  /**
    * An initial lowest satisfaction rating that gets compared to the customers. Tracks the satisfaction rating
    * of the customer least satisfied with the colour's current state.
    */
  var lowestSatisfactionWithColour: Int = 999

  /**
    * The collection of customers, tracked as they appear to the tracker.
    */
  private[this] val customers = collection.mutable.Buffer.empty[Customer]

  /**
    * The lowest satisfied customer.
    */
  private[this] var lowestSatisfiedCustomer: Option[Customer] = None

  /**
    * Add a satisfied customer.
    *
    * @param customer the customer that is satisfied.
    */
  def addSatisfiedCustomer(customer: Customer): Unit = {
    if (customer.getHappyCount < lowestSatisfactionWithColour) {
      lowestSatisfactionWithColour = customer.getHappyCount
      lowestSatisfiedCustomer = Some(customer)
    }
    // Can't use insert sort because a customer satisfaction may increase or decrease independent of satisfaction
    // with this colour.
    customers += customer
  }

  /**
    * Increase the satisfaction of a set of customers.
    *
    * @param satisfiedCustomers the customers whose satisfaction should be increased.
    */
  def increaseSatisfactionForCustomers(
      satisfiedCustomers: collection.mutable.Set[Customer]): Unit = {
    if (lowestSatisfiedCustomer.isDefined && satisfiedCustomers.contains(
          lowestSatisfiedCustomer.get)) {
      lowestSatisfactionWithColour += 1
      if (customers.nonEmpty) {
        //Recalculate lowest satisfaction with colour in case the above increment made the unhappiest customer happier
        //than another
        val sortedCustomerList = customers.sortBy(_.getHappyCount)
        lowestSatisfiedCustomer = Some(sortedCustomerList.head)
        lowestSatisfactionWithColour = lowestSatisfiedCustomer.get.getHappyCount
      }
    }
  }
}
