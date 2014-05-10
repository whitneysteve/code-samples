import io.Source

/**
 * The premise is this:
 * - Assume all orders are for gloss;
 * - Set a happiness for each customer to be the number of gloss colors they want;
 * - If they want only matte, add them to unsatisfied customers and keep track of the colors that may need to be upgraded to matte;
 * - While there are unsatisfied customers upgrade the color that will please the most of the unsatisfied customers;
 * - If the color cannot be upgraded because it will upset a previously satisfied customer, skip it's upgrade;
 * - If there are unsatisfied customers after applying all possible upgrades, there is no solution.
 */
object Paintshop {
  def main(args: Array[String]) {
    for(inputFile <- args) {
      try {
        new Paintshop().process(inputFile)
      } catch {
        case NoSolution => print("No solution exists")
      }
      println()
    }
  }

  protected val OrderPattern = """(\d) ([G|M])""".r
  protected val Matte = "M"
  protected val Gloss = "G"

  protected object MutableMapWithDefault {
    def apply[K,V](defaultFactory: K => V) = new collection.mutable.HashMap[K, V] {
      override def default(key: K) = {
        val defaultValue = defaultFactory(key)
        this += (key -> defaultValue)
        defaultValue
      }
    }
  }
}

class Paintshop {
  import Paintshop._

  // Colors to prepare in matte
  private[this] val matteColors = collection.mutable.MutableList.empty[Int]
  // This customer has no orders they want
  private[this] implicit val unsatisfiedCustomers = collection.mutable.Set.empty[Customer]
  // Colors start as gloss, but these customer would be happier if it was matte.
  private[this] implicit val matteUpgrades = MutableMapWithDefault[Int, collection.mutable.Set[Customer]] { _ =>
    collection.mutable.Set[Customer]()
  }
  // What upgrades will please the most amount of unsatisfied customers
  private[this] implicit val satisfiedWhenUpgraded = MutableMapWithDefault[Int, Int] { _ => 0 }
  // In case of upgrade matte, we need to decrement the happiness of customers who want that color in gloss
  private[this] implicit val satisfiedWithGloss = MutableMapWithDefault[Int, SatisfactionWithColor] { key =>
    SatisfactionWithColor(key)
  }

  /**
   * Process a single order file.
   * @param inputFile input file path
   */
  def process(inputFile: String): Unit = {
    val lines = Source.fromFile(inputFile).getLines()
    val numColors = lines.next().toInt

    for(line <- lines) {
      val customer = Customer(line)

      if(!customer.satisfied) unsatisfiedCustomers += customer

      OrderPattern.findAllIn(line).matchData map { orderData =>
        Order(orderData.group(1).toInt, orderData.group(2))
      } foreach { order: Order =>
        order.paintType match {
          case Matte =>
            matteUpgrades(order.color) += customer
            if(!customer.satisfied) {
              val wouldBeSatisfied = satisfiedWhenUpgraded(order.color)
              satisfiedWhenUpgraded(order.color) = wouldBeSatisfied + 1
            }
          case Gloss =>
            satisfiedWithGloss(order.color).addSatisfiedCustomer(customer)
          case _ =>
            throw InvalidInputFile
        }
      }
    }

    // Sort the potential upgrades so the highest number of unsatisfied customer that would be satisfied by upgrading
    val upgradeIterator = (satisfiedWhenUpgraded.toList sortBy { _._2 }).reverse.map(_._1).toIterator

    // Check potential upgrades to satisfy customer, starting with the potential upgrade that will satisfy the most
    // customers, therefore requiring the least amount of upgrades.
    while(unsatisfiedCustomers.size > 0 && upgradeIterator.hasNext) {
      val colorToUpgrade = upgradeIterator.next()

      // Everyone will still be OK if we upgrade this to matte. Hopefully this will satisfy as many customers as
      // possible, if not everyone, then we'll move onto the next possible upgrade
      if(!willUpgradeUpsetAnotherCustomer(colorToUpgrade))
        performUpgrade(colorToUpgrade, matteUpgrades)
    }

    if(unsatisfiedCustomers.size > 0) {
      // After all the upgrades we could apply without unsatisfying a previous order, we still have unsatisfied
      // customers, so ...
      throw NoSolution
    } else {
      for(i <- Range.inclusive(1, numColors)) {
        if(matteColors.contains(i)) {
          print(Matte)
        } else {
          print(Gloss)
        }
        print(' ')
      }
    }
  }

  def willUpgradeUpsetAnotherCustomer(colorToUpgrade: Int) =
    satisfiedWithGloss.contains(colorToUpgrade) && (satisfiedWithGloss(colorToUpgrade).lowestSatisfactionWithColor <= 1)

  def performUpgrade(colorToUpgrade: Int, matteUpgrades: collection.mutable.Map[Int, collection.mutable.Set[Customer]]) = {
    matteColors += colorToUpgrade
    var satisfaction: Option[SatisfactionWithColor] = None

    satisfaction = satisfiedWithGloss.get(colorToUpgrade) flatMap { _satisfaction =>
      _satisfaction.lowestSatisfactionWithColor
      Some(_satisfaction)
    }

    matteUpgrades.get(colorToUpgrade) foreach { customersNowSatisfied =>
      unsatisfiedCustomers --= customersNowSatisfied
      // We can increase the happiness of the customers who would have preferred matte, it may give us more room to
      // upgrade a color that they wanted in gloss later.
      satisfaction map { _.increaseSatisfactionForCustomers(customersNowSatisfied) }
    }
  }
}

object Customer {
  private[this] val HappyWithGlossPattern = "G".r
  private[this] var nextId = 0

  def apply(line: String) = {
    nextId += 1
    new Customer(nextId, computeInitialHappiness(line))
  }

  private[this] def computeInitialHappiness(line: String) = HappyWithGlossPattern.findAllIn(line).size
}

class Customer(val id: Int, var happyCount: Int) {
  def satisfied = happyCount > 0
}

object SatisfactionWithColor {
  def apply(color: Int) = new SatisfactionWithColor(color)
}

class SatisfactionWithColor(val color: Int) {
  var lowestSatisfactionWithColor: Int = 999

  private[this] val customers = collection.mutable.Buffer.empty[Customer]
  private[this] var lowestSatisfiedCustomer: Option[Customer] = None

  def addSatisfiedCustomer(customer: Customer) {
    if(customer.happyCount < lowestSatisfactionWithColor) {
      lowestSatisfactionWithColor = customer.happyCount
      lowestSatisfiedCustomer = Some(customer)
    }
    // Can't use insert sort because a customer satisfaction may increase or decrease independent of satisfaction
    // with this color
    customers += customer
  }

  def increaseSatisfactionForCustomers(satisfiedCustomers: collection.mutable.Set[Customer]) {
    if(lowestSatisfiedCustomer.isDefined && satisfiedCustomers.contains(lowestSatisfiedCustomer.get)) {
      lowestSatisfactionWithColor += 1
      if(customers.nonEmpty) {
        //Recalculate lowest satisfaction with color in case the above increment made the unhappiest customer happier
        //than another
        val sortedCustomerList = customers.sortBy(_.happyCount)
        lowestSatisfiedCustomer = Some(sortedCustomerList(0))
        lowestSatisfactionWithColor = lowestSatisfiedCustomer.get.happyCount
      }
    }
  }
}

object NoSolution extends Exception
object InvalidInputFile extends Exception
case class Order(color: Int, paintType: String)
